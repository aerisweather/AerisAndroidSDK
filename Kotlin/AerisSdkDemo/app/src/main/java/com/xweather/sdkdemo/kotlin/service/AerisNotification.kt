package com.xweather.sdkdemo.kotlin.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.aerisweather.aeris.logging.Logger
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.aerisweather.aeris.util.FileUtil
import com.xweather.sdkdemo.kotlin.BaseApplication
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.util.FormatUtil
import com.xweather.sdkdemo.kotlin.view.weather.MainActivity

class AerisNotification {
    private var builder: NotificationCompat.Builder? = null
    private var remoteViews: RemoteViews? = null
    var map = HashMap<String, Int>()
        get() {
            field["am_pcloudyr"] = R.drawable.mcloudyr
            field["am_showers"] = R.drawable.showers
            field["am_snowshowers"] = R.drawable.showers
            field["am_tstorm"] = R.drawable.tstorms
            field["blizzard"] = R.drawable.blizzard
            field["blizzardn"] = R.drawable.blizzard
            field["blowingsnow"] = R.drawable.snoww
            field["blowingsnown"] = R.drawable.snoww
            field["chancestorm"] = R.drawable.tstorms
            field["chancestormn"] = R.drawable.tstorms
            field["clear"] = R.drawable.sunny
            field["clearn"] = R.drawable.clearn
            field["clearw"] = R.drawable.wind
            field["clearwn"] = R.drawable.wind
            field["cloudy"] = R.drawable.cloudy
            field["cloudyn"] = R.drawable.cloudy
            field["cloudyw"] = R.drawable.wind
            field["cloudywn"] = R.drawable.wind
            field["drizzle"] = R.drawable.drizzle
            field["drizzlef"] = R.drawable.drizzle
            field["drizzlefn"] = R.drawable.drizzle
            field["drizzlen"] = R.drawable.drizzle
            field["dust"] = R.drawable.hazy
            field["dustn"] = R.drawable.hazyn
            field["fair"] = R.drawable.sunny
            field["fairn"] = R.drawable.clearn
            field["fairnw"] = R.drawable.wind
            field["fairnwn"] = R.drawable.wind
            field["fdrizzle"] = R.drawable.drizzle
            field["fdrizzlen"] = R.drawable.drizzle
            field["flurries"] = R.drawable.flurries
            field["flurriesn"] = R.drawable.flurries
            field["flurriesw"] = R.drawable.flurries
            field["flurrieswn"] = R.drawable.flurries
            field["fog"] = R.drawable.fog
            field["fogn"] = R.drawable.fog
            field["freezingrain"] = R.drawable.rain
            field["freezingrainn"] = R.drawable.rain
            field["hazy"] = R.drawable.hazy
            field["hazyn"] = R.drawable.hazyn
            field["mcloudy"] = R.drawable.mcloudy
            field["mcloudyn"] = R.drawable.mcloudyn
            field["mcloudyr"] = R.drawable.mcloudyr
            field["mcloudyrn"] = R.drawable.mcloudyrn
            field["mcloudyrw"] = R.drawable.mcloudyrw
            field["mcloudyrwn"] = R.drawable.mcloudyrwn
            field["mcloudys"] = R.drawable.mcloudys
            field["mcloudysfn"] = R.drawable.mcloudysn
            field["mcloudysfw"] = R.drawable.mcloudysw
            field["mcloudysfwn"] = R.drawable.mcloudyswn
            field["mcloudysn"] = R.drawable.mcloudysn
            field["mcloudysw"] = R.drawable.mcloudysw
            field["mcloudyswn"] = R.drawable.mcloudyswn
            field["mcloudyt"] = R.drawable.mcloudyt
            field["mcloudytn"] = R.drawable.mcloudytn
            field["mcloudytw"] = R.drawable.mcloudytw
            field["mcloudytwn"] = R.drawable.mcloudytwn
            field["mcloudyw"] = R.drawable.wind
            field["mcloudywn"] = R.drawable.wind
            field["pcloudy"] = R.drawable.pcloudy
            field["pcloudyn"] = R.drawable.pcloudyn
            field["pcloudyr"] = R.drawable.mcloudyr
            field["pcloudyrn"] = R.drawable.mcloudyrn
            field["pcloudyrw"] = R.drawable.mcloudyrw
            field["pcloudyrwn"] = R.drawable.mcloudyrwn
            field["pcloudys"] = R.drawable.mcloudys
            field["pcloudysf"] = R.drawable.mcloudys
            field["pcloudysfn"] = R.drawable.mcloudysn
            field["pcloudysfw"] = R.drawable.snoww
            field["pcloudysfwn"] = R.drawable.snoww
            field["pcloudysn"] = R.drawable.mcloudysn
            field["pcloudysw"] = R.drawable.mcloudysw
            field["pcloudyswn"] = R.drawable.mcloudyswn
            field["pcloudyt"] = R.drawable.mcloudyt
            field["pcloudytn"] = R.drawable.mcloudytn
            field["pcloudytw"] = R.drawable.mcloudytw
            field["pcloudytwn"] = R.drawable.mcloudytwn
            field["pcloudyw"] = R.drawable.wind
            field["pcloudywn"] = R.drawable.wind
            field["pm_pcloudy"] = R.drawable.pcloudy
            field["pm_pcloudyr"] = R.drawable.mcloudyr
            field["pm_showers"] = R.drawable.showers
            field["pm_snowshowers"] = R.drawable.snowshowers
            field["pm_tstorm"] = R.drawable.tstorms
            field["rain"] = R.drawable.rain
            field["rainn"] = R.drawable.rain
            field["rainandsnow"] = R.drawable.rainandsnow
            field["rainandsnown"] = R.drawable.rainandsnow
            field["raintosnow"] = R.drawable.rainandsnow
            field["raintosnown"] = R.drawable.rainandsnow
            field["rainw"] = R.drawable.rainw
            field["showers"] = R.drawable.showers
            field["showersn"] = R.drawable.showers
            field["showersw"] = R.drawable.showersw
            field["showerswn"] = R.drawable.showersw
            field["sleet"] = R.drawable.sleet
            field["sleetn"] = R.drawable.sleet
            field["sleetsnow"] = R.drawable.sleetsnow
            field["sleetsnown"] = R.drawable.sleetsnow
            field["smoke"] = R.drawable.hazy
            field["smoken"] = R.drawable.hazyn
            field["snow"] = R.drawable.snow
            field["snown"] = R.drawable.snow
            field["snowflurries"] = R.drawable.flurries
            field["snowflurriesn"] = R.drawable.flurries
            field["snowshowers"] = R.drawable.snowshowers
            field["snowshowersn"] = R.drawable.snowshowers
            field["snowshowersw"] = R.drawable.snowshowers
            field["snowshowerswn"] = R.drawable.snowshowersn
            field["snowtorain"] = R.drawable.rainandsnow
            field["snowtorainn"] = R.drawable.rainandsnown
            field["snoww"] = R.drawable.snoww
            field["snowwn"] = R.drawable.snoww
            field["sunny"] = R.drawable.sunny
            field["sunnyn"] = R.drawable.clearn
            field["sunnyw"] = R.drawable.wind
            field["sunnywn"] = R.drawable.wind
            field["tstorm"] = R.drawable.tstorms
            field["tstormn"] = R.drawable.tstorms
            field["tstormw"] = R.drawable.tstormsw
            field["tstormwn"] = R.drawable.tstormsw
            field["tstorms"] = R.drawable.tstorms
            field["tstormsn"] = R.drawable.tstorms
            field["tstormsw"] = R.drawable.tstormsw
            field["tstormswn"] = R.drawable.tstormsw
            field["wind"] = R.drawable.wind
            field["wintrymix"] = R.drawable.wintrymix
            field["wintrymixn"] = R.drawable.wintrymix
            return field
        }
        private set
    private val TAG = AerisNotification::class.java.simpleName

