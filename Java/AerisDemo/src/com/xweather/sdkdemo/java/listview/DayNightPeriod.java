package com.xweather.sdkdemo.java.listview;

import com.aerisweather.aeris.model.ForecastPeriod;

public class DayNightPeriod {
	public ForecastPeriod day;
	public ForecastPeriod night;

	public DayNightPeriod(ForecastPeriod day, ForecastPeriod night) {
		this.day = day;
		this.night = night;
	}
}
