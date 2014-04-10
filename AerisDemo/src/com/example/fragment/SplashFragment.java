package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoaerisproject.DrawerActivity;
import com.example.demoaerisproject.R;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.logging.Logger;

public class SplashFragment extends AerisFragment {

	// how long until we go to the next activity
	protected int _splashTime = 7500;

	private Thread splashThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// thread for displaying the SplashScreen
		splashThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						// wait 5 sec
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
				} finally {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((DrawerActivity) getActivity()).displayView(1);
							getActivity().getActionBar().show();
						}
					});

				}
			}
		};
		getActivity().getActionBar().hide();
		headlessFragment.performDetailedObservation(null);
		splashThread.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.splash, container, false);
		return rootView;
	}

	@Override
	public void notifyDataChanged() {
		if (headlessFragment.getResponse(HeadlessFragment.DETAILED_OBSERVATION) != null) {
			Logger.i("TEST", "Got Detailed Observation");
			splashThread.interrupt();
		}
	}

	@Override
	void performRequest() {

	}

	@Override
	EndpointType getEndpointType() {
		return null;
	}

	@Override
	String getKey() {
		return null;
	}

}
