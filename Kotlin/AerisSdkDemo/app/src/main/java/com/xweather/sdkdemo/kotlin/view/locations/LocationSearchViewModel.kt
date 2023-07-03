package com.xweather.sdkdemo.kotlin.view.locations

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerisweather.aeris.response.PlacesResponse
import com.xweather.sdkdemo.kotlin.data.location.LocationRepository
import com.xweather.sdkdemo.kotlin.data.location.PlaceResponseEvent
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class LocationSearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val myPlaceRepository: MyPlaceRepository,
) : ViewModel() {
    val openMenu = mutableStateOf(false)

    protected val _event = MutableLiveData<SearchEvent>()
    val event: LiveData<SearchEvent> = _event

    init {
        initEventListener()
    }

    @OptIn(FlowPreview::class)
    protected fun initEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                locationRepository.event.debounce(1000).collect {
                    val event = when (it) {
                        is PlaceResponseEvent.Success -> {
                            SearchEvent.Success(it.response)
                        }

                        is PlaceResponseEvent.Error -> {
                            SearchEvent.Error(it.msg)
                        }
                    }
                    _event.value = event
                }
            }
        }.onFailure {
            _event.value = SearchEvent.Error(it.toString())
            Log.e("LocationSearchViewModel.initEventListener", it.toString())
        }
    }

    fun locateMe() {
        _event.value = SearchEvent.InProgress
        locationRepository.requestMyLocation()
    }

    fun search(text: String) {
        _event.postValue(SearchEvent.InProgress)
        val trimmed = text.trim { it <= ' ' }
        locationRepository.requestNearest(trimmed)
    }

    fun addLocation(myPlace: MyPlace) {
        viewModelScope.launch {
            myPlaceRepository.resetMyLocFalse()
            myPlaceRepository.insert(myPlace)
        }
    }
}

sealed class SearchEvent() {
    object InProgress : SearchEvent()
    class Success(val response: List<PlacesResponse>) : SearchEvent()
    class Confirm() : SearchEvent()
    class Error(val msg: String) : SearchEvent()
}
