package com.xweather.sdkdemo.kotlin.view.locations

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.xweather.sdkdemo.kotlin.data.location.LocationRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationMapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val myPlaceRepository: MyPlaceRepository,
) : ViewModel() {

    private var zoom: Float = 4f
    var myPlace: MyPlace = MyPlace(false, 2.38, 103.97)

    private var markerState = mutableStateOf(MarkerState(myPlace.getLatLong()))
    val markerStateValue: MarkerState
        get() {
            return markerState.value
        }

    private var cameraPositionState = mutableStateOf(
        CameraPositionState(
            CameraPosition.fromLatLngZoom(
                myPlace.getLatLong(),
                zoom,
            ),
        ),
    )
    val cameraPositionStateValue: CameraPositionState
        get() {
            return cameraPositionState.value
        }

    /*
     * TODO - do we need these ?
     */
    var openMenu = mutableStateOf(false)
    var uiSettings = mutableStateOf(MapUiSettings(compassEnabled = false))
    var mapProperties = mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    var mapVisible = mutableStateOf(true)

    private val _event = MutableLiveData<MapEvent>()
    val event: LiveData<MapEvent> = _event

    init {
        initDbListener()
    }

    fun updateMyPlace(place: MyPlace) {
        myPlace = place
        markerState.value = MarkerState(myPlace.getLatLong())
        cameraPositionState.value = CameraPositionState(
            CameraPosition.fromLatLngZoom(
                myPlace.getLatLong(),
                zoom,
            ),
        )
    }

    private fun initDbListener() {
        _event.value = MapEvent.InProgress

        kotlin.runCatching {
            viewModelScope.launch {
                myPlaceRepository.allPlaces.collect {
                    var hasLocation = false
                    for (place in it) {
                        if (place.myLoc) {
                            hasLocation = true
                            _event.value = MapEvent.Success(place)
                        }
                    }
                    if(!hasLocation) {
                        // API response with place of no location
                        _event.value = MapEvent.Success(null)
                    }

                    // initialization only
                    this.cancel()
                }
            }
        }.onFailure {
            _event.value = MapEvent.Error(it.toString())
            Log.e("LocationMapModel.initEventListener", it.toString())
        }
    }

    fun selectAsMyLocation() {
        viewModelScope.launch {
            myPlaceRepository.resetMyLocFalse()
            myPlace.myLoc = true
            myPlaceRepository.insert(myPlace)

            // tell Activity we are done
            _event.value = MapEvent.Confirm(myPlace)
        }
    }

    fun invalidate(newSelectPlace: MyPlace) {
        _event.value = MapEvent.Success(newSelectPlace)
    }
}

sealed class MapEvent() {
    object InProgress : MapEvent()
    class Success(val myPlace: MyPlace?) : MapEvent()
    class Confirm(val myPlace: MyPlace?) : MapEvent()
    class Error(val msg: String) : MapEvent()
}
