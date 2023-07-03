package com.xweather.sdkdemo.java.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.aerisweather.aeris.model.Place;
import com.aerisweather.aeris.response.PlacesResponse;
import com.aerisweather.aeris.util.WeatherUtil;

public class PlacesItemHolder implements AdapterHolder<PlacesResponse> {

	private TextView placeTextView;
	private ImageView icon;
	private TextView countTextView;

	@Override
	public View inflateview(LayoutInflater mInflater) {
		View view = mInflater.inflate(R.layout.drawer_list_item, null, false);
		placeTextView = (TextView) view.findViewById(R.id.title);
		icon = (ImageView) view.findViewById(R.id.icon);
		countTextView = (TextView) view.findViewById(R.id.counter);
		return view;
	}

	@Override
	public void populateView(PlacesResponse t, int position) {
		Place place = t.getPlace();
		String text = "";
		if (place.state != null && place.state.length() > 0) {
			text = String.format("%s, %s, %s",
					WeatherUtil.capitalize(place.name),
					place.state.toUpperCase(), place.country.toUpperCase());
		} else {
			text = String.format("%s, %s", WeatherUtil.capitalize(place.name),
					place.country.toUpperCase());
		}
		placeTextView.setText(text);
		icon.setVisibility(View.GONE);
		countTextView.setVisibility(View.GONE);

	}

}
