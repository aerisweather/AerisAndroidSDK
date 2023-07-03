package com.xweather.sdkdemo.java.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.xweather.sdkdemo.java.listview.ObservationAdapter;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisDataJSON;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.response.ObservationResponse;

public class NearbyObsFragment extends AerisFragment {

	ListView listView;
	ObservationAdapter adapter;
	private List<ObservationResponse> periods;

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
		if (endpoint == EndpointType.OBSERVATIONS) {
			if (response.isSuccessfulWithResponses()) {

				periods = new ArrayList<ObservationResponse>();
				for (AerisDataJSON data : response.getListOfResponse()) {
					periods.add(new ObservationResponse(data));
				}
				if (adapter == null) {
					adapter = new ObservationAdapter(periods, getActivity());
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
		headlessFragment.performNearbyObs(this);
	}

	@Override
	EndpointType getEndpointType() {
		return EndpointType.OBSERVATIONS;
	}

	@Override
	String getKey() {
		return HeadlessFragment.NEARBY_OBS;
	}
}
