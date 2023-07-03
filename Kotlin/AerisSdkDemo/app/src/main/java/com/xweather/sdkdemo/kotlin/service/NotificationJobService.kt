package com.xweather.sdkdemo.kotlin.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.aerisweather.aeris.logging.Logger
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import com.xweather.sdkdemo.kotlin.data.weather.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class NotificationJobService : JobService() {

    @Inject
    lateinit var myPlaceRepository: MyPlaceRepository

    @Inject
    lateinit var prefStoreRepository: PrefStoreRepository

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var prefStore: PrefStoreRepository

    override fun onCreate() {
        super.onCreate()
        Logger.d(TAG, "onCreate()")
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    /**
     * onStartJob()
     * @return Always return true so that the job will be rescheduled to run again
     */
    private fun collectNotificationEnabled(): Boolean {
        var isEnabled: Boolean? = null
        val result = runBlocking {
            kotlin.runCatching {
                isEnabled =
                    prefStore.getBoolean(PrefStoreRepository.NOTIFICATION_ENABLED_KEY).firstOrNull()
            }.onFailure {
                Logger.d(TAG, "onStartJob() - exception: " + it.message)
            }
        }
        return result.isSuccess && isEnabled == true
    }

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        if (collectNotificationEnabled()) {
            Logger.d(TAG, "onStartJob()")
            NotificationBuilder(
                context = applicationContext,
                myPlaceRepository,
                prefStoreRepository,
                weatherRepository,
                prefStore,
            ).request()
            jobFinished(jobParameters, true)
        } else {
            AerisNotification().cancel(baseContext)
        }
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

    companion object {
        private val TAG = NotificationJobService::class.java.simpleName
    }
}
