package com.xweather.sdkdemo.java.db;

import com.aerisweather.aeris.util.WeatherUtil;

public class MyPlace {

	public String name;
	public String country;
	public String state;
	public boolean myLoc = false;
	public double latitude;
	public double longitude;

	public String getTextDisplay(String defaultText) {
		String text = defaultText;
		if (state != null && state.length() > 0) {
			text = String.format("%s, %s, %s", WeatherUtil.capitalize(name),
					state.toUpperCase(), country.toUpperCase());
		} else {
			text = String.format("%s, %s", WeatherUtil.capitalize(name),
					country.toUpperCase());
		}
		return text;
	}

}
