package com.example.demoaerisproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hamweather.aeris.communication.Action;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisCommunicationTask;
import com.hamweather.aeris.communication.AerisProgressListener;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.communication.BatchBuilder;
import com.hamweather.aeris.communication.BatchCallback;
import com.hamweather.aeris.communication.BatchCommunicationTask;
import com.hamweather.aeris.communication.Endpoint;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.communication.parameter.RadiusParameter;
import com.hamweather.aeris.communication.parameter.RadiusUnit;
import com.hamweather.aeris.model.AerisBatchResponse;
import com.hamweather.aeris.model.AerisError;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.response.ObservationResponse;

public class MainActivity extends Activity implements AerisProgressListener,
		AerisCallback, OnClickListener, BatchCallback {

	private TextView placeTextView;
	private TextView tempFTextView;
	private TextView weatherShortTextView;
	private TextView snowDepthInTextView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		placeTextView = (TextView) findViewById(R.id.tvPlace);
		tempFTextView = (TextView) findViewById(R.id.tvTempF);
		weatherShortTextView = (TextView) findViewById(R.id.tvWeatherShort);
		snowDepthInTextView = (TextView) findViewById(R.id.tvsnowDepthIN);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		this.findViewById(R.id.btnGo).setOnClickListener(this);
		this.findViewById(R.id.btnGoBatch).setOnClickListener(this);

	}

	private void setPlaceText(String place) {
		placeTextView.setText("Place:" + place);
	}

	private void setTempFText(String temp) {
		tempFTextView.setText("Temp: " + temp + " F");
	}

	private void setWeatherShort(String text) {
		weatherShortTextView.setText("Weather Short: " + text);
	}

	private void setSnowDepth(Number snow) {
		snowDepthInTextView.setText("Snow Depth: " + snow.toString() + " in");
	}

	@Override
	public void onResult(EndpointType endpoint, AerisResponse response) {
		if (endpoint == EndpointType.OBSERVATIONS) {
			if (response.isSuccessful() && response.getError() == null) {
				ObservationResponse obResponse = new ObservationResponse(
						response.getFirstResponse());
				setPlaceText(obResponse.getPlace().name);
				snowDepthInTextView.setVisibility(View.VISIBLE);
				if (obResponse.getObservation().snowDepthIN != null) {
					setSnowDepth(obResponse.getObservation().snowDepthIN);
				} else {
					setSnowDepth(0);
				}
				setWeatherShort(obResponse.getObservation().weatherShort);
				setTempFText(obResponse.getObservation().tempF.toString());
			} else {
				handleError(response.getError());
			}
		}
	}

	private void handleError(AerisError error) {
		if (error != null) {
			Toast.makeText(this, error.code + ": " + error.description,
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onBatchResponse(AerisBatchResponse response) {
		if (response.isSuccessful() && response.getError() == null) {
			ObservationResponse nyResponse = new ObservationResponse(
					response.responses.get(0).getFirstResponse());
			ObservationResponse mnResponse = new ObservationResponse(
					response.responses.get(1).getFirstResponse());
			ObservationResponse chicagoResponse = new ObservationResponse(
					response.responses.get(2).getFirstResponse());
			snowDepthInTextView.setVisibility(View.GONE);
			setPlaceText(nyResponse.getPlace().name + "/"
					+ mnResponse.getPlace().name + "/"
					+ chicagoResponse.getPlace().name);
			setWeatherShort(nyResponse.getObservation().weatherShort + "/"
					+ mnResponse.getObservation().weatherShort + "/"
					+ chicagoResponse.getObservation().weatherShort);
			setTempFText(nyResponse.getObservation().tempF + "/"
					+ mnResponse.getObservation().tempF + "/"
					+ chicagoResponse.getObservation().tempF);

		} else {
			handleError(response.getError());
		}
	}

	@Override
	public void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnGo) {
			AerisRequest request = new AerisRequest(new Endpoint(
					EndpointType.OBSERVATIONS), Action.CLOSEST,
					new PlaceParameter(this), new RadiusParameter(25,
							RadiusUnit.MILES));
			AerisCommunicationTask task = new AerisCommunicationTask(this,
					this, request);
			task.withProgress(this);
			task.execute();
		} else if (v.getId() == R.id.btnGoBatch) {
			BatchBuilder builder = new BatchBuilder();
			builder.addEndpoint(new Endpoint(EndpointType.OBSERVATIONS,
					"new york,new york"));
			builder.addEndpoint(new Endpoint(EndpointType.OBSERVATIONS,
					"minneapolis,mn"));
			builder.addEndpoint(new Endpoint(EndpointType.OBSERVATIONS,
					"chicago,il"));
			AerisRequest request = builder.build();
			BatchCommunicationTask task = new BatchCommunicationTask(this,
					this, request);
			task.withProgress(this);
			task.execute();
		}
	}

}
