package com.example.demoaerisproject.view.viewmodel

import android.content.Context
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.WeatherRepository
import com.example.demoaerisproject.view.weather.viewmodel.BaseWeatherEvent
import com.example.demoaerisproject.view.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TestWeatherViewModel @Inject constructor(
    @ApplicationContext override val context: Context,
    weatherRepository: WeatherRepository,
    private val myPlaceRepository: MyPlaceRepository,
    private val prefStoreRepository: PrefStoreRepository
) : WeatherViewModel(context, weatherRepository, myPlaceRepository, prefStoreRepository) {
    override fun requestDetailedObservation() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestExtForecast() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestNearbyObservation() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestOverview() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestWeekendForecast() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestAirQuality() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }

    override fun requestSunMoon() {
        _event.value = BaseWeatherEvent.Error("fake error")
    }
}