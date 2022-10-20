package com.example.demoaerisproject.data.room

import androidx.room.Entity
import com.aerisweather.aeris.util.WeatherUtil
import java.util.*

@Entity(tableName = "my_place_table", primaryKeys = ["name", "state", "country"])
class MyPlace(
    var name: String,
    var state: String,
    var country: String,
    var myLoc: Boolean = false,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) {

    fun getTextDisplay(defaultText: String?): String {
        return if(defaultText.isNullOrEmpty()) {
            String.format(
                "%s, %s", WeatherUtil.capitalize(name),
                country.uppercase(Locale.getDefault())
            )
        }
        else {
            String.format(
                "%s, %s, %s", WeatherUtil.capitalize(name),
                state.uppercase(Locale.getDefault()),
                country.uppercase(Locale.getDefault())
            )
        }
    }
}
