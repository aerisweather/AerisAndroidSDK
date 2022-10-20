package com.example.customendpoint;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.example.listview.AdapterHolder;
import com.aerisweather.aeris.util.WeatherUtil;

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
	public void populateView(CustomSunmoonModel model, int position)
	{
		if (model.sun.riseISO != null)
			sunriseTextView.setText(WeatherUtil.getFormatFromISO(model.sun.riseISO, "h:mm a"));
		else
			sunriseTextView.setText("N/A");

		if (model.sun.setISO != null)
			sunsetTextView.setText(WeatherUtil.getFormatFromISO(model.sun.setISO, "h:mm a"));
		else
			sunsetTextView.setText("N/A");

		if (position == 0)
		{
			titleView.setText("Today");
		}
		else if (position == 1)
		{
			titleView.setText("Tomorrow");
		}
		else
		{
			titleView.setText(WeatherUtil.getFormatFromISO(model.dateTimeISO, "EEE dd"));
		}

		if((model.sun.riseISO != null) && (model.sun.setISO != null))
		{
			long seconds = model.sun.set - model.sun.rise;
			long hours = seconds / 60 / 60;
			long minutes = (seconds / 60) - (hours * 60);

			totalDaylightTextView.setText(String.format("%d hours,%d minutes", hours, minutes));
			totalDaylightPBar.setProgress((int) seconds);
		}
		else
		{
			totalDaylightTextView.setText("N/A");
		}

		moonphaseTextView.setText(WeatherUtil.capitalize(model.moon.phase.name));
	}
}
