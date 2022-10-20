package com.example.demoaerisproject.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.aerisweather.aeris.logging.Logger


class ScreenOnService : Service() {
    private var receiver: ScreenOnReceiver? = null

    /*
	 * (non-Javadoc)
	 *
	 * @see android.app.Service#onCreate()
	 */
    override fun onCreate() {
        super.onCreate()
        receiver = ScreenOnReceiver()
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.priority = 999
        registerReceiver(receiver, filter)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see android.app.Service#onDestroy()
	 */
    override fun onDestroy() {
        Logger.d(TAG, "Screen On Service destroyed...")
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    companion object {
        private const val TAG = "AERIS.UserPresentService"
    }
}
