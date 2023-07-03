package com.xweather.sdkdemo.kotlin.data.location

import android.content.Context
import com.aerisweather.aeris.communication.loaders.PlacesTask
import com.aerisweather.aeris.communication.loaders.PlacesTaskCallback
import com.aerisweather.aeris.communication.parameter.ParameterBuilder
import com.aerisweather.aeris.communication.parameter.PlaceParameter
import com.aerisweather.aeris.communication.parameter.QueryParameter
import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.response.PlacesResponse
import com.aerisweather.aeris.util.ValidationUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

class LocationRepository @Inject constructor(
    @ApplicationContext val context: Context,
) : PlacesTaskCallback {

    private val _event =
        MutableStateFlow<PlaceResponseEvent>(
            PlaceResponseEvent.Success(emptyList()),
        )
    val event: StateFlow<PlaceResponseEvent> = _event

    fun requestNearest(text: String) {
        PlacesTask(context, this).let {
            when {
                ValidationUtil.isValidCoordinate(text) -> execTaskNearest(text, it)

                ValidationUtil.isValidZipcode(text) ->
                    it.requestSearch(QueryParameter("zipcode:$text"))

                !ValidationUtil.isNumber(text) && ValidationUtil.isValidPlaceString(text)
                -> execTaskSearch(text, it)

                else ->
                    _event.value =
                        PlaceResponseEvent.Error("Failed validation: unsupported input value")
            }
        }
    }

    fun requestMyLocation() {
        val task = PlacesTask(context, this)
        val builder = ParameterBuilder().withLimit(25)
            .withRadius("50mi")
        task.requestClosest(PlaceParameter(context), *builder.build())
    }

    override fun onPlacesLoaded(responses: MutableList<PlacesResponse>?) {
        _event.value = PlaceResponseEvent.Success(responses?.toList() ?: emptyList())
    }

    override fun onPlacesFailed(error: AerisError?) {
        _event.value = PlaceResponseEvent.Error(error.toString())
    }

    private fun execTaskNearest(text: String, task: PlacesTask) {
        val temp = text.split(",").toTypedArray()
        val builder = ParameterBuilder()

        kotlin.runCatching {
            val latitude = temp[0].toDouble()
            val longitude = temp[1].toDouble()
            builder.withRadius("50mi")
            task.requestClosest(
                PlaceParameter(latitude, longitude),
                *builder.build(),
            )
        }.onFailure {
            _event.value = PlaceResponseEvent.Error("Places search based on lat/lng failed!")
        }
    }

    private fun execTaskSearch(text: String, task: PlacesTask) {
        val builder = ParameterBuilder()
        val temp = text.split(",").toTypedArray()
        builder.withLimit(50).withFilter("cities").withSort("pop:-1")
        if (temp.size == 1) {
            builder.withQuery("name:^" + text.lowercase(Locale.getDefault()))
            task.requestSearch(*builder.build())
        } else {
            val name = temp[0].trim { it <= ' ' }
            var state: String? = null
            if (temp.size > 1) {
                state = temp[1].trim { it <= ' ' }.lowercase(Locale.getDefault())
                if (state.length == 1) {
                    state = "^$state"
                }
            }
            var country: String? = null
            if (temp.size > 2) {
                country = temp[2].trim { it <= ' ' }.lowercase(Locale.getDefault())
                if (country.length == 1) {
                    country = "^$country"
                }
            }
            val queryBuilder = StringBuilder("name:")
            queryBuilder.append(name)
            state?.apply {
                queryBuilder.append(",")
                    .append("state:")
                    .append(state)
            }
            country?.apply {
                queryBuilder.append(",")
                    .append("country:")
                    .append(country)
            }
            builder.withQuery(queryBuilder.toString())
            task.requestSearch(*builder.build())
        }
    }
}

sealed class PlaceResponseEvent {
    class Success(val response: List<PlacesResponse>) : PlaceResponseEvent()
    class Error(val msg: String) : PlaceResponseEvent()
}
