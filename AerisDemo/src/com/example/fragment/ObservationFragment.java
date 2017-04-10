package com.example.fragment;

import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerisweather.aeris.response.ConvectiveOutlookResponse;
import com.example.demoaerisproject.R;
import com.example.view.DayNightView;
import com.example.view.SmallForecastView;
import com.example.view.TwoPartView;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.model.Observation;
import com.aerisweather.aeris.model.Place;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.aerisweather.aeris.response.PlacesResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;

public class ObservationFragment extends AerisFragment {

	private static final String N_A = "N/A";
	private TextView placeTextView;
	private TextView tempTextView;
	private ImageView iconImageView;
	private TextView weatherShortTextView;
	private TextView feelslikeTextView;

	private TwoPartView humidityView;
	private TwoPartView windsView;
	private TwoPartView dewPointView;
	private TwoPartView pressureView;

	private DayNightView todayView;
	private DayNightView nightView;

	private LinearLayout forecastsLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_observation,
				container, false);

		placeTextView = (TextView) rootView.findViewById(R.id.tvPlace);
		tempTextView = (TextView) rootView.findViewById(R.id.tvTemperature);
		iconImageView = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);
		weatherShortTextView = (TextView) rootView
				.findViewById(R.id.tvWeatherShort);
		feelslikeTextView = (TextView) rootView.findViewById(R.id.tvFeelsLike);
		humidityView = (TwoPartView) rootView.findViewById(R.id.viewHumidity);
		windsView = (TwoPartView) rootView.findViewById(R.id.viewWinds);
		dewPointView = (TwoPartView) rootView.findViewById(R.id.viewDewPoint);
		pressureView = (TwoPartView) rootView.findViewById(R.id.viewPressure);
		todayView = (DayNightView) rootView.findViewById(R.id.viewToday);
		nightView = (DayNightView) rootView.findViewById(R.id.viewTonight);
		forecastsLayout = (LinearLayout) rootView
				.findViewById(R.id.llObservationForecasts);
		return rootView;
	}

	@Override
	public void showProgress() {
		super.showProgress();

	}

	@Override
	public void hideProgress() {
		super.hideProgress();

	}

	private void setObservation(Observation ob) {
		tempTextView.setText(WeatherUtil.appendDegree(ob.tempF));
		weatherShortTextView.setText(ob.weatherShort);
		feelslikeTextView.setText("Feels like "
				+ WeatherUtil.appendDegree(ob.feelslikeF));
		iconImageView.setImageResource(FileUtil.getDrawableByName(ob.icon,
				getActivity()));

		if (ob.humidity != null) {
			humidityView.setText("Humidity", ob.humidity.intValue() + "%");
		} else {
			humidityView.setText("Humidity", N_A);
		}

		dewPointView.setText("Dew Point",
				WeatherUtil.appendDegree(ob.dewpointF));

		if (ob.pressureIN != null) {
			pressureView.setText("Pressure", ob.pressureIN.doubleValue()
					+ " IN");
		} else {
			pressureView.setText("Pressure", N_A);
		}

		if (ob.windDir != null) {
			windsView.setText("Winds", String.format(Locale.ENGLISH,
					"%s %d mph", ob.windDir, ob.windSpeedMPH.intValue()));
		} else {
			windsView.setText("Winds", N_A);
		}

	}

	private void setPlace(Place place) {
		String temp = String.format("%s, %s, %s", place.name, place.state,
				place.country);
		placeTextView.setText(temp);

	}

	@Override
	public void onBatchResponse(AerisBatchResponse response) {
		if (todayView == null) {
			return;
		}
		if (response.isSuccessful() && response.getError() == null) {
			ObservationResponse obResponse = new ObservationResponse(
					response.responses.get(0).getFirstResponse());
			Observation ob = obResponse.getObservation();
			setObservation(ob);

			PlacesResponse pResponse = new PlacesResponse(response.responses
					.get(1).getFirstResponse());
			setPlace(pResponse.getPlace());

			ForecastsResponse fResponse = new ForecastsResponse(
					response.responses.get(2).getFirstResponse());

			String dayTitle1 = "Today";
			String dayTitle2 = "Tonight";
			if (fResponse.getPeriod(0).isDay == false) {
				dayTitle1 = "Tonight";
				dayTitle2 = "Tomorrow";
			}
			todayView.setPeriod(fResponse.getPeriod(0), dayTitle1);
			nightView.setPeriod(fResponse.getPeriod(1), dayTitle2);

			ForecastsResponse hoursResponse = new ForecastsResponse(
					response.responses.get(3).getFirstResponse());

			forecastsLayout.removeAllViews();
			for (ForecastPeriod p : hoursResponse.getPeriods()) {
				SmallForecastView view = new SmallForecastView(getActivity());
				view.setForecast(p);
				forecastsLayout.addView(view);
			}

			HeadlessFragment headless = HeadlessFragment
					.getFragment(getActivity());
			headless.storeResponse(HeadlessFragment.DETAILED_OBSERVATION,
					response);
		} else {
			handleError(response.getError());
		}
	}

	@Override
	void performRequest() {
		headlessFragment.performDetailedObservation(this);

	}

	@Override
	EndpointType getEndpointType() {
		return null;
	}

	@Override
	String getKey() {
		return HeadlessFragment.DETAILED_OBSERVATION;
	}
}
