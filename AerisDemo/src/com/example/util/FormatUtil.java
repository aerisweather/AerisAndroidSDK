package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
	public static String getTimehhmmFromISO(String iso) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss",
					Locale.ENGLISH);
			Date dateObj = formatter.parse(iso.split("T")[1]);

			SimpleDateFormat dayFormat = new SimpleDateFormat("hh:mm",
					Locale.ENGLISH);
			return dayFormat.format(dateObj);
		} catch (ParseException e) {
			return "";
		}
	}

	public static String getDayFromISO(String iso, boolean partial) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			Date dateObj = formatter.parse(iso.split("T")[0]);

			SimpleDateFormat dayFormat;
			if (partial) {
				dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
			} else {
				dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
			}
			return dayFormat.format(dateObj);
		} catch (ParseException e) {
			return "";
		}
	}

	public static String getMonthDayFromISO(String iso) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			Date dateObj = formatter.parse(iso.split("T")[0]);

			SimpleDateFormat dayFormat = new SimpleDateFormat("MMM dd",
					Locale.ENGLISH);
			return dayFormat.format(dateObj);
		} catch (ParseException e) {
			return "";
		}
	}
}
