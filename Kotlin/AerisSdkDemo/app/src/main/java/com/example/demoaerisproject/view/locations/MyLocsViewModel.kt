package com.example.demoaerisproject.view.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoaerisproject.data.room.MyPlace
import com.example.demoaerisproject.data.room.MyPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLocationViewModel @Inject constructor(
    private val myPlaceRepository: MyPlaceRepository
) : ViewModel() {

    protected val _event = MutableLiveData<MyLocationEvent>()
    val event: LiveData<MyLocationEvent> = _event

    init {
        initDbListener()
    }

    fun initDbListener() {
        _event.value = MyLocationEvent.InProgress

        kotlin.runCatching {
            viewModelScope.launch {
                myPlaceRepository.allPlaces.collect {
                    _event.value = MyLocationEvent.Success(it)
                }
            }
        }.onFailure {
            _event.value = MyLocationEvent.Error(it.toString())
            Log.e("LocationSearchViewModel.initEventListener", it.toString())
        }
    }

    fun selectAsMyLocation(myPlace: MyPlace) {
        viewModelScope.launch {
            myPlaceRepository.resetMyLocFalse()
            myPlace.myLoc = true
            myPlaceRepository.update(myPlace)
        }
    }
}

sealed class MyLocationEvent {
    object InProgress : MyLocationEvent()
    class Success(val response: List<MyPlace>) : MyLocationEvent()
    class Error(val msg: String) : MyLocationEvent()
}