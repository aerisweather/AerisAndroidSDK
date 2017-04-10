package com.example.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aerisweather.aeris.response.ConvectiveOutlookResponse;
import com.example.demoaerisproject.R;
import com.example.listview.ForecastAdapter;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.response.ForecastsResponse;

public class ExtForecastFragment extends AerisFragment {

	ListView listView;
	ForecastAdapter adapter;
	private List<ForecastPeriod> periods;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_extforecast,
				container, false);
		listView = (ListView) rootView;
		return rootView;
	}

	@Override
	public void onResult(EndpointType endpoint, AerisResponse response) {
		if (listView == null) {
			return;
		}
		if (endpoint == EndpointType.FORECASTS) {
			if (response.isSuccessfulWithResponses()) {
				ForecastsResponse fResponse = new ForecastsResponse(
						response.getFirstResponse());
				periods = fResponse.getPeriods();
				if (adapter == null) {
					adapter = new ForecastAdapter(periods, getActivity());
					listView.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}
			} else {
				this.handleError(response.getError());
			}
		}
	}

	@Override
	void performRequest() {
		headlessFragment.performExtForecast(this);
	}

	@Override
	EndpointType getEndpointType() {
		return EndpointType.FORECASTS;
	}

	@Override
	String getKey() {
		return HeadlessFragment.EXT_FORECAST;
	}
}
