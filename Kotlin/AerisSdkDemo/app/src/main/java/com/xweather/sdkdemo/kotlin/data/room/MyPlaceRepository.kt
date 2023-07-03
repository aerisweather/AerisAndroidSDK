package com.xweather.sdkdemo.kotlin.data.room

import androidx.lifecycle.asLiveData
import javax.inject.Inject

class MyPlaceRepository @Inject constructor(
    private val myPlaceDao: MyPlaceDao,
) {
    val allPlaces = myPlaceDao.getAllMyPlaces()

    fun getAll(): List<MyPlace> {
        return myPlaceDao.getAllMyPlaces().asLiveData().value ?: emptyList()
    }

    suspend fun insert(myPlace: MyPlace) {
        myPlaceDao.insert(myPlace)
    }

    suspend fun clear() {
        myPlaceDao.deleteAll()
    }

    suspend fun deleteByName(selected: String) {
        myPlaceDao.deleteByName(selected)
    }

    suspend fun update(myPlace: MyPlace) {
        myPlaceDao.update(myPlace)
    }

    suspend fun resetMyLocFalse() {
        myPlaceDao.resetMyLocFalse()
    }
}
