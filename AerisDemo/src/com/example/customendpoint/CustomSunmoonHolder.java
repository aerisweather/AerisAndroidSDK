package com.example.customendpoint;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.example.listview.AdapterHolder;
import com.hamweather.aeris.util.WeatherUtil;

public class CustomSunmoonHolder implements AdapterHolder<CustomSunmoonModel> {
	private TextView titleView;
	private TextView sunriseTextView;
	private TextView sunsetTextView;
	private TextView totalDaylightTextView;
	private TextView moonphaseTextView;
	private ProgressBar totalDaylightPBar;

	@Override
	public View inflateview(LayoutInflater mInflater) {
		View v = mInflater.inflate(R.layout.view_sunmoon, null, false);
		sunriseTextView = (TextView) v.findViewById(R.id.tvSunmoonDayTime);
		sunsetTextView = (TextView) v.findViewById(R.id.tvSunmoonNightTime);
		totalDaylightTextView = (TextView) v
				.findViewById(R.id.tvSunmoonTotalDaylightVal);
		moonphaseTextView = (TextView) v.findViewById(R.id.tvSunmoonMoonphase);
		totalDaylightPBar = (ProgressBar) v
				.findViewById(R.id.pbSunmoonTotalDaylight);
		totalDaylightPBar.setMax(60 * 60 * 24);
		titleView = (TextView) v.findViewById(R.id.tvSunmoonTitle);
		return v;
	}

	@Override
	public void populateView(CustomSunmoonModel model, int position) {
		sunriseTextView.setText(WeatherUtil.getFormatFromISO(model.sun.riseISO,
				"h:mm a"));
		sunsetTextView.setText(WeatherUtil.getFormatFromISO(model.sun.setISO,
				"h:mm a"));
		if (position == 0) {
			titleView.setText("Today");
		} else if (position == 1) {
			titleView.setText("Tomorrow");
		} else {
			titleView.setText(WeatherUtil.getFormatFromISO(model.dateTimeISO,
					"EEE dd"));
		}

		long seconds = model.sun.set - model.sun.rise;
		long minutes = seconds % 60;
		long hours = seconds / 60 / 60;

		totalDaylightTextView.setText(String.format("%d hours,%d minutes",
				hours, minutes));
		totalDaylightPBar.setProgress((int) seconds);
		moonphaseTextView
				.setText(WeatherUtil.capitalize(model.moon.phase.name));
	}

}
