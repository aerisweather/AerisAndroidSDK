package com.hamweather.aeris.maps;

import android.app.Fragment;
import android.os.Bundle;

public class MapViewFragment extends Fragment{

	protected AerisMapView mapView; 
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy(); 
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		mapView.onLowMemory(); 
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause(); 
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
