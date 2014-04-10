package com.example.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.example.demoaerisproject.BaseApplication;
import com.example.demoaerisproject.R;
import com.example.preference.PrefManager;

public class AerisPrefFragment extends PreferenceFragment implements
		OnPreferenceClickListener, OnPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.prefs);

//		this.findPreference(getString(R.string.pref_my_location))
//				.setOnPreferenceChangeListener(this);
		this.findPreference(getString(R.string.pref_ntf_enabled))
				.setOnPreferenceChangeListener(this);
//		this.findPreference(getString(R.string.pref_setlocation))
//				.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference.getKey().equals(getString(R.string.pref_my_location))) {
			PrefManager.setPreference(getActivity(), preference.getKey(),
					(Boolean) newValue);
			return true;
		} else if (preference.getKey().equals(
				getString(R.string.pref_ntf_enabled))) {
			boolean bool = (Boolean) newValue;
			PrefManager.setPreference(getActivity(), preference.getKey(), bool);
			BaseApplication.enableNotificationService(getActivity(), bool);
			return true;
		}
		return false;
	}
}
