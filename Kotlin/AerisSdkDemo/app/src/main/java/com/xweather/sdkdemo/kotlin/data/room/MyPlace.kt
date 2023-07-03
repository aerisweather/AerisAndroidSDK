package com.xweather.sdkdemo.kotlin.data.room

import androidx.room.Entity
import com.aerisweather.aeris.util.WeatherUtil
import com.google.android.gms.maps.model.LatLng
import java.util.*

@Entity(tableName = "my_place_table", primaryKeys = ["latitude", "longitude"])
class MyPlace(
    var myLoc: Boolean = false,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var name: String? = "unknown",
    var state: String? = "unknown",
    var country: String? = "unknown",
) {

    fun getTextDisplay(defaultText: String?): String? {
        return if (defaultText.isNullOrEmpty()) {
            String.format(
                "%s, %s",
                WeatherUtil.capitalize(name),
                country?.uppercase(Locale.getDefault()),
            )
        } else {
            String.format(
                "%s, %s, %s",
                WeatherUtil.capitalize(name),
                state?.uppercase(Locale.getDefault()),
                country?.uppercase(Locale.getDefault()),
            )
        }
    }

    fun getLatLong() = LatLng(latitude, longitude)
}
