package com.example.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoaerisproject.R;
import com.example.view.TemperatureWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hamweather.aeris.communication.Action;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisCommunicationTask;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.communication.Endpoint;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.fields.Fields;
import com.hamweather.aeris.communication.fields.ObservationFields;
import com.hamweather.aeris.communication.parameter.FieldsParameter;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.location.LocationHelper;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.AerisMapView.OnAerisMapLongClickListener;
import com.hamweather.aeris.maps.MapOptionsActivity;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.model.Observation;
import com.hamweather.aeris.model.RelativeTo;
import com.hamweather.aeris.response.ObservationResponse;

public class MapFragment extends MapViewFragment implements
		OnAerisMapLongClickListener, AerisCallback {
	public static final int OPTIONS_ACTIVITY = 1025;
	private LocationHelper locHelper;
	private Marker marker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_interactive_maps,
				container, false);
		mapView = (AerisMapView) view.findViewById(R.id.aerisfragment_map);
		mapView.init(savedInstanceState, AerisMapType.GOOGLE);
		initMap();
		setHasOptionsMenu(true);
		return view;
	}

	/**
	 * Inits the map with specific setting
	 */
	private void initMap() {
		locHelper = new LocationHelper(getActivity());
		Location myLocation = locHelper.getCurrentLocation();
		mapView.moveToLocation(myLocation, 7);
		mapView.setOnAerisMapLongClickListener(this);
		mapView.getMap().setInfoWindowAdapter(
				new TemperatureWindowAdapter(getActivity()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_weather_layers) {
			this.startActivityForResult(new Intent(getActivity(),
					MapOptionsActivity.class), OPTIONS_ACTIVITY);
			return false;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_maps_fragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hamweather.aeris.maps.AerisMapView.OnAerisMapLongClickListener#
	 * onMapLongClick(double, double)
	 */
	@Override
	public void onMapLongClick(double lat, double longitude) {
		AerisRequest request = new AerisRequest(new Endpoint(
				EndpointType.OBSERVATIONS), Action.CLOSEST, new PlaceParameter(
				lat, longitude), FieldsParameter.initWith(
				ObservationFields.ICON, ObservationFields.TEMP_C,
				ObservationFields.TEMP_F, Fields.RELATIVE_TO));
		AerisCommunicationTask task = new AerisCommunicationTask(getActivity(),
				this, request);

		task.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hamweather.aeris.communication.AerisCallback#onResult(com.hamweather
	 * .aeris.communication.EndpointType,
	 * com.hamweather.aeris.model.AerisResponse)
	 */
	@Override
	public void onResult(EndpointType type, AerisResponse response) {
		if (type == EndpointType.OBSERVATIONS) {
			if (response.isSuccessfulWithResponses()) {
				ObservationResponse obResponse = new ObservationResponse(
						response.getFirstResponse());
				Observation ob = obResponse.getObservation();
				RelativeTo relativeTo = obResponse.getRelativeTo();
				MarkerOptions options = new MarkerOptions()
						.position(new LatLng(relativeTo.lat, relativeTo.lon))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.map_indicator_blank))
						.title(ob.icon).snippet(String.valueOf(ob.tempF))
						.anchor(.5f, .5f);
				if (marker != null) {
					marker.remove();
				}
				marker = mapView.getMap().addMarker(options);
				marker.showInfoWindow();
			}
		}
	}

}
