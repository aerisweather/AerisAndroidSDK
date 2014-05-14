package com.example.customendpoint;

import java.util.List;

import android.app.Activity;

import com.example.listview.AdapterHolder;
import com.example.listview.ListAdapter;

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
