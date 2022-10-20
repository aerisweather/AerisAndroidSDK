package com.example.demoaerisproject

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.aerisweather.aeris.communication.AerisEngine
import com.aerisweather.aeris.logging.Logger
import com.aerisweather.aeris.maps.AerisMapsEngine
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.service.NotificationJobService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var prefStore: PrefStoreRepository

    override fun onCreate() {
        super.onCreate()

        // setting up secret key and client id for oauth to aeris
        AerisEngine.initWithKeys(
            this.getString(R.string.aerisapi_client_id),
            this.getString(R.string.aerisapi_client_secret),
            this
        )

        enableNotificationService(baseContext)

        /*
		 * can override default point parameters programmatically used on the
		 * map. dt:-1 -> sorts to closest time| -4hours -> 4 hours ago. Limit is
		 * a required parameter.Can also be done through the xml values in the
		 * aeris_default_values.xml
		 */
        AerisMapsEngine.getInstance(this).defaultPointParameters
            .setLightningParameters("dt:-1", 500, null, null)
    }

    companion object {
        private const val NOTIFICATION_JOB_ID = 2001
        const val PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001
        private const val ONE_MIN = 60 * 1000
        private val TAG = BaseApplication::class.java.simpleName

        fun enableNotificationService(context: Context) {

            Logger.d(TAG, "enableNotificationService() - using JobScheduler")
            val notificationComponent = ComponentName(
                context,
                NotificationJobService::class.java
            )
            val notificationBuilder = JobInfo.Builder(
                NOTIFICATION_JOB_ID,
                notificationComponent
            ) // schedule it to run any time between 15-20 minutes
                .setMinimumLatency((ONE_MIN * 1).toLong())
                .setOverrideDeadline((ONE_MIN * 2).toLong())
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
            val notificationJobScheduler =
                context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            notificationJobScheduler.schedule(notificationBuilder.build())
        }
    }
}