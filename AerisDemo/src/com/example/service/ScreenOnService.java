package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.hamweather.aeris.logging.Logger;

public class ScreenOnService extends Service {

	private static final String TAG = "AERIS.UserPresentService";
	private ScreenOnReceiver receiver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		receiver = new ScreenOnReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.setPriority(999);
		registerReceiver(receiver, filter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		Logger.d(TAG, "Screen On Service destroyed...");
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
