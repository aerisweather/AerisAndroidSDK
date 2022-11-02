package com.example.demoaerisproject.view.weather.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aerisweather.aeris.communication.parameter.PlaceParameter
import com.aerisweather.aeris.location.LocationHelper
import com.aerisweather.aeris.model.AerisLocation
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlace
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.WeatherRepository
import com.example.demoaerisproject.view.NavDrawerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    @ApplicationContext open val context: Context,
    weatherRepository: WeatherRepository,
    private val myPlaceRepository: MyPlaceRepository,
    private val prefStoreRepository: PrefStoreRepository
) : BaseWeatherViewModel(weatherRepository) {

    private val _locationEvent = MutableLiveData<MyPlaceEvent>()
    val locationEvent: LiveData<MyPlaceEvent> = _locationEvent

    private val _unitEvent = MutableLiveData<UnitEvent>()
    val unitEvent: LiveData<UnitEvent> = _unitEvent
    var route: String = NavDrawerItem.Detailed.route

    init {
        initDbListener()
        initUnitListener()
    }

    val myPlace: MyPlace?
        get() = (_locationEvent.value as? MyPlaceEvent.Current)?.myPlace

    val placeParam: PlaceParameter
        get() = getPlaceParameter(myPlace)

    fun requestMyPlace() {
        initDbListener()
    }

    fun requestByMapLatLong(lat: Double, longitude: Double) {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestByMapLatLong(lat, longitude)
    }

    open fun requestOverview() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestOverview(placeParam)
    }

    open fun requestWeekendForecast() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestWeekendForecast(placeParam)
    }

    open fun requestAirQuality() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestAirQuality(placeParam)
    }

    open fun requestExtForecast() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestExtForecast(placeParam)
    }

    open fun requestNearbyObservation() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestNearbyObservation(placeParam)
    }

    open fun requestDetailedObservation() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestDetailedObservation(placeParam)
    }

    open fun requestSunMoon() {
        _event.value = ObservationEvent.InProgress
        weatherRepository.requestSunMoon(myPlace)
    }

    val execLocationHelper: () -> Unit = {
        context.let {
            LocationHelper(it).lastLocs
            LocationHelper(it).currentLocation?.let {
                val address = Geocoder(context, Locale.getDefault()).getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                )
                _locationEvent.value =
                    MyPlaceEvent.Current(
                        MyPlace(
                            address[0].locality,
                            address[0].adminArea,
                            address[0].countryName.uppercase().replace("UNITED STATES", "US"),
                            true, it.latitude, it.longitude
                        )
                    )
            }
        }
    }

    private fun initUnitListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                prefStoreRepository.getBoolean(PrefStoreRepository.UNIT_METRIC_ENABLED_KEY)
                    .collect() {
                        _unitEvent.value = if (it == true) {
                            UnitEvent.Metrics
                        } else {
                            UnitEvent.Imperial
                        }
                    }
            }
        }.onFailure {
            Log.e("WeatherViewModel.initUnitListener", it.toString())
        }
    }

    private fun getPlaceParameter(myPlace: MyPlace?): PlaceParameter {
        return myPlace?.let {
            val location = AerisLocation()
            location.lat = it.latitude
            location.lon = it.longitude
            PlaceParameter(location)
        } ?: PlaceParameter(context)
    }

    private fun initDbListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                myPlaceRepository.allPlaces.collect {
                    it.find { it.myLoc }?.let {
                        _locationEvent.value = MyPlaceEvent.Current(it)
                    } ?: execLocationHelper()
                }
            }
        }.onFailure {
            _event.value = ObservationEvent.Error(it.toString())
            Log.e("WeatherViewModel.initDBListener", it.toString())
        }
    }
}

sealed class MyPlaceEvent {
    class Current(val myPlace: MyPlace?) : MyPlaceEvent()
}

sealed class UnitEvent() {
    object Imperial : UnitEvent()
    object Metrics : UnitEvent()
}