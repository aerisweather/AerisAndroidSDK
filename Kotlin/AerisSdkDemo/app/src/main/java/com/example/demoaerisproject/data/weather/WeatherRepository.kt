package com.example.demoaerisproject.data.weather

import android.content.Context
import com.aerisweather.aeris.communication.*
import com.aerisweather.aeris.communication.fields.Fields
import com.aerisweather.aeris.communication.fields.ForecastsFields
import com.aerisweather.aeris.communication.fields.ObservationFields
import com.aerisweather.aeris.communication.loaders.ObservationsTask
import com.aerisweather.aeris.communication.loaders.ObservationsTaskCallback
import com.aerisweather.aeris.communication.parameter.*
import com.aerisweather.aeris.model.AerisBatchResponse
import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.example.demoaerisproject.data.room.MyPlace
import com.example.demoaerisproject.data.weather.model.SunMoonResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

open class WeatherRepository @Inject constructor(
    @ApplicationContext open val context: Context
) : BatchCallback, AerisCallback, CustomCallback, ObservationsTaskCallback {

    val _batchEvent =
        MutableStateFlow<ApiResponseEvent>(
            ApiResponseEvent.Success(null)
        )
    val batchEvent: StateFlow<ApiResponseEvent> = _batchEvent
    private val NUMBER_OF_DAYS = 7

    fun requestAirQuality(placeParam: PlaceParameter) {
        val request = AerisRequest(
            Endpoint(
                EndpointType.AIRQUALITY
            ),
            Action.CLOSEST,
            placeParam,
            LimitParameter(10)
        )

        val task = AerisCommunicationTask(context, this, request)
        task.execute()
    }

    fun requestExtForecast(placeParam: PlaceParameter) {
        val request = AerisRequest(
            Endpoint(
                EndpointType.FORECASTS
            ), Action.CLOSEST, placeParam,
            FieldsParameter.initWith(
                Fields.INTERVAL,
                ForecastsFields.WEATHER_PRIMARY,
                ForecastsFields.MAX_TEMP_F,
                ForecastsFields.MAX_TEMP_C,
                ForecastsFields.ICON,
                ForecastsFields.DATETIME_ISO,
                ForecastsFields.MIN_TEMP_F,
                ForecastsFields.MIN_TEMP_C
            ), FilterParameter("7"),
            LimitParameter(10)
        )

        val task = AerisCommunicationTask(context, this, request)
        task.execute()
    }

    fun requestNearbyObservation(placeParam: PlaceParameter) {
        val request = AerisRequest(
            Endpoint(
                EndpointType.OBSERVATIONS
            ), Action.CLOSEST,
            placeParam, FieldsParameter.initWith(
                ObservationFields.TEMP_C, ObservationFields.TEMP_F,
                ObservationFields.ICON,
                ObservationFields.WEATHER_SHORT, Fields.PLACE,
                ObservationFields.DATETIME
            ), LimitParameter(10)
        )

        val task = AerisCommunicationTask(context, this, request)
        task.execute()
    }

    fun requestWeekendForecast(placeParam: PlaceParameter) {
        val request = AerisRequest(
            Endpoint(
                EndpointType.FORECASTS
            ), Action.CLOSEST, placeParam,
            FilterParameter("daynight"), FromParameter("friday"),
            ToParameter("+3days")
        )

        val task = AerisCommunicationTask(context, this, request)
        task.execute()
    }

    /*
     * Map Lat Long request
     */
    fun requestByMapLatLong(lattitude: Double, longitude: Double) {
        val builder = ParameterBuilder().withFields(
            ObservationFields.ICON, ObservationFields.TEMP_C,
            ObservationFields.TEMP_F, Fields.RELATIVE_TO
        )
        val task = ObservationsTask(context, this)
        task.requestClosest(PlaceParameter(lattitude, longitude), *builder.build())
    }

    /*
     * Map Lat Long response
     */
    override fun onObservationsLoaded(responses: MutableList<ObservationResponse>?) {
        responses?.let {
            it[0].let {
                _batchEvent.value = ApiResponseEvent.Map(it)
                return
            }
        }
        _batchEvent.value = ApiResponseEvent.Error("no map value returned")
    }

    /*
     * Map Lat Long response
     */
    override fun onObservationsFailed(error: AerisError?) {
        _batchEvent.value =
            ApiResponseEvent.Error("Failed to load observation at that point")
    }

    fun requestOverview(placeParam: PlaceParameter) {
        BatchBuilder().let {
            it.addGlobalParameter(placeParam)
            it.addEndpoint(
                Endpoint(EndpointType.CONDITIONS)
                    .addParameters(FieldsParameter.initWith("periods"))
            )

            it.addEndpoint(
                Endpoint(EndpointType.PLACES, Action.CLOSEST)
                    .addParameters(FieldsParameter.initWith("place"))
            )

            it.addEndpoint(
                Endpoint(EndpointType.FORECASTS, Action.CLOSEST)
                    .addParameters(
                        FieldsParameter.initWith(
                            ForecastsFields.WEATHER_PRIMARY,
                            ForecastsFields.MAX_TEMP_F,
                            ForecastsFields.MAX_TEMP_C,
                            ForecastsFields.ICON,
                            ForecastsFields.DATETIME_ISO,
                            ForecastsFields.MIN_TEMP_F,
                            ForecastsFields.MIN_TEMP_C
                        )
                    )
            )
            val request = it.build()
            val task = BatchCommunicationTask(
                context,
                this, request
            )
            task.execute()
        }
    }

    fun requestForecast4Notification(place: PlaceParameter? = null) {
        BatchBuilder().let {
            it.addGlobalParameter(place ?: PlaceParameter(context))
            it.addEndpoint(
                Endpoint(
                    EndpointType.OBSERVATIONS,
                    Action.CLOSEST
                ).addParameters(
                    FieldsParameter.initWith(
                        ObservationFields.ICON,
                        ObservationFields.TEMP_F,
                        ObservationFields.WEATHER,
                        ObservationFields.TEMP_C,
                        ObservationFields.WEATHER_SHORT
                    )
                )
            )
            it.addEndpoint(
                Endpoint(EndpointType.FORECASTS, Action.CLOSEST)
                    .addParameters(
                        FieldsParameter.initWith(
                            Fields.INTERVAL,
                            ForecastsFields.IS_DAY,
                            ForecastsFields.MAX_TEMP_F,
                            ForecastsFields.MIN_TEMP_F,
                            ForecastsFields.MIN_TEMP_C,
                            ForecastsFields.MAX_TEMP_C
                        ), FilterParameter(
                            "daynight"
                        ), PLimitParameter(2)
                    )
            )
            val request = it.build()
            val task = BatchCommunicationTask(context, this, request)
            task.execute()
        }
    }

    fun requestSunMoon(myPlace: MyPlace?) {
        ParameterBuilder()
            .withLimit(NUMBER_OF_DAYS)
            .withFrom("now")
            .withTo("+" + NUMBER_OF_DAYS + "days").let {

                val request = AerisRequest(
                    Endpoint("sunmoon"),
                    myPlace?.getTextDisplay("") ?: ":auto", *it.build()
                )
                request.withDebugOutput(true)
                val task = AerisCustomCommunicationTask(
                    context, this,
                    request
                )
                task.execute()
            }
    }

    /*
     * Sunmoon Response
     */
    override fun onResult(custom: String?, response: String?) {
        kotlin.runCatching {
            val list = Gson().fromJson(response, SunMoonResponse::class.java)
            _batchEvent.value = ApiResponseEvent.SunMoon(list)
        }.onFailure {
            _batchEvent.value = ApiResponseEvent.Error("sunMoon:$it")
        }
    }

    /*
     * Air Quality Response
     */
    override fun onResult(endpoint: EndpointType?, response: AerisResponse?) {
        _batchEvent.value = if (response?.isSuccessful == true) {
            val batch = AerisBatchResponse()
            batch.responses = mutableListOf(response)
            ApiResponseEvent.Success(batch)
        } else {
            ApiResponseEvent.Error("air 1uality: failed")
        }
    }

    open fun requestDetailedObservation(placeParam: PlaceParameter) {
        BatchBuilder().let {
            it.addGlobalParameter(placeParam)
            it.addEndpoint(
                Endpoint(EndpointType.CONDITIONS)
                    .addParameters(FieldsParameter.initWith("periods"))
            )
            it.addEndpoint(
                Endpoint(EndpointType.PLACES, Action.CLOSEST)
                    .addParameters(FieldsParameter.initWith("place"))
            )
            it.addEndpoint(
                Endpoint(EndpointType.FORECASTS, Action.CLOSEST)
                    .addParameters(
                        FilterParameter("daynight"),
                        PLimitParameter(2)
                    )
            )
            it.addEndpoint(
                Endpoint(EndpointType.FORECASTS, Action.CLOSEST)
                    .addParameters(
                        FilterParameter("3hr"), PLimitParameter(
                            8
                        ), FieldsParameter.initWith(
                            ForecastsFields.TEMP_F,
                            ForecastsFields.TEMP_C, ForecastsFields.ICON,
                            ForecastsFields.DATETIME_ISO, Fields.INTERVAL
                        )
                    )
            )
            val request = it.build()
            val task = BatchCommunicationTask(context, this, request)
            task.execute()
        }
    }

    /*
     * TODO
     *  1. Eventually replace callback from library with async response or retrofit
     */
    override fun onBatchResponse(response: AerisBatchResponse?) {
        _batchEvent.value = if (response?.isSuccessful == true) {
            ApiResponseEvent.Success(response)
        } else {
            ApiResponseEvent.Error("batch response failed")
        }
    }
}

sealed class ApiResponseEvent {
    class Success(val response: AerisBatchResponse?) : ApiResponseEvent()
    class SunMoon(val response: SunMoonResponse?) : ApiResponseEvent()
    class Map(val response: ObservationResponse?) : ApiResponseEvent()
    class Error(val msg: String) : ApiResponseEvent()
}
