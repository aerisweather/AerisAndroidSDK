package com.example.demoaerisproject.data.preferenceStore

import java.time.Duration
import java.time.ZonedDateTime

object Cache {
    const val REQUEST_AIR_QUALITY = "air quality"
    const val REQUEST_MY_PLACE = "my place"
    const val REQUEST_LAT_LONG = "lat long"
    const val REQUEST_OVERVIEW = "overview"
    const val REQUEST_WEEKEND_FORECAST = "weekend forecast"
    const val REQUEST_EXT_FORECAST = "ext forecast"
    const val REQUEST_NEARBY_OBSERVATION = "nearby observation"
    const val REQUEST_DETAILED_OBSERVATION = "detailed observation"
    const val REQUEST_SUN_MOON = "sun moon"

    private var map = HashMap<String, Pair<ZonedDateTime, Any>?>()
    private val EXPIRE_IN_MINUTES = 10
    private val MAX_COUNT = 10

    fun isExpired(request: String): Boolean? {
        map[request]?.let {
            return Duration.between(it.first, ZonedDateTime.now()).toMinutes() > EXPIRE_IN_MINUTES
        }
        return null
    }

    fun remove(request: String) {
        map.remove(request)
    }

    /*
     * Insert or Update
     * - if too many, just dump all
     */
    fun upsert(request:String, obj:Any) {
        if(map.size > MAX_COUNT) {
            map.clear()
        }
        map[request] = Pair<ZonedDateTime, Any>(ZonedDateTime.now(), obj)
    }

    /*
     * Call this to find request waiting for value
     * On Request -> (key, null)
     */
    fun getLastRequestKey():List<String> {
        return map.filter{it.value == null}.keys.toList()
    }

    /*
     * Empty map at start and my_place change
     */
    fun clear() {
        map.clear()
    }
}