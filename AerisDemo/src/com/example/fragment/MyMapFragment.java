package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aerisweather.aeris.maps.MapOptionsActivity;
import com.aerisweather.aeris.maps.MapOptionsActivityBuilder;
import com.aerisweather.aeris.tiles.AerisPointData;
import com.aerisweather.aeris.tiles.AerisPolygonData;
import com.example.db.MyPlace;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.DrawerActivity;
import com.example.demoaerisproject.MapOptionsLocalActivity;
import com.example.demoaerisproject.R;
import com.example.view.TemperatureInfoData;
import com.example.view.TemperatureWindowAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.aerisweather.aeris.communication.AerisCallback;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.communication.fields.Fields;
import com.aerisweather.aeris.communication.fields.ObservationFields;
import com.aerisweather.aeris.communication.loaders.ObservationsTask;
import com.aerisweather.aeris.communication.loaders.ObservationsTaskCallback;
import com.aerisweather.aeris.communication.parameter.ParameterBuilder;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;
import com.aerisweather.aeris.location.LocationHelper;
import com.aerisweather.aeris.maps.AerisMapOptions;
import com.aerisweather.aeris.maps.AerisMapView;
import com.aerisweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.aerisweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener;
import com.aerisweather.aeris.maps.markers.AerisMarker;
import com.aerisweather.aeris.model.AerisError;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.model.Observation;
import com.aerisweather.aeris.model.RelativeTo;
import com.aerisweather.aeris.response.EarthquakesResponse;
import com.aerisweather.aeris.response.FiresResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.aerisweather.aeris.response.StormCellResponse;
import com.aerisweather.aeris.response.StormReportsResponse;
import com.aerisweather.aeris.response.RecordsResponse;
import com.aerisweather.aeris.tiles.AerisTile;

