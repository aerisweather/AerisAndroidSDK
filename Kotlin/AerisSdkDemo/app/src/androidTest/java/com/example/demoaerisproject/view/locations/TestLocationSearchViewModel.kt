package com.example.demoaerisproject.view.locations

import androidx.lifecycle.ViewModel
import com.example.demoaerisproject.data.location.LocationRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import javax.inject.Inject

class TestLocationSearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val myPlaceRepository: MyPlaceRepository
) : LocationSearchViewModel(locationRepository, myPlaceRepository) {
    init {
        _event.value = SearchEvent.Error("fake test")
    }
}