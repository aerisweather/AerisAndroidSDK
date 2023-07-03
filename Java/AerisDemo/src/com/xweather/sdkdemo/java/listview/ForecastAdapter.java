package com.xweather.sdkdemo.java.listview;

import java.util.List;

import android.app.Activity;

import com.aerisweather.aeris.model.ForecastPeriod;

public class ForecastAdapter extends ListAdapter<ForecastPeriod> {

	public ForecastAdapter(List<ForecastPeriod> items, Activity activity) {
		super(items, activity, 0);

	}

	@Override
	public AdapterHolder<ForecastPeriod> getHolder() {
		return new ForecastItemHolder();
	}

}
