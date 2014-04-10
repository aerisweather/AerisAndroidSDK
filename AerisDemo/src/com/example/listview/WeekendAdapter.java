package com.example.listview;

import java.util.List;

import android.app.Activity;

public class WeekendAdapter extends ListAdapter<DayNightPeriod> {

	public WeekendAdapter(List<DayNightPeriod> items, Activity activity) {
		super(items, activity, 0);
	}

	@Override
	public AdapterHolder<DayNightPeriod> getHolder() {
		return new WeekendItemHolder();
	}

}
