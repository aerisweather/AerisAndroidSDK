package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.demoaerisproject.R;
import com.example.listview.DayNightPeriod;
import com.example.listview.WeekendAdapter;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.response.ForecastsResponse;

public class WeekendFragment extends AerisFragment implements AerisCallback {
	ListView listView;
	WeekendAdapter adapter;
	private List<DayNightPeriod> periods;

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
				periods = new ArrayList<DayNightPeriod>();

				for (int i = 0; i < fResponse.getPeriodsSize(); i = i + 2) {
					periods.add(new DayNightPeriod(fResponse.getPeriod(i),
							fResponse.getPeriod(i + 1)));
				}
				if (adapter == null) {
					adapter = new WeekendAdapter(periods, getActivity());
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
		headlessFragment.performWeekendForecast(this);
	}

	@Override
	EndpointType getEndpointType() {
		return EndpointType.FORECASTS;
	}

	@Override
	String getKey() {
		return HeadlessFragment.WEEKEND;
	}

}
