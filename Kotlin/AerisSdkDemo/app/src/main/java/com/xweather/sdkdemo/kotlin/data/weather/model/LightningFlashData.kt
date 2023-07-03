package com.xweather.sdkdemo.kotlin.data.weather.model

import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.LightningFlash
import com.aerisweather.aeris.model.LightningFlashResponse

data class LightningFlashData(
    val success: Boolean,
    val error: AerisError?,
    val responses: List<LightningFlashResponse>,
) {
    companion object {
        fun marshall(apiResponse: LightningFlash?): LightningFlashData? {
            apiResponse?.apply {
                return LightningFlashData(success, error, response)
            }
            return null
        }
    }
}
