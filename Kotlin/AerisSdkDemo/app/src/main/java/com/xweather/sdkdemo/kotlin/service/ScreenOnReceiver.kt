package com.xweather.sdkdemo.kotlin.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aerisweather.aeris.logging.Logger

class ScreenOnReceiver : BroadcastReceiver() {

    /**
     * (non-Javadoc)
     *
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    override fun onReceive(context: Context, intent: Intent) {
        // if (SystemClock.elapsedRealtime()
        // - PrefManager.getLongPreference(context,
        // PrefManager.NTF_TIMESTAMP_KEY) > 1000 * 60 * 5) {
        Logger.i(TAG, "Screen on intent received")
        val newIntent = Intent(
            context.applicationContext,
            NotificationService::class.java,
        )
        context.startService(newIntent)
    }

    companion object {
        private const val TAG = "AERIS.ScreenOnReceiver"
    }
}
