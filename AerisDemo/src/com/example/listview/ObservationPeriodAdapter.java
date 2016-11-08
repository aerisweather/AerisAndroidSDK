package com.example.listview;

import java.util.List;

import android.app.Activity;

import com.aerisweather.aeris.model.ObservationPeriod;

public class ObservationPeriodAdapter extends ListAdapter<ObservationPeriod> {

	public ObservationPeriodAdapter(List<ObservationPeriod> items,
			Activity activity) {
		super(items, activity, 0);
	}

	@Override
	public AdapterHolder<ObservationPeriod> getHolder() {
		return new RecentObsHolder();
	}

}
