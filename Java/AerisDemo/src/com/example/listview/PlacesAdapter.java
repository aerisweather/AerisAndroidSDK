package com.example.listview;

import java.util.List;

import android.app.Activity;

import com.example.demoaerisproject.R;
import com.aerisweather.aeris.response.PlacesResponse;

public class PlacesAdapter extends ListAdapter<PlacesResponse> {

	public PlacesAdapter(List<PlacesResponse> items, Activity activity) {
		super(items, activity, R.color.light_blue);

	}

	@Override
	public AdapterHolder<PlacesResponse> getHolder() {
		return new PlacesItemHolder();
	}

}
