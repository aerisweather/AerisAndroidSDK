package com.xweather.sdkdemo.kotlin.view.weather.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aerisweather.aeris.communication.parameter.PlaceParameter
import com.aerisweather.aeris.location.LocationHelper
import com.aerisweather.aeris.model.AerisLocation
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import com.xweather.sdkdemo.kotlin.data.weather.WeatherRepository
import com.xweather.sdkdemo.kotlin.view.NavDrawerItem
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
    private val prefStoreRepository: PrefStoreRepository,
) : BaseWeatherViewModel(weatherRepository) {

    private val _locationEvent = MutableLiveData<MyPlaceEvent>()
    val locationEvent: LiveData<MyPlaceEvent> = _locationEvent

    private val _unitEvent = MutableLiveData<UnitEvent>()
    val unitEvent: LiveData<UnitEvent> = _unitEvent
    var route: String = NavDrawerItem.Detailed.route

    var openDialog = mutableStateOf(false)

    init {
        initDbListener()
        initUnitListener()
    }

    val myPlace: MyPlace?
        get() = (_locationEvent.value as? MyPlaceEvent.Current)?.myPlace

    private val placeParam: PlaceParameter
        get() = getPlaceParameter(myPlace)

    fun requestMyPlace() {
        initDbListener()
    }

    fun requestByMapLatLong(lat: Double, longitude: Double) {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestByMapLatLong(lat, longitude)
    }

    open fun requestOverview() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestOverview(placeParam)
    }

    fun requestLightning() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestLightningStrike(placeParam)
    }

    fun requestLightningThreats() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestLightningThreats(placeParam)
    }

    fun requestLightningFlash() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestLightningFlash(placeParam)
    }

    fun requestLightningSummary() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestLightningSummary(placeParam)
    }

    fun requestMaritime() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestMaritime(placeParam)
    }

    open fun requestWeekendForecast() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestWeekendForecast(placeParam)
    }

    open fun requestAirQuality() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestAirQuality(placeParam)
    }

    open fun requestExtForecast() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestExtForecast(placeParam)
    }

    open fun requestNearbyObservation() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestNearbyObservation(placeParam)
    }

    open fun requestDetailedObservation() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestDetailedObservation(placeParam)
    }

    open fun requestSunMoon() {
        _event.value = BaseWeatherEvent.InProgress
        weatherRepository.requestSunMoon(myPlace)
    }

    val execLocationHelper: () -> Unit = {
        context.let {
            LocationHelper(it).currentLocation?.let {
                val address = Geocoder(context, Locale.getDefault()).getFromLocation(
                    it.latitude,
                    it.longitude,
                    1,
                )
                address?.apply {
                    _locationEvent.value =
                        MyPlaceEvent.Current(
                            MyPlace(
                                true,
                                it.latitude,
                                it.longitude,
                                this[0].locality ?: "",
                                this[0].adminArea ?: "",
                                this[0].countryName ?: "".uppercase()
                                    .replace("UNITED STATES", "US"),
                            ),
                        )
                }
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
            _event.value = BaseWeatherEvent.Error(it.toString())
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
