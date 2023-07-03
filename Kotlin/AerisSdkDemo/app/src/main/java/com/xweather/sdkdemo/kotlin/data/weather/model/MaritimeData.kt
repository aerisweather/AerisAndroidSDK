package com.xweather.sdkdemo.kotlin.data.weather.model

import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.Maritime
import com.aerisweather.aeris.model.MaritimeResponse

data class MaritimeData(
    val success: Boolean,
    val error: AerisError?,
    val responses: List<MaritimeResponse>,
) {
    companion object {
        fun marshall(maritime: Maritime?): MaritimeData? {
            maritime?.apply {
                return MaritimeData(success, error, response)
            }
            return null
        }
    }
}
