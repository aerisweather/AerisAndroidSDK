package com.example.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aerisweather.aeris.response.ConvectiveOutlookResponse;
import com.example.demoaerisproject.R;
import com.example.listview.ObservationPeriodAdapter;
import com.aerisweather.aeris.communication.AerisCallback;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.model.ObservationPeriod;
import com.aerisweather.aeris.response.ObRecentResponse;

public class RecentObsFragment extends AerisFragment implements AerisCallback {
	ListView listView;
	ObservationPeriodAdapter adapter;
	private List<ObservationPeriod> periods;

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
		if (endpoint == EndpointType.OBSERVATIONS_RECENT) {
			if (response.isSuccessfulWithResponses()) {
				ObRecentResponse fResponse = new ObRecentResponse(
						response.getFirstResponse());
				periods = fResponse.getPeriods();
				if (adapter == null) {
					adapter = new ObservationPeriodAdapter(periods,
							getActivity());
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
		headlessFragment.performRecentsObs(this);
	}

	@Override
	EndpointType getEndpointType() {
		return EndpointType.OBSERVATIONS_RECENT;
	}

	@Override
	String getKey() {
		return HeadlessFragment.RECENT_OBS;
	}
}
