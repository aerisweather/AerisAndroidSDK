package com.xweather.sdkdemo.kotlin.di

import android.content.Context
import com.xweather.sdkdemo.kotlin.data.location.LocationRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import com.xweather.sdkdemo.kotlin.view.locations.LocationMapViewModel
import com.xweather.sdkdemo.kotlin.view.locations.LocationSearchViewModel
import com.xweather.sdkdemo.kotlin.view.locations.MyLocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        LocationRepository(context)

    @Provides
    fun provideLocationSearchViewModel(
        locationRepository: LocationRepository,
        myPlaceRepository: MyPlaceRepository,
    ): LocationSearchViewModel =
        LocationSearchViewModel(locationRepository, myPlaceRepository)

    @Provides
    fun provideMyLocationViewModel(
        myPlaceRepository: MyPlaceRepository,
    ): MyLocationViewModel =
        MyLocationViewModel(myPlaceRepository)

    @Provides
    fun provideLocationMapViewModel(
        locationRepository: LocationRepository,
        myPlaceRepository: MyPlaceRepository,
    ): LocationMapViewModel =
        LocationMapViewModel(locationRepository, myPlaceRepository)
}
