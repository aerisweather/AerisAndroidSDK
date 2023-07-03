package com.xweather.sdkdemo.kotlin.di

import android.content.Context
import androidx.room.Room
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceDao
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceDatabase
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideMyPlaceRepository(myPlaceDao: MyPlaceDao): MyPlaceRepository {
        return MyPlaceRepository(myPlaceDao = myPlaceDao)
    }

    @Provides
    fun provideMyPlaceDao(myPlaceDatabase: MyPlaceDatabase): MyPlaceDao {
        return myPlaceDatabase.myPlaceDao()
    }

    @Provides
    @Singleton
    fun providesMyPlaceDatabase(@ApplicationContext appContext: Context): MyPlaceDatabase {
        return Room.databaseBuilder(appContext, MyPlaceDatabase::class.java, "my_place_database")
            .build()
    }
}
