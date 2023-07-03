package com.xweather.sdkdemo.java.service;

import android.app.IntentService;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.SystemClock;

import com.aerisweather.aeris.logging.Logger;
import com.xweather.sdkdemo.java.db.MyPlacesDb;
import com.xweather.sdkdemo.java.demoaerisproject.AerisNotification;
import com.xweather.sdkdemo.java.preference.PrefManager;
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
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;

public class NotificationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Logger.d("NotificationService", "onStartJob()");

        BatchBuilder builder = new BatchBuilder();
        MyPlacesDb db = new MyPlacesDb(this);
        PlaceParameter place = db.getMyPlaceParameter();
        db.close();
        if (place == null) {
            place = new PlaceParameter(this);
        }
        builder.addGlobalParameter(place);
        Logger.d("NotificationService", "builder add place");

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
        Logger.d("NotificationService", "execute request");

        if (retval.responses != null && retval.responses.size() == 2) {
            ObservationResponse obResponse = new ObservationResponse(
                    retval.responses.get(0).getFirstResponse());
            ForecastsResponse fResponse = new ForecastsResponse(
                    retval.responses.get(1).getFirstResponse());
            AerisNotification
                    .setCustomNotification(this, obResponse, fResponse);
            PrefManager.setLongPreference(this, PrefManager.NTF_TIMESTAMP_KEY,
                    SystemClock.elapsedRealtime());
            Logger.d("NotificationService", "response success");

        }
        else {
            Logger.d("NotificationService", "response failed");

        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
