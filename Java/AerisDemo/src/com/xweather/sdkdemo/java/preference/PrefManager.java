package com.xweather.sdkdemo.java.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xweather.sdkdemo.java.demoaerisproject.R;

public class PrefManager {

	public static final String NTF_TIMESTAMP_KEY = "ntf_timestamp_key";
	private static final String PREFERENCES = "aeris_preferences";
	private static final String TIMESTAMP = "_timestamp";
	private static final long TEN_MINUTES = 1000 * 60 * 10;

	public static boolean setPreference(Context context, String key,
			String value) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.putLong(key + TIMESTAMP, System.currentTimeMillis());
		return editor.commit();
	}

	public static String getPreference(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		return prefs.getString(key, null);

	}

	public static boolean isPreferenceFresh(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		long delta = System.currentTimeMillis()
				- prefs.getLong(key + TIMESTAMP, 0);
		return delta < TEN_MINUTES;

	}

	public static long getPreferenceTimestamp(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		return prefs.getLong(key + TIMESTAMP, 0);

	}

	public static long getLongPreference(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		return prefs.getLong(key, 0);
	}

	public static void setLongPreference(Context context, String key, long value) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static boolean setPreference(Context context, String key,
			boolean value) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static boolean getBoolPreference(Context context, String key) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES,
				Context.MODE_PRIVATE);
		// default the ntf pref to true
		if (context.getString(R.string.pref_ntf_enabled).equals(key)) {
			return prefs.getBoolean(key, true);
		} else {
			return prefs.getBoolean(key, false);
		}

	}

}
