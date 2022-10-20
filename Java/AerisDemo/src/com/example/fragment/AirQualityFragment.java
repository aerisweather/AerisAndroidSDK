package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisDataJSON;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.response.AirQualityResponse;
import com.example.demoaerisproject.R;
import com.example.listview.AirQualityAdapter;

import java.util.ArrayList;
import java.util.List;


public class AirQualityFragment extends AerisFragment
{
	ListView listView;
	AirQualityAdapter adapter;
	private List<AirQualityResponse> aqResponses;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//use the existing generic listview fragment we use for the extended forecast and nearby obs
		View rootView = inflater.inflate(R.layout.fragment_extforecast,	container, false);
		listView = (ListView) rootView;
		return rootView;
	}

	@Override
	public void onResult(EndpointType endpoint, AerisResponse response)
	{
		if (listView == null)
		{
			return;
		}
		if (endpoint == EndpointType.AIR_QUALITY)
		{
			if (response.isSuccessfulWithResponses())
			{
				aqResponses = new ArrayList<AirQualityResponse>();

				for (AerisDataJSON data : response.getListOfResponse())
				{
					aqResponses.add(new AirQualityResponse(data));
				}

				if (adapter == null)
				{
					adapter = new AirQualityAdapter(aqResponses, getActivity());
					listView.setAdapter(adapter);
				}
				else
				{
					adapter.notifyDataSetChanged();
				}
			}
			else
			{
				this.handleError(response.getError());
			}
		}
	}

	@Override
	void performRequest() {
		headlessFragment.performAirQuality(this);
	}

	@Override
	EndpointType getEndpointType() {
		return EndpointType.AIR_QUALITY;
	}

	@Override
	String getKey() {
		return HeadlessFragment.AIR_QUALITY;
	}
}
