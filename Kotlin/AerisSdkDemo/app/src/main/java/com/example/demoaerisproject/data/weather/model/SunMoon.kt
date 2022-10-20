package com.example.demoaerisproject.data.weather.model

import com.aerisweather.aeris.model.AerisLocation
import com.aerisweather.aeris.model.Moon
import com.aerisweather.aeris.model.Sun

data class SunMoon(
    var timestamp: Long = 0,
    var dateTimeISO: String? = null,
    var loc: AerisLocation? = null,
    var place: Place? = null,
    var profile: Profile? = null,
    var sun: Sun? = null,
    var moon: Moon? = null
)