public class MyMapFragment extends Fragment implements
		OnAerisMapLongClickListener, AerisCallback, ObservationsTaskCallback,
		OnAerisMarkerInfoWindowClickListener, RefreshInterface, OnMapReadyCallback
{
    private OnFragmentInteractionListener mListener;
	private LocationHelper m_locHelper;
	private Marker m_marker;
	private TemperatureWindowAdapter m_infoAdapter;
    private static final int REQUEST_PERMISSIONS = 0;
    LayoutInflater m_inflater;
    ViewGroup m_container;
    Bundle m_savedInstanceState;
	GoogleMap m_googleMap;
	protected AerisMapView m_mapView;
    private AerisMapOptions m_mapOptions = null;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_inflater = inflater;
        m_container = container;
        m_savedInstanceState = savedInstanceState;

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
		m_mapView = (AerisMapView) view.findViewById(R.id.mapView);
		m_mapView.onCreate(savedInstanceState);
		m_mapView.getMapAsync(this);

		return view;
	}

	@Override
	public void onMapReady(GoogleMap googleMap)
	{
        m_googleMap = googleMap;
        m_mapView.init(googleMap);
        initMap();
	}

    private void requestMultiplePermissions(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        String readExternalPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasLocPermission = ContextCompat.checkSelfPermission(getActivity(), locationPermission);
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), readExternalPermission);
        List<String> permissions = new ArrayList<String>();

        if (hasLocPermission != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(locationPermission);
        }

        if (hasReadPermission != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(readExternalPermission);
        }

        if (!permissions.isEmpty())
        {
            String[] params = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), params, REQUEST_PERMISSIONS);
        }
        else
        {
            // We already have permission, so handle as normal
            //initMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case REQUEST_PERMISSIONS:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //initMap();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.permissions_verbiage, Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /**
	 * Inits the map with specific setting
	 */
	private void initMap()
    {
        setHasOptionsMenu(true);

        //AerisMapOptions mapOptions = AerisMapOptions.getPreference(this.m_mapView.getContext());
        //AerisMapOptions mapOptions = ((DrawerActivity) getActivity()).getMapOptions();
        //get the map options from local store (if there is one)
        m_mapOptions = AerisMapOptions.getPreference(getActivity());

		if (m_mapOptions == null)
		{
            m_mapOptions = AerisMapOptions.getPreference(getActivity(), true); //gets options with defaults
            m_mapOptions.withPointData(AerisPointData.NONE);
            m_mapOptions.withPolygon(AerisPolygonData.NONE);
            m_mapOptions.withTile(AerisTile.RADAR);

            m_mapOptions.setPreference(getActivity());
		}

        m_mapView.displayMapWithOptions(m_mapOptions);

		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();

        MarkerOptions markerOptions = new MarkerOptions();

		if (place == null)
        {
            m_locHelper = new LocationHelper(getActivity());
			Location myLocation = m_locHelper.getCurrentLocation();
            m_mapView.moveToLocation(myLocation, 9);

			if (markerOptions != null)
				markerOptions.position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
		}
        else
        {
            m_mapView.moveToLocation(new LatLng(place.latitude, place.longitude),	9);

			if (markerOptions != null)
				markerOptions.position(new LatLng(place.latitude, place.longitude));
		}

		if ((m_googleMap != null) && (markerOptions != null))
			m_googleMap.addMarker(markerOptions);

        m_mapView.setOnAerisMapLongClickListener(this);

		// setup the custom info window adapter to use
        m_infoAdapter = new TemperatureWindowAdapter(getActivity());
        m_mapView.addWindowInfoAdapter(m_infoAdapter);

		// setup doing something when a user presses an info window
		// from the Aeris Point Data.
        m_mapView.setOnAerisWindowClickListener(this);

		db.close();
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
			// call to launch using permissions obtained through API
			// these permissions are obtained at start of AerisEngine
            //m_mapView.startAerisMapOptionsActivity(getActivity());

			/*
			 * Alternatively you could only show the map points options like
			 * this using the MapOptionsActivityBuilder(). See the classes
			 * javadoc for more detail and examples of it.
			 */
			//MapOptionsActivityBuilder builder = new
			//MapOptionsActivityBuilder();
			//builder.withAllPoints();
			//m_mapView.startAerisMapOptionsActivity(getActivity(), builder);

            getActivity().startActivity(new Intent(getActivity(), MapOptionsLocalActivity.class));

			return false;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

        if (m_mapView != null)
        {

            if (m_mapOptions != null)
            {
                m_mapOptions = AerisMapOptions.getPreference(getActivity());
                m_mapView.displayMapWithOptions(m_mapOptions);
            }

            m_mapView.onResume();
        }
	}

	@Override
	public void onPause() {
		super.onPause();
		m_mapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		m_mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		m_mapView.onLowMemory();
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
	 * @see com.aerisweather.aeris.maps.AerisMapView.OnAerisMapLongClickListener#
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
	 * com.aerisweather.aeris.communication.AerisCallback#onResult(com.aerisweather.aeris.communication.EndpointType,
	 * com.aerisweather.aeris.model.AerisResponse)
	 */
	@Override
	public void onResult(EndpointType type, AerisResponse response) {
		if (type == EndpointType.OBSERVATIONS) {
			if (response.isSuccessfulWithResponses()) {
				ObservationResponse obResponse = new ObservationResponse(
						response.getFirstResponse());
				Observation ob = obResponse.getObservation();
				RelativeTo relativeTo = obResponse.getRelativeTo();
				if (m_marker != null) {
                    m_marker.remove();
				}
				TemperatureInfoData data = new TemperatureInfoData(ob.icon,
						String.valueOf(ob.tempF));
                m_marker = m_infoAdapter.addGoogleMarker(m_mapView.getMap(),
						relativeTo.lat, relativeTo.lon, BitmapDescriptorFactory
								.fromResource(R.drawable.map_indicator_blank),
						data);
                m_marker.showInfoWindow();
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
    public void recordsWindowPressed(RecordsResponse response, AerisMarker marker) {
        // do something with the response data.
        Toast.makeText(getActivity(), "Daily Record pressed!", Toast.LENGTH_SHORT)
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
		if (m_marker != null) {
            m_marker.remove();
		}
		TemperatureInfoData data = new TemperatureInfoData(ob.icon,
				String.valueOf(ob.tempF));
        m_marker = m_infoAdapter.addGoogleMarker(m_mapView.getMap(), relativeTo.lat,
				relativeTo.lon, BitmapDescriptorFactory
						.fromResource(R.drawable.map_indicator_blank), data);
        m_marker.showInfoWindow();
	}

	@Override
	public void refreshPressed()
    {
		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();

        if (place != null)
        {
            m_mapView.moveToLocation(new LatLng(place.latitude, place.longitude),	9);
		}
        else
        {
            m_locHelper = new LocationHelper(getActivity());
			Location myLocation = m_locHelper.getCurrentLocation();
            m_mapView.moveToLocation(myLocation, 9);
		}

		db.close();
	}
}
