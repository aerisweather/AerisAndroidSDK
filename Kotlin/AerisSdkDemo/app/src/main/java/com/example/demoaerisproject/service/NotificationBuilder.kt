package com.example.demoaerisproject.service

import android.content.Context
import android.os.SystemClock
import com.aerisweather.aeris.communication.parameter.PlaceParameter
import com.aerisweather.aeris.logging.Logger
import com.aerisweather.aeris.model.AerisBatchResponse
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.AerisBatchResponseEvent
import com.example.demoaerisproject.data.weather.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotificationBuilder(
    val context: Context,
    val myPlaceRepository: MyPlaceRepository,
    val prefStoreRepository: PrefStoreRepository,
    val weatherRepository: WeatherRepository,
    val prefStore: PrefStoreRepository
) {

    fun request() {
        initEventListener()

        CoroutineScope(Dispatchers.Default).launch {
            kotlin.runCatching {
                myPlaceRepository.allPlaces.collect {
                    val p = it.filter { it.myLoc }
                    val place = if (p.isNotEmpty()) {
                        PlaceParameter(p[0].latitude, p[0].longitude)
                    } else {
                        null
                    }
                    weatherRepository.requestForecast4Notification(place)
                }
            }.onSuccess {
                Logger.d(TAG, "onHandleIntent() - success")
            }.onFailure {
                Logger.d(TAG, "onHandleIntent() - exception: " + it.message)
            }
        }
    }

    private fun initEventListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                weatherRepository.batchEvent.collect {
                    when (it) {
                        is AerisBatchResponseEvent.Success ->
                            send(it.response)

                        else -> {

                        }
                    }
                }
            }
        }.onFailure {
            Logger.d(TAG, "onHandleIntent() - exception: " + it.message)
        }
    }

    private fun send(response: AerisBatchResponse?) {
        response?.responses?.let {

            if (it.size == 2) {
                val obResponse = ObservationResponse(it[0].firstResponse)
                val fResponse = ForecastsResponse(it[1].firstResponse)
                AerisNotification().setCustom(
                    context,
                    isMetricEnabled(),
                    obResponse,
                    fResponse
                )
                CoroutineScope(Dispatchers.Default).launch {
                    prefStore.setLong(
                        PrefStoreRepository.NTF_TIMESTAMP_KEY,
                        SystemClock.elapsedRealtime()
                    )
                }
            }
        }
    }

    private fun isMetricEnabled(): Boolean {
        return runBlocking {
            prefStoreRepository.getBoolean(PrefStoreRepository.UNIT_METRIC_ENABLED_KEY)
                .firstOrNull() ?: false
        }
    }

    companion object {
        private val TAG = NotificationBuilder::class.java.simpleName
    }
}