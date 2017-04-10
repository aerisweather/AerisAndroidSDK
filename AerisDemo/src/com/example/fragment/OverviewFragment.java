package com.example.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aerisweather.aeris.response.ConvectiveOutlookResponse;
import com.example.demoaerisproject.R;
import com.example.listview.ForecastAdapter;
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

public class OverviewFragment extends AerisFragment {

	ListView listView;
	ForecastAdapter adapter;
	private List<ForecastPeriod> periods;
	private TextView placeTextView;
	private TextView tempTextView;
	private ImageView iconImageView;
	private TextView weatherShortTextView;
	private TextView feelslikeTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.lvOverview);
		placeTextView = (TextView) rootView.findViewById(R.id.tvPlace);
		tempTextView = (TextView) rootView.findViewById(R.id.tvTemperature);
		iconImageView = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);
		weatherShortTextView = (TextView) rootView
				.findViewById(R.id.tvWeatherShort);
		feelslikeTextView = (TextView) rootView.findViewById(R.id.tvFeelsLike);
		return rootView;
	}

	private void setObservation(Observation ob) {
		tempTextView.setText(WeatherUtil.appendDegree(ob.tempF));
		weatherShortTextView.setText(ob.weatherShort);
		feelslikeTextView.setText("Feels like "
				+ WeatherUtil.appendDegree(ob.feelslikeF));
		iconImageView.setImageResource(FileUtil.getDrawableByName(ob.icon,
				getActivity()));

	}

	private void setPlace(Place place) {
		String temp = String.format("%s, %s, %s", place.name, place.state,
				place.country);
		placeTextView.setText(temp);

	}

	@Override
	public void onBatchResponse(AerisBatchResponse response) {
		if (listView == null) {
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
			periods = fResponse.getPeriods();
			if (adapter == null) {
				adapter = new ForecastAdapter(periods, getActivity());
				listView.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}

		} else {
			handleError(response.getError());
		}
	}

	@Override
	void performRequest() {
		headlessFragment.performWeatherOverview(this);
	}

	@Override
	EndpointType getEndpointType() {
		return null;
	}

	@Override
	String getKey() {
		return HeadlessFragment.OVERVIEW;
	}

}
