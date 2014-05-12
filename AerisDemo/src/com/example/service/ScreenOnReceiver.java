package com.example.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.preference.PrefManager;

public class ScreenOnReceiver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		if (SystemClock.elapsedRealtime()
				- PrefManager.getLongPreference(context,
						PrefManager.NTF_TIMESTAMP_KEY) > 1000 * 60 * 5) {
			Intent newIntent = new Intent(context, NotificationService.class);
			context.startService(newIntent);

		}
	}
}
