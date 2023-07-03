package com.xweather.sdkdemo.java.listview;

import android.app.Activity;

import com.aerisweather.aeris.response.AirQualityResponse;

import java.util.List;

public class AirQualityAdapter extends ListAdapter<AirQualityResponse>
{
	Activity m_activity;

	public AirQualityAdapter(List<AirQualityResponse> items, Activity activity)
	{
		super(items, activity, 0);
		m_activity = activity;
	}

	@Override
	public AdapterHolder<AirQualityResponse> getHolder() {
		return new AirQualityItemHolder(m_activity);
	}

}
