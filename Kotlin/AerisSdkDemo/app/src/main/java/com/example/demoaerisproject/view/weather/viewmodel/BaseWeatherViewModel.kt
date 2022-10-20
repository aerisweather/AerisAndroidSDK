package com.example.demoaerisproject.view.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerisweather.aeris.model.AerisBatchResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.example.demoaerisproject.data.weather.AerisBatchResponseEvent
import com.example.demoaerisproject.data.weather.WeatherRepository
import com.example.demoaerisproject.data.weather.model.SunMoonResponse
import kotlinx.coroutines.launch

open class BaseWeatherViewModel(val weatherRepository: WeatherRepository) : ViewModel() {
    protected val _event = MutableLiveData<ObservationEvent>()
    val event: LiveData<ObservationEvent> = _event

    init {
        initEventListener()
    }

    protected fun initEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                weatherRepository.batchEvent.collect {
                    val event = when (it) {
                        is AerisBatchResponseEvent.Success ->
                            ObservationEvent.Success(it.response)

                        is AerisBatchResponseEvent.SunMoon ->
                            ObservationEvent.SunMoon(it.response)

                        is AerisBatchResponseEvent.Map ->
                            ObservationEvent.Map(it.response)

                        is AerisBatchResponseEvent.Error ->
                            ObservationEvent.Error(it.msg)
                    }
                    _event.value = event
                }
            }
        }.onFailure {
            _event.value = ObservationEvent.Error(it.toString())
            Log.e("BaseWeatherViewModel.initEventListener", it.toString())
        }
    }
}

sealed class ObservationEvent() {
    object InProgress : ObservationEvent()
    class Success(val response: AerisBatchResponse?) : ObservationEvent()
    class SunMoon(val response: SunMoonResponse?) : ObservationEvent()
    class Map(val response: ObservationResponse?) : ObservationEvent()
    class Error(val msg: String) : ObservationEvent()
}