package com.example.demoaerisproject.service

import android.app.job.JobParameters
import android.app.job.JobService
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : JobService() {

    @Inject
    lateinit var myPlaceRepository: MyPlaceRepository

    @Inject
    lateinit var prefStoreRepository: PrefStoreRepository

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var prefStore: PrefStoreRepository

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        NotificationBuilder(
            context = applicationContext,
            myPlaceRepository,
            prefStoreRepository,
            weatherRepository,
            prefStore
        ).request()
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }
}