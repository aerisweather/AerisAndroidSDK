package com.xweather.sdkdemo.kotlin.di

import android.content.Context
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import com.xweather.sdkdemo.kotlin.data.weather.WeatherRepository
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun provideWeatherRepository(@ApplicationContext context: Context): WeatherRepository =
        WeatherRepository(context)

    @Provides
    fun provideWeatherViewModel(
        @ApplicationContext context: Context,
        weatherRepository: WeatherRepository,
        myPlaceRepository: MyPlaceRepository,
        prefStoreRepository: PrefStoreRepository,
    ): WeatherViewModel =
        WeatherViewModel(context, weatherRepository, myPlaceRepository, prefStoreRepository)
}
