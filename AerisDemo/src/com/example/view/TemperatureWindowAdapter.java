package com.example.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.google.android.gms.maps.model.Marker;
import com.hamweather.aeris.maps.AerisMarkerWindow;
import com.hamweather.aeris.util.FileUtil;
import com.hamweather.aeris.util.WeatherUtil;

/**
 * Adapter that applies a new layer of tiles over the map.
 * 
 * @author Ben Collins
 * 
 */
public class TemperatureWindowAdapter extends AerisMarkerWindow {
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

	@Override
	public View getView() {
		TextView view = (TextView) inflater.inflate(
				R.layout.dialog_aeris_windowadapter, null);
		return view;
	}

	@Override
	public void fillView(View view, Marker marker) {
		TextView textView = (TextView) view;
		TemperatureInfoData data = (TemperatureInfoData) getData(marker);
		textView.setText(WeatherUtil.appendDegree(data.temperature));
		textView.setCompoundDrawablesWithIntrinsicBounds(
				FileUtil.getDrawableByName(data.icon, context), 0, 0, 0);

	}

	@Override
	public void onInfoWindowPressed(Marker marker) {

	}

}