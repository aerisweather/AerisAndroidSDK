package com.xweather.sdkdemo.java.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import com.xweather.sdkdemo.java.db.MyPlacesDb;
import com.xweather.sdkdemo.java.demoaerisproject.AerisNotification;
import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.xweather.sdkdemo.java.preference.PrefManager;


public class NotificationJobService extends JobService
{
	private static final String TAG = "NotificationJobService";// NotificationJobService.class.getSimpleName();

	public NotificationJobService()
	{
		super();
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d(TAG, "onCreate()");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	/**
	 * onStartJob()
	 * @param jobParameters
	 * @return Always return true so that the job will be rescheduled to run again
	 */
	@Override
	public boolean onStartJob(JobParameters jobParameters)
	{
		Log.d(TAG, "onStartJob() starting...");
		Boolean isEnabled = PrefManager.getBoolPreference(this, getString(R.string.pref_ntf_enabled));
		if (isEnabled) {
			try {
				BatchBuilder builder = new BatchBuilder();

				MyPlacesDb db = new MyPlacesDb(this);
				PlaceParameter place = db.getMyPlaceParameter();
				db.close();

				if (place == null) {
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

				if (retval.responses != null && retval.responses.size() == 2) {
					ObservationResponse obResponse = new ObservationResponse(retval.responses.get(0).getFirstResponse());
					ForecastsResponse fResponse = new ForecastsResponse(retval.responses.get(1).getFirstResponse());
					AerisNotification.setCustomNotification(this, obResponse, fResponse);
					PrefManager.setLongPreference(this, PrefManager.NTF_TIMESTAMP_KEY, SystemClock.elapsedRealtime());
				} else {
					Log.d(TAG, "retval.response is null or size != 2 ");
				}
			} catch (Exception ex) {
				Log.d(TAG, "onStartJob() - exception: " + ex.getMessage());
				return true;
			} finally {
				jobFinished(jobParameters, true);
			}
		}
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters)
	{
		return false;
	}

}
