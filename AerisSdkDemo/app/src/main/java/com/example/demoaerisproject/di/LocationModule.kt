package com.example.demoaerisproject.di

import android.content.Context
import com.example.demoaerisproject.data.location.LocationRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.view.locations.LocationSearchViewModel
import com.example.demoaerisproject.view.locations.MyLocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object LocationModule {
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        LocationRepository(context)

    @Provides
    fun provideLocationSearchViewModel(
        locationRepository: LocationRepository,
        myPlaceRepository: MyPlaceRepository
    ): LocationSearchViewModel =
        LocationSearchViewModel(locationRepository, myPlaceRepository)

    @Provides
    fun provideMyLocationViewModel(
        myPlaceRepository: MyPlaceRepository
    ): MyLocationViewModel =
        MyLocationViewModel(myPlaceRepository)

}