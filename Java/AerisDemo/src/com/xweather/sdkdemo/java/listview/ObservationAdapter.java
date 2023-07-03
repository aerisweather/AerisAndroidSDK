package com.xweather.sdkdemo.java.listview;

import java.util.List;

import android.app.Activity;

import com.aerisweather.aeris.response.ObservationResponse;

public class ObservationAdapter extends ListAdapter<ObservationResponse> {

	public ObservationAdapter(List<ObservationResponse> items, Activity activity) {
		super(items, activity, 0);
	}

	@Override
	public AdapterHolder<ObservationResponse> getHolder() {
		return new ObservationItemHolder();
	}

}
