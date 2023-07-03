package com.xweather.sdkdemo.kotlin.data.weather.model

class SunMoonResponse(
    val success: Boolean,
    val error: Error?,
    val response: List<SunMoon>,
)
