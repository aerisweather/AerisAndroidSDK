package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aerisweather.aeris.maps.MapOptionsActivity;
import com.aerisweather.aeris.tiles.AerisAMP;
import com.aerisweather.aeris.tiles.AerisAmpLayer;
import com.example.db.MyPlace;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.MapOptionsLocalActivity;
import com.example.demoaerisproject.R;
import com.example.view.TemperatureInfoData;
import com.example.view.TemperatureWindowAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
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
import com.aerisweather.aeris.tiles.AerisPointData;
import com.aerisweather.aeris.tiles.AerisPolygonData;


public class MyMapFragment extends Fragment implements
		OnAerisMapLongClickListener, AerisCallback, ObservationsTaskCallback,
		OnAerisMarkerInfoWindowClickListener, RefreshInterface, OnMapReadyCallback
{
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_inflater = inflater;
        m_container = container;
        m_savedInstanceState = savedInstanceState;

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
		m_mapView = (AerisMapView) view.findViewById(R.id.mapView);
		m_mapView.onCreate(savedInstanceState);

		return view;
	}

	@Override
	public void onMapReady(GoogleMap googleMap)
	{
        m_googleMap = googleMap;
        m_mapView.init(googleMap);
        initMap();
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //check for permissions
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            requestMultiplePermissions(m_inflater, m_container, savedInstanceState);
        }
        else
        {
            m_mapView.getMapAsync(this);
        }
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
            m_mapView.getMapAsync(this);
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
                    m_mapView.getMapAsync(this);
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
	 * Initializes the map with specific settings
	 */
	private void initMap()
    {
        //create an instance of the AerisAMP class
		AerisAMP aerisAMP = new AerisAMP(getString(R.string.aerisapi_client_id), getString(R.string.aerisapi_client_secret));

        setHasOptionsMenu(true);

        //instantiate the AerisMapOptions class
        m_mapOptions = AerisMapOptions.getPreference(getActivity());

        //set the mapOptions AerisAMP pointer
        m_mapOptions.setAerisAMP(aerisAMP);

		if (m_mapOptions == null)
		{
			//gets map options with default settings
            m_mapOptions = AerisMapOptions.getPreference(getActivity(), true);
            //m_mapOptions.withPointData(AerisPointData.NONE);
			m_mapOptions.setPointData(AerisPointData.NONE);
            m_mapOptions.withPolygon(AerisPolygonData.NONE);
            //m_mapOptions.withTile(AerisTile.RADAR);

			//set the AmpLayer defaults
			m_mapOptions.setDefaultAmpLayers();

            //save the map options
            m_mapOptions.setPreference(getActivity());
		}

		//display the map with the options we specified
        //m_mapView.displayMapWithOptions(m_mapOptions); //this is tiles

		//add the AMP layers
		m_mapView.addLayer(aerisAMP);

		//m_mapView.addLayer(m_mapOptions.getTile());
        m_mapView.addLayer(m_mapOptions.getPolygon());
        m_mapView.addLayer(m_mapOptions.getPointData());

		//get a new marker option object
		MarkerOptions markerOptions = new MarkerOptions();

		//get a stored location if there is one
		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();

		if (place == null)
        {
            //TODO Make sure this is set for prod
            /*
			LatLng mpls = new LatLng(44.986656, -93.258133);
            m_mapView.moveToLocation(mpls, 9);
            markerOptions.position(mpls);
            */

			//we didn't find a stored location, so get the current location
            m_locHelper = new LocationHelper(getActivity());
			Location myLocation = m_locHelper.getCurrentLocation();

			//move the map to the location
            m_mapView.moveToLocation(myLocation, 9);

			//set the marker location
			markerOptions.position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));

		}
        else
        {
			//we found a stored location so move the map to it
            m_mapView.moveToLocation(new LatLng(place.latitude, place.longitude),	9);

			//set the marker location
			markerOptions.position(new LatLng(place.latitude, place.longitude));
		}

		//add the marker with specified options
		if (m_googleMap != null)
			m_googleMap.addMarker(markerOptions);

		//do something when a user makes a long click
        m_mapView.setOnAerisMapLongClickListener(this);

		// setup the custom info window adapter to use
        m_infoAdapter = new TemperatureWindowAdapter(getActivity());
        m_mapView.addWindowInfoAdapter(m_infoAdapter);

		//do something when a user presses an info window from the Aeris Point Data.
        m_mapView.setOnAerisWindowClickListener(this);

		db.close();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int itemId = item.getItemId();
		if (itemId == R.id.menu_weather_layers)
		{
			//start the custom map options activity
            getActivity().startActivity(new Intent(getActivity(), MapOptionsLocalActivity.class));
			return false;
		}
		else
		{
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();

		//we are resuming the map view, so check for updated options
        if (m_mapView != null)
        {
            if (m_mapOptions != null)
            {
                m_mapOptions = AerisMapOptions.getPreference(getActivity());
                m_mapView.displayMapWithOptions(m_mapOptions);
                m_mapView.addLayer(m_mapOptions.getPointData());
                m_mapView.addLayer(m_mapOptions.getPolygon());
            }

			//tell the map to redraw itself
            m_mapView.onResume();
        }
	}

	@Override
	public void onPause()
	{
		super.onPause();
		m_mapView.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		m_mapView.onDestroy();
	}

	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		m_mapView.onLowMemory();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_maps_fragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onMapLongClick(double lat, double longitude)
	{
		ParameterBuilder builder = new ParameterBuilder().withFields(
				ObservationFields.ICON, ObservationFields.TEMP_C,
				ObservationFields.TEMP_F, Fields.RELATIVE_TO);
		ObservationsTask task = new ObservationsTask(getActivity(), this);
		task.requestClosest(new PlaceParameter(lat, longitude), builder.build());
	}

	@Override
	public void onResult(EndpointType type, AerisResponse response)
	{
		if (type == EndpointType.OBSERVATIONS)
		{
			if (response.isSuccessfulWithResponses())
			{
				ObservationResponse obResponse = new ObservationResponse(response.getFirstResponse());
				Observation ob = obResponse.getObservation();
				RelativeTo relativeTo = obResponse.getRelativeTo();
				if (m_marker != null)
				{
                    m_marker.remove();
				}

				TemperatureInfoData data = new TemperatureInfoData(ob.icon,	String.valueOf(ob.tempF));
                m_marker = m_infoAdapter.addGoogleMarker(
						m_mapView.getMap(),
						relativeTo.lat,
						relativeTo.lon,
						BitmapDescriptorFactory.fromResource(R.drawable.map_indicator_blank),
						data);
                m_marker.showInfoWindow();
			}
		}
	}

	@Override
	public void earthquakeWindowPressed(EarthquakesResponse response, AerisMarker marker)
	{
		// do something with the response data.
		Toast.makeText(getActivity(), "Earthquake pressed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void stormReportsWindowPressed(StormReportsResponse response, AerisMarker marker)
	{
		// do something with the response data.
		Toast.makeText(getActivity(), "Storm Report pressed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void stormCellsWindowPressed(StormCellResponse response, AerisMarker marker)
	{
		// do something with the response data.
		Toast.makeText(getActivity(), "Storm Cell pressed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void wildfireWindowPressed(FiresResponse response, AerisMarker marker)
	{
		// do something with the response data.
		Toast.makeText(getActivity(), "Wildfire pressed!", Toast.LENGTH_SHORT).show();
	}

    @Override
    public void recordsWindowPressed(RecordsResponse response, AerisMarker marker)
	{
        // do something with the response data.
        Toast.makeText(getActivity(), "Daily Record pressed!", Toast.LENGTH_SHORT).show();
    }

	@Override
	public void onObservationsFailed(AerisError arg0)
	{
		// do something with the response
		Toast.makeText(getActivity(), "Failed to load observation at that point", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onObservationsLoaded(List<ObservationResponse> responses)
	{
		ObservationResponse obResponse = responses.get(0);
		Observation ob = obResponse.getObservation();
		RelativeTo relativeTo = obResponse.getRelativeTo();

		if (m_marker != null)
		{
            m_marker.remove();
		}

		TemperatureInfoData data = new TemperatureInfoData(ob.icon,	String.valueOf(ob.tempF));
        m_marker = m_infoAdapter.addGoogleMarker(
				m_mapView.getMap(),
				relativeTo.lat,
				relativeTo.lon,
				BitmapDescriptorFactory.fromResource(R.drawable.map_indicator_blank),
				data);
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
