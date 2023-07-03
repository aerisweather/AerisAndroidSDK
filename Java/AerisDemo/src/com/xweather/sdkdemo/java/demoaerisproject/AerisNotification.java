package com.xweather.sdkdemo.java.demoaerisproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.widget.RemoteViews;

import com.aerisweather.aeris.logging.Logger;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.model.Observation;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;

import java.util.HashMap;
import java.util.Map;

import static com.xweather.sdkdemo.java.demoaerisproject.BaseApplication.PRIMARY_FOREGROUND_NOTIF_SERVICE_ID;
import static com.xweather.sdkdemo.java.demoaerisproject.BaseApplication.PRIMARY_NOTIF_CHANNEL;

public class AerisNotification {
    private static final int WEATHER_NTF = 100;
    private static NotificationCompat.Builder builder = null;
    private static RemoteViews remoteViews;
    private static Map<String, Integer> map;
    private static final String TAG = AerisNotification.class.getSimpleName();

    private static String CHANNEL_ID = "channelID";
    private static String CHANNEL_NAME = "channelName";

    /**
     * Sets the notification for the observation
     */
    public static void setCustomNotification(Context context, ObservationResponse obResponse, ForecastsResponse fResponse) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager mgr = (NotificationManager) context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE);
        mgr.createNotificationChannel(channel);

        Logger.d(TAG, "setCustomNotification()");
        Intent intent = new Intent(context, DrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if (builder == null) {
        	/*
			builder = new NotificationCompat.Builder(context)
					// Set Ticker Message
					.setSmallIcon(R.drawable.ic_stat_action_about)
					.setContentIntent(pIntent).setOngoing(true)
					.setPriority(NotificationCompat.PRIORITY_HIGH)
					.setAutoCancel(false);
			*/
            String title = context.getString(R.string.app_name);
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setSmallIcon(getDrawableByName(obResponse.getObservation().icon))
                    .setOngoing(true);
        }

        if (remoteViews == null) {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.ntf_observation);
            builder.setContent(remoteViews);
        }

        Observation ob = obResponse.getObservation();
        remoteViews.setImageViewResource(R.id.ivNtfIcon, FileUtil.getDrawableByName(ob.icon, context));
        remoteViews.setTextViewText(R.id.tvNtfDesc, ob.weather);
        remoteViews.setTextViewText(R.id.tvNtfTemp, WeatherUtil.appendDegree(ob.tempF));
        builder.setContentText(ob.weather + " " +ob.tempF);

        ForecastPeriod period = fResponse.getPeriod(0);

        // reverses orday if isday is not true.
        if (period.isDay) {
            remoteViews.setTextViewText(R.id.tvNtfHigh, WeatherUtil.appendDegree(fResponse.getPeriod(0).maxTempF));
            remoteViews.setTextViewText(R.id.tvNtfLow, WeatherUtil.appendDegree(fResponse.getPeriod(1).minTempF));
        } else {
            try {
                remoteViews.setTextViewText(R.id.tvNtfHigh, WeatherUtil.appendDegree(fResponse.getPeriod(1).maxTempF));
                remoteViews.setTextViewText(R.id.tvNtfLow, WeatherUtil.appendDegree(fResponse.getPeriod(2).minTempF));
            } catch (Exception e) {
                Log.d("AerisNotification", "remoteViews failed:" + e);
            }
        }

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Build Notification with Notification Manager
        //notificationmanager.notify(WEATHER_NTF, builder.build());
        notificationmanager.notify(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, builder.build());
    }

    public static void cancelNotification(Context context) {
        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Build Notification with Notification Manager
        //notificationmanager.cancel(WEATHER_NTF);
        notificationmanager.cancel(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID);
        remoteViews = null;
        builder = null;
    }

    /**
     * Gets the drawable by the string name. This is used in conjunction with
     * the icon field in many of the forecast, observation. Accesses the name
     * from the drawable folder.
     *
     * @param name name of the drawable
     * @return the int key of the drawable.
     */
    public static int getDrawableByName(String name) {
        if (name == null) {
            return 0;
        }
        String iconName = name.substring(0, name.indexOf("."));

        Logger.d(TAG, "getDrawableByName() - ObIcon: " + iconName);

        Integer item = getMap().get(iconName);
        if (item != null) {
            return item;
        } else {
            if (iconName.charAt(iconName.length() - 1) == 'n') {
                item = getMap().get(iconName.substring(0, iconName.length() - 1));
                if (item != null) {
                    return item;
                }
            }
        }
        return 0;
    }

    public static Map<String, Integer> getMap() {
        if (map == null) {
            map = new HashMap<String, Integer>();

            map.put("am_pcloudyr", R.drawable.mcloudyr);
            map.put("am_showers", R.drawable.showers);
            map.put("am_snowshowers", R.drawable.showers);
            map.put("am_tstorm", R.drawable.tstorms);
            map.put("blizzard", R.drawable.blizzard);
            map.put("blizzardn", R.drawable.blizzard);
            map.put("blowingsnow", R.drawable.snoww);
            map.put("blowingsnown", R.drawable.snoww);
            map.put("chancestorm", R.drawable.tstorms);
            map.put("chancestormn", R.drawable.tstorms);
            map.put("clear", R.drawable.sunny);
            map.put("clearn", R.drawable.clearn);
            map.put("clearw", R.drawable.wind);
            map.put("clearwn", R.drawable.wind);
            map.put("cloudy", R.drawable.cloudy);
            map.put("cloudyn", R.drawable.cloudy);
            map.put("cloudyw", R.drawable.wind);
            map.put("cloudywn", R.drawable.wind);
            map.put("drizzle", R.drawable.drizzle);
            map.put("drizzlef", R.drawable.drizzle);
            map.put("drizzlefn", R.drawable.drizzle);
            map.put("drizzlen", R.drawable.drizzle);
            map.put("dust", R.drawable.hazy);
            map.put("dustn", R.drawable.hazyn);
            map.put("fair", R.drawable.sunny);
            map.put("fairn", R.drawable.clearn);
            map.put("fairnw", R.drawable.wind);
            map.put("fairnwn", R.drawable.wind);
            map.put("fdrizzle", R.drawable.drizzle);
            map.put("fdrizzlen", R.drawable.drizzle);
            map.put("flurries", R.drawable.flurries);
            map.put("flurriesn", R.drawable.flurries);
            map.put("flurriesw", R.drawable.flurries);
            map.put("flurrieswn", R.drawable.flurries);
            map.put("fog", R.drawable.fog);
            map.put("fogn", R.drawable.fog);
            map.put("freezingrain", R.drawable.rain);
            map.put("freezingrainn", R.drawable.rain);
            map.put("hazy", R.drawable.hazy);
            map.put("hazyn", R.drawable.hazyn);
            map.put("mcloudy", R.drawable.mcloudy);
            map.put("mcloudyn", R.drawable.mcloudyn);
            map.put("mcloudyr", R.drawable.mcloudyr);
            map.put("mcloudyrn", R.drawable.mcloudyrn);
            map.put("mcloudyrw", R.drawable.mcloudyrw);
            map.put("mcloudyrwn", R.drawable.mcloudyrwn);
            map.put("mcloudys", R.drawable.mcloudys);
            map.put("mcloudysfn", R.drawable.mcloudysn);
            map.put("mcloudysfw", R.drawable.mcloudysw);
            map.put("mcloudysfwn", R.drawable.mcloudyswn);
            map.put("mcloudysn", R.drawable.mcloudysn);
            map.put("mcloudysw", R.drawable.mcloudysw);
            map.put("mcloudyswn", R.drawable.mcloudyswn);
            map.put("mcloudyt", R.drawable.mcloudyt);
            map.put("mcloudytn", R.drawable.mcloudytn);
            map.put("mcloudytw", R.drawable.mcloudytw);
            map.put("mcloudytwn", R.drawable.mcloudytwn);
            map.put("mcloudyw", R.drawable.wind);
            map.put("mcloudywn", R.drawable.wind);
            map.put("pcloudy", R.drawable.pcloudy);
            map.put("pcloudyn", R.drawable.pcloudyn);
            map.put("pcloudyr", R.drawable.mcloudyr);
            map.put("pcloudyrn", R.drawable.mcloudyrn);
            map.put("pcloudyrw", R.drawable.mcloudyrw);
            map.put("pcloudyrwn", R.drawable.mcloudyrwn);
            map.put("pcloudys", R.drawable.mcloudys);
            map.put("pcloudysf", R.drawable.mcloudys);
            map.put("pcloudysfn", R.drawable.mcloudysn);
            map.put("pcloudysfw", R.drawable.snoww);
            map.put("pcloudysfwn", R.drawable.snoww);
            map.put("pcloudysn", R.drawable.mcloudysn);
            map.put("pcloudysw", R.drawable.mcloudysw);
            map.put("pcloudyswn", R.drawable.mcloudyswn);
            map.put("pcloudyt", R.drawable.mcloudyt);
            map.put("pcloudytn", R.drawable.mcloudytn);
            map.put("pcloudytw", R.drawable.mcloudytw);
            map.put("pcloudytwn", R.drawable.mcloudytwn);
            map.put("pcloudyw", R.drawable.wind);
            map.put("pcloudywn", R.drawable.wind);
            map.put("pm_pcloudy", R.drawable.pcloudy);
            map.put("pm_pcloudyr", R.drawable.mcloudyr);
            map.put("pm_showers", R.drawable.showers);
            map.put("pm_snowshowers", R.drawable.snowshowers);
            map.put("pm_tstorm", R.drawable.tstorms);
            map.put("rain", R.drawable.rain);
            map.put("rainn", R.drawable.rain);
            map.put("rainandsnow", R.drawable.rainandsnow);
            map.put("rainandsnown", R.drawable.rainandsnow);
            map.put("raintosnow", R.drawable.rainandsnow);
            map.put("raintosnown", R.drawable.rainandsnow);
            map.put("rainw", R.drawable.rainw);
            map.put("showers", R.drawable.showers);
            map.put("showersn", R.drawable.showers);
            map.put("showersw", R.drawable.showersw);
            map.put("showerswn", R.drawable.showersw);
            map.put("sleet", R.drawable.sleet);
            map.put("sleetn", R.drawable.sleet);
            map.put("sleetsnow", R.drawable.sleetsnow);
            map.put("sleetsnown", R.drawable.sleetsnow);
            map.put("smoke", R.drawable.hazy);
            map.put("smoken", R.drawable.hazyn);
            map.put("snow", R.drawable.snow);
            map.put("snown", R.drawable.snow);
            map.put("snowflurries", R.drawable.flurries);
            map.put("snowflurriesn", R.drawable.flurries);
            map.put("snowshowers", R.drawable.snowshowers);
            map.put("snowshowersn", R.drawable.snowshowers);
            map.put("snowshowersw", R.drawable.snowshowers);
            map.put("snowshowerswn", R.drawable.snowshowersn);
            map.put("snowtorain", R.drawable.rainandsnow);
            map.put("snowtorainn", R.drawable.rainandsnown);
            map.put("snoww", R.drawable.snoww);
            map.put("snowwn", R.drawable.snoww);
            map.put("sunny", R.drawable.sunny);
            map.put("sunnyn", R.drawable.clearn);
            map.put("sunnyw", R.drawable.wind);
            map.put("sunnywn", R.drawable.wind);
            map.put("tstorm", R.drawable.tstorms);
            map.put("tstormn", R.drawable.tstorms);
            map.put("tstormw", R.drawable.tstormsw);
            map.put("tstormwn", R.drawable.tstormsw);
            map.put("tstorms", R.drawable.tstorms);
            map.put("tstormsn", R.drawable.tstorms);
            map.put("tstormsw", R.drawable.tstormsw);
            map.put("tstormswn", R.drawable.tstormsw);
            map.put("wind", R.drawable.wind);
            map.put("wintrymix", R.drawable.wintrymix);
            map.put("wintrymixn", R.drawable.wintrymix);
        }

        return map;
    }
}
