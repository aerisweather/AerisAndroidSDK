package com.example.db;

import org.apache.commons.lang3.text.WordUtils;

public class MyPlace {

	public String name;
	public String country;
	public String state;
	public boolean myLoc = false;

	public String getTextDisplay(String defaultText) {
		String text = defaultText;
		if (state != null && state.length() > 0) {
			text = String.format("%s, %s, %s", WordUtils.capitalize(name, ' '),
					state.toUpperCase(), country.toUpperCase());
		} else {
			text = String
					.format("%s, %s", WordUtils.capitalize(name, ' ', '-'),
							country.toUpperCase());
		}
		return text;
	}

}
