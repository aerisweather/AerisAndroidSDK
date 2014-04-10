package com.example.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.demoaerisproject.R;
import com.example.listview.ForecastAdapter;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.model.ForecastPeriod;
import com.hamweather.aeris.response.ForecastsResponse;

public class ExtForecastFragment extends AerisFragment{

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
			if (response.success && response.getError() == null) {
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
