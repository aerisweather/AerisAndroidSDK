package com.example.demoaerisproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.preference.PrefManager;
import com.example.service.NotificationService;
import com.example.service.ScreenOnService;
import com.hamweather.aeris.communication.AerisEngine;

public class BaseApplication extends Application {

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

	public static void enableNotificationService(Context context, boolean enable) {
		Intent intent = new Intent(context.getApplicationContext(),
				ScreenOnService.class);

		if (enable) {
			context.startService(intent);
			context.startService(new Intent(context.getApplicationContext(),
					NotificationService.class));
		} else {
			AerisNotification.cancelNotification(context);
			context.stopService(intent);
		}
	}

}
