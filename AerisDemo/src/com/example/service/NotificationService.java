package com.example.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.AerisNotification;
import com.example.preference.PrefManager;
import com.hamweather.aeris.communication.Action;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.communication.BatchBuilder;
import com.hamweather.aeris.communication.BatchCommunicationTask;
import com.hamweather.aeris.communication.Endpoint;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.fields.Fields;
import com.hamweather.aeris.communication.fields.ForecastsFields;
import com.hamweather.aeris.communication.fields.ObservationFields;
import com.hamweather.aeris.communication.parameter.FieldsParameter;
import com.hamweather.aeris.communication.parameter.FilterParameter;
import com.hamweather.aeris.communication.parameter.PLimitParameter;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.model.AerisBatchResponse;
import com.hamweather.aeris.response.ForecastsResponse;
import com.hamweather.aeris.response.ObservationResponse;

public class NotificationService extends IntentService {

	public NotificationService() {
		super("ntf_service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
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
		BatchCommunicationTask task = new BatchCommunicationTask(this, request);
		AerisBatchResponse retval = task.executeSyncTask();
		if (retval.responses != null && retval.responses.size() == 2) {
			ObservationResponse obResponse = new ObservationResponse(
					retval.responses.get(0).getFirstResponse());
			ForecastsResponse fResponse = new ForecastsResponse(
					retval.responses.get(1).getFirstResponse());
			AerisNotification
					.setCustomNotification(this, obResponse, fResponse);
			PrefManager.setLongPreference(this, PrefManager.NTF_TIMESTAMP_KEY,
					SystemClock.elapsedRealtime());
		}

	}
}
