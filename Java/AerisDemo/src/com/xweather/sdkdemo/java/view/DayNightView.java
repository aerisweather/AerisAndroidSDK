package com.xweather.sdkdemo.java.view;

import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.util.FileUtil;

public class DayNightView extends RelativeLayout {

	private TextView intervalTextView;
	private TextView tempTextView;
	private TextView precipLabel;
	private TextView precipTextView;
	private ImageView iconImageView;
	private TextView windsTextView;
	private TextView weatherShortTextView;

	public DayNightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_forecast_today_tnt, this, true);
		intervalTextView = (TextView) this.findViewById(R.id.tvForeInterval);
		tempTextView = (TextView) this.findViewById(R.id.tvForeTemperature);
		precipLabel = (TextView) this.findViewById(R.id.tvForePrecip);
		precipTextView = (TextView) this.findViewById(R.id.tvForePrecipVal);
		iconImageView = (ImageView) this.findViewById(R.id.ivForeWeatherIcon);
		windsTextView = (TextView) this.findViewById(R.id.tvForeWindsVal);
		weatherShortTextView = (TextView) this
				.findViewById(R.id.tvForeWeatherShort);
	}

	public void setPeriod(ForecastPeriod period, String intervalName) {
		intervalTextView.setText(intervalName);
		
		int temp;
		if (period.isDay == true) {
			temp = period.maxTempF.intValue();
		}
		else {
			temp = period.minTempF.intValue();
		}
		
		tempTextView.setText(String.valueOf(temp));
		iconImageView.setImageResource(FileUtil.getDrawableByName(period.icon,
				getContext()));
		if (period.snowIN == null || period.snowIN.doubleValue() == 0) {
			precipLabel.setText("Precip");
			if (period.precipIN != null) {
				precipTextView.setText(period.precipIN + " IN");
			} else {
				precipTextView.setText("0.00 IN");
			}
		} else {
			precipLabel.setText("Snow");
			precipTextView.setText(period.snowIN + " IN");
		}

		if (period.windDir != null) {
			String tWind = "";
			if (period.windSpeedMaxMPH.intValue() < 5) {
				tWind = "Calm";
			}
			if (period.windSpeedMinMPH.intValue() == period.windSpeedMaxMPH
					.intValue()) {
				tWind = String.format(Locale.ENGLISH, "%s %d mph",
						period.windDir, period.windSpeedMaxMPH.intValue());
			} else {
				tWind = String.format(Locale.ENGLISH, "%s %d-%d mph",
						period.windDir, period.windSpeedMinMPH.intValue(),
						period.windSpeedMaxMPH.intValue());
			}

			windsTextView.setText(tWind);
		} else {
			windsTextView.setText("N/A");
		}

		weatherShortTextView.setText(period.weatherPrimary);

	}

}
