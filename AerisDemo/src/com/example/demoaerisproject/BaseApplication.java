package com.example.demoaerisproject;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.preference.PrefManager;
import com.example.service.NotificationService;
import com.google.android.gms.analytics.Logger;
import com.aerisweather.aeris.communication.AerisEngine;
import com.aerisweather.aeris.logging.LogLevel;
import com.aerisweather.aeris.maps.AerisMapsEngine;

import org.json.JSONException;

public class BaseApplication extends Application {

	private static final int REQUEST_NTF_SERVICE = 10;

	@Override
	public void onCreate()
    {
		super.onCreate();

		// setting up secret key and client id for oauth to aeris
		AerisEngine.initWithKeys(this.getString(R.string.aerisapi_client_id), this.getString(R.string.aerisapi_client_secret), this);

		// Setting up default options from res values in maps sdk.
		enableNotificationService(this, PrefManager.getBoolPreference(this, getString(R.string.pref_ntf_enabled)));

		/*
		 * can override default point parameters programmatically used on the
		 * map. dt:-1 -> sorts to closest time| -4hours -> 4 hours ago. Limit is
		 * a required parameter.Can also be done through the xml values in the
		 * aeris_default_values.xml
		 */
		AerisMapsEngine.getInstance(this).getDefaultPointParameters().setLightningParameters("dt:-1", 500, null, "-4hours");
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
			manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0,
					AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
		} else {
			AerisNotification.cancelNotification(context);
			manager.cancel(pendingIntent);
		}

	}

}
