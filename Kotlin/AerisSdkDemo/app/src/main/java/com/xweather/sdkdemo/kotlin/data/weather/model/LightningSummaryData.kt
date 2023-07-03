package com.xweather.sdkdemo.kotlin.data.weather.model

import com.aerisweather.aeris.model.AerisError
import com.aerisweather.aeris.model.LightningSummaries
import com.aerisweather.aeris.model.LightningSummaryResponse

data class LightningSummaryData(
    val success: Boolean,
    val error: AerisError?,
    val responses: List<LightningSummaryResponse>,
) {
    companion object {
        fun marshall(summaries: LightningSummaries?): LightningSummaryData? {
            summaries?.apply {
                return LightningSummaryData(success, error, response)
            }
            return null
        }
    }
}
