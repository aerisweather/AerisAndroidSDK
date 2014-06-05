package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.hamweather.aeris.model.ForecastPeriod;
import com.hamweather.aeris.util.FileUtil;
import com.hamweather.aeris.util.WeatherUtil;

public class SmallForecastView extends LinearLayout {

	private TextView timeTextView;
	private TextView temperatureTextView;
	private ImageView iconImageView;

	public SmallForecastView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_small_forecast, this, true);
		timeTextView = (TextView) this.findViewById(R.id.tvSmForeTime);
		temperatureTextView = (TextView) this.findViewById(R.id.tvSmForeTemp);
		iconImageView = (ImageView) this.findViewById(R.id.ivSmForeIcon);
	}

	public SmallForecastView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_small_forecast, this, true);
		timeTextView = (TextView) this.findViewById(R.id.tvSmForeTime);
		temperatureTextView = (TextView) this.findViewById(R.id.tvSmForeTemp);
		iconImageView = (ImageView) this.findViewById(R.id.ivSmForeIcon);
	}

	public void setForecast(ForecastPeriod p) {
		iconImageView.setImageResource(FileUtil.getDrawableByName(p.icon,
				getContext()));
		if (p.tempF == null) {
			temperatureTextView.setText("--");
		} else {
			temperatureTextView.setText(p.tempF.toString());
		}

		timeTextView.setText(WeatherUtil.getTimeFromISO(p.dateTimeISO, false));
	}

}
