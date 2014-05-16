package com.example.demoaerisproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.hamweather.aeris.model.ForecastPeriod;
import com.hamweather.aeris.model.Observation;
import com.hamweather.aeris.response.ForecastsResponse;
import com.hamweather.aeris.response.ObservationResponse;
import com.hamweather.aeris.util.FileUtil;
import com.hamweather.aeris.util.WeatherUtil;

public class AerisNotification {
	private static final int WEATHER_NTF = 100;
	private static NotificationCompat.Builder builder = null;
	private static RemoteViews remoteViews;

	/**
	 * Sets the notification for the upload.
	 * 
	 * @param working
	 *            true if the progress should show, false otherwise
	 * @param title
	 *            title to display on the notification
	 * @param message
	 *            message to display on the notification
	 * @param ticker
	 *            ticker message to display for the notification
	 */
	public static void setCustomNotification(Context context,
			ObservationResponse obResponse, ForecastsResponse fResponse) {

		Intent intent = new Intent(context, DrawerActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);

		if (builder == null) {
			builder = new NotificationCompat.Builder(context)
					// Set Ticker Message
					.setSmallIcon(R.drawable.ic_stat_action_about)
					.setContentIntent(pIntent).setOngoing(true)
					.setPriority(NotificationCompat.PRIORITY_HIGH)
					.setAutoCancel(false);

		}

		if (remoteViews == null) {
			remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.ntf_observation);
			builder.setContent(remoteViews);
		}
		Observation ob = obResponse.getObservation();
		remoteViews.setImageViewResource(R.id.ivNtfIcon,
				FileUtil.getDrawableByName(ob.icon, context));

		// remoteViews.setTextViewText(R.id.tvNtfDesc, ob.weather);
		Date date = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa",
				Locale.getDefault());
		remoteViews.setTextViewText(R.id.tvNtfDesc, timeFormat.format(date));

		remoteViews.setTextViewText(R.id.tvNtfTemp,
				WeatherUtil.appendDegree(ob.tempF));

		ForecastPeriod period = fResponse.getPeriod(0);
		// reverses orday if isday is not true.
		if (period.isDay) {
			remoteViews.setTextViewText(R.id.tvNtfHigh,
					WeatherUtil.appendDegree(fResponse.getPeriod(0).maxTempF));
			remoteViews.setTextViewText(R.id.tvNtfLow,
					WeatherUtil.appendDegree(fResponse.getPeriod(1).minTempF));

		} else {
			remoteViews.setTextViewText(R.id.tvNtfHigh,
					WeatherUtil.appendDegree(fResponse.getPeriod(1).maxTempF));
			remoteViews.setTextViewText(R.id.tvNtfLow,
					WeatherUtil.appendDegree(fResponse.getPeriod(2).minTempF));
		}

		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager

		notificationmanager.notify(WEATHER_NTF, builder.build());
	}

	public static void cancelNotification(Context context) {
		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.cancel(WEATHER_NTF);
		remoteViews = null;
		builder = null;
	}
}
