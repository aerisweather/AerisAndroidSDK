package com.xweather.sdkdemo.java.customendpoint;

import java.util.List;

import android.app.Activity;

import com.xweather.sdkdemo.java.listview.AdapterHolder;
import com.xweather.sdkdemo.java.listview.ListAdapter;

public class CustomEndpointAdapter extends ListAdapter<CustomSunmoonModel> {

	public CustomEndpointAdapter(List<CustomSunmoonModel> items,
			Activity activity) {
		super(items, activity, 0);

	}

	@Override
	public AdapterHolder<CustomSunmoonModel> getHolder() {
		return new CustomSunmoonHolder();
	}

}
