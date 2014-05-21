package com.example.fragment;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.db.MyPlace;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.R;
import com.example.view.TemperatureInfoData;
import com.example.view.TemperatureWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.fields.Fields;
import com.hamweather.aeris.communication.fields.ObservationFields;
import com.hamweather.aeris.communication.loaders.ObservationsTask;
import com.hamweather.aeris.communication.loaders.ObservationsTaskCallback;
import com.hamweather.aeris.communication.parameter.ParameterBuilder;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.location.LocationHelper;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.model.AerisError;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.model.Observation;
import com.hamweather.aeris.model.RelativeTo;
import com.hamweather.aeris.response.EarthquakesResponse;
import com.hamweather.aeris.response.FiresResponse;
import com.hamweather.aeris.response.ObservationResponse;
import com.hamweather.aeris.response.StormCellResponse;
import com.hamweather.aeris.response.StormReportsResponse;

public class MapFragment extends MapViewFragment implements
		OnAerisMapLongClickListener, AerisCallback, ObservationsTaskCallback,
		OnAerisMarkerInfoWindowClickListener, RefreshInterface {
	private LocationHelper locHelper;
	private Marker marker;
	private TemperatureWindowAdapter infoAdapter;

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
		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();
		if (place == null) {
			locHelper = new LocationHelper(getActivity());
			Location myLocation = locHelper.getCurrentLocation();
			mapView.moveToLocation(myLocation, 9);
		} else {
			mapView.moveToLocation(new LatLng(place.latitude, place.longitude),
					9);
		}

		mapView.setOnAerisMapLongClickListener(this);

		// setup the custom info window adapter to use
		infoAdapter = new TemperatureWindowAdapter(getActivity());
		mapView.addWindowInfoAdapter(infoAdapter);

		// setup doing something when a user presses an info window
		// from the Aeris Point Data.
		mapView.setOnAerisWindowClickListener(this);

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
			mapView.startAerisMapOptionsActivity(getActivity());
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
		// AerisRequest request = new AerisRequest(new Endpoint(
		// EndpointType.OBSERVATIONS), Action.CLOSEST, new PlaceParameter(
		// lat, longitude), FieldsParameter.initWith(
		// ObservationFields.ICON, ObservationFields.TEMP_C,
		// ObservationFields.TEMP_F, Fields.RELATIVE_TO));
		// AerisCommunicationTask task = new
		// AerisCommunicationTask(getActivity(),
		// this, request);
		//
		// task.execute();

		// the above using a specific object loader
		ParameterBuilder builder = new ParameterBuilder().withFields(
				ObservationFields.ICON, ObservationFields.TEMP_C,
				ObservationFields.TEMP_F, Fields.RELATIVE_TO);
		ObservationsTask task = new ObservationsTask(getActivity(), this);
		task.requestClosest(new PlaceParameter(lat, longitude), builder.build());
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
				if (marker != null) {
					marker.remove();
				}
				TemperatureInfoData data = new TemperatureInfoData(ob.icon,
						String.valueOf(ob.tempF));
				marker = infoAdapter.addGoogleMarker(mapView.getMap(),
						relativeTo.lat, relativeTo.lon, BitmapDescriptorFactory
								.fromResource(R.drawable.map_indicator_blank),
						data);
				marker.showInfoWindow();
			}
		}
	}

	@Override
	public void earthquakeWindowPressed(EarthquakesResponse response,
			AerisMarker marker) {
		// do something with the response data.
		Toast.makeText(getActivity(), "Earthquake pressed!", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void stormReportsWindowPressed(StormReportsResponse response,
			AerisMarker marker) {
		// do something with the response data.
		Toast.makeText(getActivity(), "Storm Report pressed!",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void stormCellsWindowPressed(StormCellResponse response,
			AerisMarker marker) {
		// do something with the response data.
		Toast.makeText(getActivity(), "Storm Cell pressed!", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void wildfireWindowPressed(FiresResponse response, AerisMarker marker) {
		// do something with the response data.
		Toast.makeText(getActivity(), "Wildfire pressed!", Toast.LENGTH_SHORT)
				.show();

	}

	@Override
	public void onObservationsFailed(AerisError arg0) {
		Toast.makeText(getActivity(),
				"Failed to load observation at that point", Toast.LENGTH_SHORT)
				.show();

	}

	@Override
	public void onObservationsLoaded(List<ObservationResponse> responses) {
		ObservationResponse obResponse = responses.get(0);
		Observation ob = obResponse.getObservation();
		RelativeTo relativeTo = obResponse.getRelativeTo();
		if (marker != null) {
			marker.remove();
		}
		TemperatureInfoData data = new TemperatureInfoData(ob.icon,
				String.valueOf(ob.tempF));
		marker = infoAdapter.addGoogleMarker(mapView.getMap(), relativeTo.lat,
				relativeTo.lon, BitmapDescriptorFactory
						.fromResource(R.drawable.map_indicator_blank), data);
		marker.showInfoWindow();
	}

	@Override
	public void refreshPressed() {
		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();
		if (place != null) {
			mapView.moveToLocation(new LatLng(place.latitude, place.longitude),
					9);
		} else {
			locHelper = new LocationHelper(getActivity());
			Location myLocation = locHelper.getCurrentLocation();
			mapView.moveToLocation(myLocation, 9);
		}
	}
}
