package com.example.listview;

import com.hamweather.aeris.model.ForecastPeriod;

public class DayNightPeriod {
	public ForecastPeriod day;
	public ForecastPeriod night;

	public DayNightPeriod(ForecastPeriod day, ForecastPeriod night) {
		this.day = day;
		this.night = night;
	}
}
