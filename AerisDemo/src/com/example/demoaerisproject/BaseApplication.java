package com.example.demoaerisproject;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.preference.PrefManager;
import com.example.service.NotificationService;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.logging.Logger;

public class BaseApplication extends Application {

	private static final int REQUEST_NTF_SERVICE = 10;

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
				NotificationService.class);
		// if (Build.VERSION.SDK_INT > 18) {
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getService(context,
				REQUEST_NTF_SERVICE, intent, 0);
		if (enable) {
			Logger.d("TEST", "NTF started -->");

			manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0,
					AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
		} else {
			Logger.d("TEST", "NTF canceled -->");

			AerisNotification.cancelNotification(context);
			manager.cancel(pendingIntent);
		}
		// } else {
		// if (enable) {
		// context.startService(intent);
		// context.startService(new Intent(
		// context.getApplicationContext(),
		// NotificationService.class));
		// } else {
		// AerisNotification.cancelNotification(context);
		// context.stopService(intent);
		// }
		// }

	}

}
