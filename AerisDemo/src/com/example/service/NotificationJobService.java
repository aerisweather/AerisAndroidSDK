package com.example.service;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.aerisweather.aeris.communication.Action;
import com.aerisweather.aeris.communication.AerisRequest;
import com.aerisweather.aeris.communication.BatchBuilder;
import com.aerisweather.aeris.communication.BatchCommunicationTask;
import com.aerisweather.aeris.communication.Endpoint;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.communication.fields.Fields;
import com.aerisweather.aeris.communication.fields.ForecastsFields;
import com.aerisweather.aeris.communication.fields.ObservationFields;
import com.aerisweather.aeris.communication.parameter.FieldsParameter;
import com.aerisweather.aeris.communication.parameter.FilterParameter;
import com.aerisweather.aeris.communication.parameter.PLimitParameter;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;
import com.aerisweather.aeris.logging.Logger;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.AerisNotification;
import com.example.preference.PrefManager;

import static com.example.demoaerisproject.BaseApplication.PRIMARY_FOREGROUND_NOTIF_SERVICE_ID;
import static com.example.demoaerisproject.BaseApplication.PRIMARY_NOTIF_CHANNEL;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService
{
	private static final String TAG = NotificationJobService.class.getSimpleName();

	public NotificationJobService()
	{
		super();
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Logger.d(TAG, "onCreate()");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		if (Build.VERSION.SDK_INT >= 26)
		{
			Notification notification = new NotificationCompat.Builder(this, PRIMARY_NOTIF_CHANNEL)
					.setSmallIcon(com.aerisweather.R.drawable.clear)
					.setPriority(NotificationCompat.PRIORITY_MIN)
					.build();

			startForeground(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
			Logger.d(TAG, "onCreate() - foreground service started");
		}
	}

	/**
	 * onStartJob()
	 * @param jobParameters
	 * @return Always return true so that the job will be rescheduled to run again
	 */
	@Override
	public boolean onStartJob(JobParameters jobParameters)
	{
		Logger.d(TAG, "onStartJob()");
		try
		{
			BatchBuilder builder = new BatchBuilder();

			MyPlacesDb db = new MyPlacesDb(this);
			PlaceParameter place = db.getMyPlaceParameter();
			db.close();

			if (place == null)
			{
				place = new PlaceParameter(this);
			}

			builder.addGlobalParameter(place);

			builder.addEndpoint(new Endpoint(EndpointType.OBSERVATIONS,
					Action.CLOSEST).addParameters(FieldsParameter.initWith(
					ObservationFields.ICON, ObservationFields.TEMP_F,
					ObservationFields.WEATHER, ObservationFields.TEMP_C,
					ObservationFields.WEATHER_SHORT)));

			builder.addEndpoint(new Endpoint(EndpointType.FORECASTS, Action.CLOSEST)
					.addParameters(
							FieldsParameter.initWith(Fields.INTERVAL,
									ForecastsFields.IS_DAY, ForecastsFields.MAX_TEMP_F,
									ForecastsFields.MIN_TEMP_F, ForecastsFields.MIN_TEMP_C,
									ForecastsFields.MAX_TEMP_C), new FilterParameter(
									"daynight"), new PLimitParameter(2)));

			AerisRequest request = builder.build();
			request.withDebugOutput(true);
			BatchCommunicationTask task = new BatchCommunicationTask(this, request);

			AerisBatchResponse retval = task.executeSyncTask();
			if (retval.responses != null && retval.responses.size() == 2)
			{
				ObservationResponse obResponse = new ObservationResponse(retval.responses.get(0).getFirstResponse());
				ForecastsResponse fResponse = new ForecastsResponse(retval.responses.get(1).getFirstResponse());
				AerisNotification.setCustomNotification(this, obResponse, fResponse);
				PrefManager.setLongPreference(this, PrefManager.NTF_TIMESTAMP_KEY,	SystemClock.elapsedRealtime());
			}
		}
		catch(Exception ex)
		{
			Logger.d(TAG, "onStartJob() - exception: " + ex.getMessage());
			return true;
		}
		finally
		{
			jobFinished(jobParameters, true);
			Logger.d(TAG, "onStartJob() - jobFinished");
		}

		return true;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters)
	{
		return false;
	}

}
