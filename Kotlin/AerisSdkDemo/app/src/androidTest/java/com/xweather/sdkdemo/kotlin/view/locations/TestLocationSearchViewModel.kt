package com.xweather.sdkdemo.kotlin.view.locations

import com.xweather.sdkdemo.kotlin.data.location.LocationRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import javax.inject.Inject

class TestLocationSearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val myPlaceRepository: MyPlaceRepository,
) : LocationSearchViewModel(locationRepository, myPlaceRepository) {
    init {
        _event.value = SearchEvent.Error("fake test")
    }
}
