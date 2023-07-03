package com.xweather.sdkdemo.java.demoaerisproject;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.aerisweather.aeris.logging.Logger;
import com.xweather.sdkdemo.java.preference.PrefManager;
import com.xweather.sdkdemo.java.service.NotificationJobService;
import com.xweather.sdkdemo.java.service.NotificationService;

import com.aerisweather.aeris.communication.AerisEngine;
import com.aerisweather.aeris.maps.AerisMapsEngine;


public class BaseApplication extends Application {
    private static final int REQUEST_NTF_SERVICE = 10;
    public static final String PRIMARY_NOTIF_CHANNEL = "default";
    public static final int NOTIFICATION_JOB_ID = 2001;
    public static final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;
    private static final int ONE_MIN = 60 * 1000;
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // setting up secret key and client id for oauth to aeris
        AerisEngine.initWithKeys(this.getString(R.string.aerisapi_client_id), this.getString(R.string.aerisapi_client_secret), this);


        // Setting up default options from res values in maps sdk.
        enableNotificationService(this);

        /*
         * can override default point parameters programmatically used on the
         * map. dt:-1 -> sorts to closest time| -4hours -> 4 hours ago. Limit is
         * a required parameter.Can also be done through the xml values in the
         * aeris_default_values.xml
         */
        AerisMapsEngine.getInstance(this).getDefaultPointParameters().setLightningParameters("dt:-1", 500, null, null);
    }

    public static void enableNotificationService(Context context) {
        Logger.d(TAG, "enableNotificationService() - using JobScheduler");
        ComponentName notificationComponent = new ComponentName(context, NotificationJobService.class);
        JobInfo.Builder notificationBuilder = new JobInfo.Builder(NOTIFICATION_JOB_ID, notificationComponent)
                // schedule it to run any time between 15-20 minutes
                .setMinimumLatency(ONE_MIN * 1)
                .setOverrideDeadline(ONE_MIN * 2)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);
        JobScheduler notificationJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        notificationJobScheduler.schedule(notificationBuilder.build());
    }

}
