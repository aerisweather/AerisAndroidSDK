package com.example.demoaerisproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.preference.PrefManager;
import com.example.service.NotificationService;
import com.hamweather.aeris.communication.AerisEngine;

public class BaseApplication extends Application {

	private static final int REQUEST_WEATHER_NTF = 10;

	@Override
	public void onCreate() {
		super.onCreate();
		// setting up secret key and client id for oauth to aeris
		AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id),
				this.getString(R.string.aeris_client_secret), this);
		// Setting up default options from res values in maps sdk.
		enableNotificationService(this, PrefManager.getBoolPreference(this,
				getString(R.string.pref_ntf_enabled)));

	}

	public static void enableNotificationService(Context activity,
			boolean enable) {
		Intent intent = new Intent(activity.getApplicationContext(),
				NotificationService.class);
		PendingIntent sender = PendingIntent.getBroadcast(
				activity.getApplicationContext(), REQUEST_WEATHER_NTF, intent,
				0);
		AlarmManager am = (AlarmManager) activity
				.getSystemService(Activity.ALARM_SERVICE);
		if (enable) {
			am.setRepeating(AlarmManager.ELAPSED_REALTIME,
					SystemClock.elapsedRealtime(),
					AlarmManager.INTERVAL_FIFTEEN_MINUTES, sender); //
			activity.startService(intent);
		} else {
			// stop service
			activity.stopService(intent);
			am.cancel(sender);
			AerisNotification.cancelNotification(activity);
		}
	}

}
