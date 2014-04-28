package com.example.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.hamweather.aeris.util.FileUtil;
import com.hamweather.aeris.util.WeatherUtil;

/**
 * Adapter that applies a new layer of tiles over the map.
 * 
 * @author Ben Collins
 * 
 */
public class TemperatureWindowAdapter implements InfoWindowAdapter {
	/**
	 * Used to inflate the view.
	 */
	private LayoutInflater inflater;
	/**
	 * Context necessary for inflating and finding resources.
	 */
	private Context context;

	/**
	 * Adapter for grabbing the necessary tiles for up to date casts on the Map.
	 * 
	 * @param context
	 *            Context for inflating and finding resources.
	 */
	public TemperatureWindowAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.maps.GoogleMap.InfoWindowAdapter#getInfoContents
	 * (com.google.android.gms.maps.model.Marker)
	 */
	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.maps.GoogleMap.InfoWindowAdapter#getInfoWindow
	 * (com.google.android.gms.maps.model.Marker)
	 */
	@Override
	public View getInfoWindow(Marker arg0) {
		TextView view = (TextView) inflater.inflate(
				R.layout.dialog_aeris_windowadapter, null);
		view.setText(WeatherUtil.appendDegree(arg0.getSnippet()));
		int drawable = FileUtil.getDrawableByName(arg0.getTitle(), context);
		view.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
		return view;
	}

}