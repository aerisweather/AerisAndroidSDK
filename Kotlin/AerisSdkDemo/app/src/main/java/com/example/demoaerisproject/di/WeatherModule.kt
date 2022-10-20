package com.example.demoaerisproject.di

import android.content.Context
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.WeatherRepository
import com.example.demoaerisproject.view.weather.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object WeatherModule {

    @Provides
    fun provideWeatherRepository(@ApplicationContext context: Context): WeatherRepository =
        WeatherRepository(context)

    @Provides
    fun provideWeatherViewModel(
        @ApplicationContext context: Context,
        weatherRepository: WeatherRepository,
        myPlaceRepository: MyPlaceRepository,
        prefStoreRepository: PrefStoreRepository
    ): WeatherViewModel =
        WeatherViewModel(context, weatherRepository, myPlaceRepository, prefStoreRepository)
}