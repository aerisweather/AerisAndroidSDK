package com.xweather.sdkdemo.kotlin.data.weather

import android.content.Context
import com.aerisweather.aeris.communication.AerisCallback
import com.aerisweather.aeris.communication.BatchCallback
import com.aerisweather.aeris.communication.CustomCallback
import com.aerisweather.aeris.communication.loaders.ObservationsTaskCallback
import com.aerisweather.aeris.communication.parameter.PlaceParameter
import com.aerisweather.aeris.model.AerisBatchResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TestWeatherRepository @Inject constructor(
    @ApplicationContext override val context: Context,
) : BatchCallback, AerisCallback, CustomCallback, ObservationsTaskCallback, WeatherRepository(context) {

    override fun requestDetailedObservation(placeParam: PlaceParameter) {
        val response = AerisBatchResponse()
        _batchEvent.value = ApiResponseEvent.Success(response)
    }
}