    /**
     * Sets the notification for the observation
     */
    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"

    fun setCustom(
        context: Context,
        isMetrics: Boolean,
        obResponse: ObservationResponse,
        fResponse: ForecastsResponse,
    ) {
        createNotifChannel(context)

        Logger.d(TAG, "setCustomNotification()")

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

        val ob = obResponse.observation
        if (builder == null) {
            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.resources.getString(R.string.app_name))
                .setSmallIcon(getDrawableByName(obResponse.observation.icon))
                .setOngoing(true)
        }

        if (remoteViews == null) {
            remoteViews = RemoteViews(context.packageName, R.layout.ntf_observation)
            builder?.setContent(remoteViews)
        }

        remoteViews?.apply {
            setImageViewResource(
                R.id.ivNtfIcon,
                FileUtil.getDrawableByName(ob.icon, context),
            )
            setTextViewText(R.id.tvNtfDesc, ob.weather)
            val printTemp = FormatUtil.printDegree(context, isMetrics, Pair(ob.tempC, ob.tempF))
            builder?.setContentText(ob.weather.toString() + " " + printTemp)
            setTextViewText(R.id.tvNtfTemp, printTemp)
            val period = fResponse.getPeriod(0)

            // reverses order if isday is not true.
            val indexes = if (period.isDay) {
                listOf(0, 1)
            } else {
                listOf(1, 0)
            }

            fResponse.getPeriod(indexes[0]).let {
                setTextViewText(
                    R.id.tvNtfHigh,
                    FormatUtil.printDegree(context, isMetrics, Pair(it.maxTempC, it.maxTempF)),
                )
            }

            fResponse.getPeriod(indexes[1]).let {
                setTextViewText(
                    R.id.tvNtfLow,
                    FormatUtil.printDegree(context, isMetrics, Pair(it.minTempC, it.minTempF)),
                )
            }
        }

        // Create Notification Manager
        val notificationmanager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationmanager.notify(
            BaseApplication.PRIMARY_FOREGROUND_NOTIF_SERVICE_ID,
            builder?.build(),
        )
    }

    private fun createNotifChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        val manager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun cancel(context: Context) {
        // Create Notification Manager
        val notificationmanager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Build Notification with Notification Manager
        // notificationmanager.cancel(WEATHER_NTF);
        notificationmanager.cancel(BaseApplication.PRIMARY_FOREGROUND_NOTIF_SERVICE_ID)
        remoteViews = null
        builder = null
    }

    /**
     * Gets the drawable by the string name. This is used in conjunction with
     * the icon field in many of the forecast, observation. Accesses the name
     * from the drawable folder.
     *
     * @param name name of the drawable
     * @return the int key of the drawable.
     */
    private fun getDrawableByName(name: String?): Int {
        if (name == null) {
            return 0
        }
        val iconName = name.substring(0, name.indexOf("."))
        Logger.d(
            TAG,
            "getDrawableByName() - ObIcon: $iconName",
        )
        return map[iconName] ?: if (iconName[iconName.length - 1] == 'n') {
            map[iconName.substring(0, iconName.length - 1)] ?: 0
        } else {
            0
        }
    }
}
