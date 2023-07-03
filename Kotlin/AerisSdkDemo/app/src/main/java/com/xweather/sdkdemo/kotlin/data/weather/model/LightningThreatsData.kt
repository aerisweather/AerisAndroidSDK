package com.xweather.sdkdemo.kotlin.data.weather.model

import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.LightningThreats
import com.aerisweather.aeris.model.LightningThreatsResponse

data class LightningThreatsData(
    val success: Boolean,
    val error: AerisError?,
    val responses: List<LightningThreatsResponse>,
) {
    companion object {
        fun marshall(apiResponse: LightningThreats?): LightningThreatsData? {
            apiResponse?.apply {
                return LightningThreatsData(success, error, response)
            }
            return null
        }
    }
}
