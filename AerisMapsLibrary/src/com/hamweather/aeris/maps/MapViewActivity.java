package com.hamweather.aeris.maps;

import android.app.Activity;
import android.os.Bundle;

public class MapViewActivity extends Activity {
	protected AerisMapView mapView;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mapView != null)
			mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (mapView != null)
			mapView.onLowMemory();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mapView != null)
			mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mapView != null)
			mapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mapView != null)
			mapView.onSaveInstanceState(outState);
	}

}
