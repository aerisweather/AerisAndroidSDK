package com.xweather.sdkdemo.kotlin.view.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerisweather.aeris.model.AerisBatchResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.xweather.sdkdemo.kotlin.data.weather.ApiResponseEvent
import com.xweather.sdkdemo.kotlin.data.weather.WeatherRepository
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningData
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningFlashData
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningSummaryData
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningThreatsData
import com.xweather.sdkdemo.kotlin.data.weather.model.MaritimeData
import com.xweather.sdkdemo.kotlin.data.weather.model.SunMoonResponse
import kotlinx.coroutines.launch

open class BaseWeatherViewModel(val weatherRepository: WeatherRepository) : ViewModel() {
    protected val _event = MutableLiveData<BaseWeatherEvent>()
    val event: LiveData<BaseWeatherEvent> = _event

    init {
        initEventListener()
    }

    protected fun initEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                weatherRepository.batchEvent.collect {
                    val event = when (it) {
                        is ApiResponseEvent.Success ->
                            BaseWeatherEvent.Success(it.response)

                        is ApiResponseEvent.SunMoon ->
                            BaseWeatherEvent.SunMoon(it.response)

                        is ApiResponseEvent.Map ->
                            BaseWeatherEvent.Map(it.response)

                        is ApiResponseEvent.Lightning ->
                            BaseWeatherEvent.Lightning(it.response)

                        is ApiResponseEvent.LightningThreats ->
                            BaseWeatherEvent.LightningThreats(it.response)

                        is ApiResponseEvent.LightningFlash ->
                            BaseWeatherEvent.LightningFlash(it.response)

                        is ApiResponseEvent.LightningSummary ->
                            BaseWeatherEvent.LightningSummary(it.response)

                        is ApiResponseEvent.Maritime ->
                            BaseWeatherEvent.Maritime(it.response)

                        is ApiResponseEvent.Error ->
                            BaseWeatherEvent.Error(it.msg)
                    }
                    _event.value = event
                }
            }
        }.onFailure {
            _event.value = BaseWeatherEvent.Error(it.toString())
            Log.e("BaseWeatherViewModel.initEventListener", it.toString())
        }
    }
}

sealed class BaseWeatherEvent() {
    object InProgress : BaseWeatherEvent()
    class Success(val response: AerisBatchResponse?) : BaseWeatherEvent()
    class SunMoon(val response: SunMoonResponse?) : BaseWeatherEvent()
    class Map(val response: ObservationResponse?) : BaseWeatherEvent()
    class Lightning(val response: LightningData?) : BaseWeatherEvent()
    class LightningThreats(val response: LightningThreatsData?) : BaseWeatherEvent()
    class LightningFlash(val response: LightningFlashData?) : BaseWeatherEvent()
    class LightningSummary(val response: LightningSummaryData?) : BaseWeatherEvent()
    class Maritime(val response: MaritimeData?) : BaseWeatherEvent()
    class Error(val msg: String) : BaseWeatherEvent()
}
