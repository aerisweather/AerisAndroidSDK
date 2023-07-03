package com.xweather.sdkdemo.java.listview;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.xweather.sdkdemo.java.util.FormatUtil;
import com.xweather.sdkdemo.java.view.DayNightView;

public class WeekendItemHolder implements AdapterHolder<DayNightPeriod> {

	DayNightView day;
	DayNightView night;
	TextView weekday;
	TextView date;

	@Override
	public View inflateview(LayoutInflater mInflater) {
		View v = mInflater.inflate(R.layout.listview_weekend, null, false);
		day = (DayNightView) v.findViewById(R.id.viewToday);
		night = (DayNightView) v.findViewById(R.id.viewTonight);
		weekday = (TextView) v.findViewById(R.id.tvWeekendDay);
		date = (TextView) v.findViewById(R.id.tvWeekendDate);
		return v;
	}

	@Override
	public void populateView(DayNightPeriod t, int position) {
		Resources resources = day.getContext().getResources();
		date.setText(FormatUtil.getMonthDayFromISO(t.day.dateTimeISO));
		weekday.setText(FormatUtil.getDayFromISO(t.day.dateTimeISO, false));
		day.setPeriod(t.day, "");
		day.setBackgroundColor(resources.getColor(R.color.light_gray));
		night.setBackgroundColor(resources.getColor(R.color.dark_blue));
		night.setPeriod(t.night, "");

	}

}
