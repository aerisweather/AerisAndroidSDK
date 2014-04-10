package com.example.listview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.hamweather.aeris.model.ForecastPeriod;
import com.hamweather.aeris.util.FileUtil;

public class ForecastItemHolder implements AdapterHolder<ForecastPeriod> {

	ImageView weatherIcon;
	TextView day;
	TextView date;
	TextView weatherDesc;
	TextView tempHigh;
	TextView tempLow;

	@Override
	public View inflateview(LayoutInflater mInflater) {
		View v = mInflater.inflate(R.layout.listview_extended_forecast, null,
				false);
		weatherIcon = (ImageView) v.findViewById(R.id.ivListIcon);
		weatherDesc = (TextView) v.findViewById(R.id.tvListDesc);
		tempHigh = (TextView) v.findViewById(R.id.tvListHigh);
		tempLow = (TextView) v.findViewById(R.id.tvListLow);
		date = (TextView) v.findViewById(R.id.tvListDate);
		day = (TextView) v.findViewById(R.id.tvListDay);
		return v;
	}

	@Override
	public void populateView(ForecastPeriod t, int position) {
		weatherIcon.setImageResource(FileUtil.getDrawableByName(t.icon,
				day.getContext()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		try {
			String iso = t.dateTimeISO;
			Date dateObj = formatter.parse(iso.split("T")[0]);
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEE",
					Locale.ENGLISH);
			day.setText(dayFormat.format(dateObj));
			SimpleDateFormat monthFormat = new SimpleDateFormat("MMM dd",
					Locale.ENGLISH);
			date.setText(monthFormat.format(dateObj));
		} catch (ParseException e) {

		}

		weatherDesc.setText(t.weather);
		tempHigh.setText(t.maxTempF.toString());
		tempLow.setText(t.minTempF.toString());

	}

}
