package com.xweather.sdkdemo.kotlin.data.weather.model

import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.AerisLocation
import com.aerisweather.aeris.model.LightningObservation
import com.aerisweather.aeris.model.LightningStrike
import com.aerisweather.aeris.model.LightningStrikeResponse

data class LightningInstance(
    val id: String,
    val location: AerisLocation,
    val observation: LightningObservation,
)

data class LightningData(
    val success: Boolean,
    val error: AerisError?,
    val list: List<LightningStrikeResponse>,
) {
    companion object {
        fun marshall(strike: LightningStrike?): LightningData? {
            return strike?.let {
                LightningData(true, null, it.response.toList())
            }
        }
    }
}
