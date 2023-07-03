package com.xweather.sdkdemo.java.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aerisweather.aeris.logging.Logger;

public class ScreenOnReceiver extends BroadcastReceiver {

	private static final String TAG = "AERIS.ScreenOnReceiver";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		// if (SystemClock.elapsedRealtime()
		// - PrefManager.getLongPreference(context,
		// PrefManager.NTF_TIMESTAMP_KEY) > 1000 * 60 * 5) {
		Logger.i(TAG, "Screen on intent received");
		Intent newIntent = new Intent(context.getApplicationContext(),
				NotificationService.class);
		context.startService(newIntent);

		// }
	}
}
