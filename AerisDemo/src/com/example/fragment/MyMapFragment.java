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

import com.aerisweather.aeris.maps.AerisMapContainerView;
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
import com.aerisweather.aeris.model.AerisPermissions;
import com.aerisweather.aeris.response.EarthquakesResponse;
import com.aerisweather.aeris.response.FiresResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.aerisweather.aeris.response.StormCellResponse;
import com.aerisweather.aeris.response.StormReportsResponse;
import com.aerisweather.aeris.response.RecordsResponse;
import com.aerisweather.aeris.tiles.AerisPointData;;
import com.aerisweather.aeris.tiles.AerisPolygonData;
import com.aerisweather.aeris.tiles.AerisAmp;
import com.aerisweather.aeris.tiles.AerisAmpGetLayersTask;
import com.aerisweather.aeris.tiles.AerisAmpLayer;
import com.aerisweather.aeris.tiles.AerisAmpOnGetLayersTaskCompleted;


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
	protected AerisMapView m_aerisMapView;
    private AerisMapOptions m_mapOptions = null;
	private AerisAmp m_aerisAmp;
	private boolean m_isMapReady = false;
	private boolean m_isAmpReady = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_inflater = inflater;
        m_container = container;
        m_savedInstanceState = savedInstanceState;

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
		AerisMapContainerView mapContainer = (AerisMapContainerView) view.findViewById(R.id.mapView);
		m_aerisMapView = mapContainer.getAerisMapView();
		m_aerisMapView.onCreate(savedInstanceState);

		//create an instance of the AerisAMP class
		m_aerisAmp = new AerisAmp(getString(R.string.aerisapi_client_id), getString(R.string.aerisapi_client_secret));

		//start the task to get the AMP layers
		try
		{
			//get all the possible layers, then get permissions from the API and generate a list of permissible layers
			new AerisAmpGetLayersTask(new GetLayersTaskCallback(), m_aerisAmp).execute().get();
		}
		catch (Exception ex)
		{
			String s = ex.getMessage();
			//if the task fails, keep going without AMP layers
		}

		return view;
	}

	@Override
	public void onMapReady(GoogleMap googleMap)
	{
		m_isMapReady = true;
		m_googleMap = googleMap;
		m_aerisMapView.init(googleMap);

		if (m_isAmpReady)
		{
			initMap();
		}
	}

	public class GetLayersTaskCallback implements AerisAmpOnGetLayersTaskCompleted
	{
        public GetLayersTaskCallback() { }

        public void onAerisAmpGetLayersTaskCompleted(ArrayList<AerisAmpLayer> permissibleLayers,
													 AerisPermissions permissions)
		{
            m_isAmpReady = true;

            if (m_isMapReady)
            {
                initMap();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //check for permissions
        if ((ContextCompat.checkSelfPermission(getActivity(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(getActivity(),
								Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            requestMultiplePermissions(m_inflater, m_container, savedInstanceState);
        }
        else
        {
			m_aerisMapView.getMapAsync(this);
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
			m_aerisMapView.getMapAsync(this);
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
					m_aerisMapView.getMapAsync(this);
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.permissions_verbiage,
							Toast.LENGTH_LONG).show();
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
		m_aerisMapView.setUseMapOptions(true);

		setHasOptionsMenu(true);

		//create a new MapOptions obj
		m_mapOptions = new AerisMapOptions();
		m_aerisAmp.getPermissibleLayers(false);

		//set the mapOptions class's AerisAMP obj
		m_mapOptions.setAerisAMP(m_aerisAmp);

		if (!m_mapOptions.getMapPreferences(getActivity()))
		{
			//set default layers/data
			m_mapOptions.setDefaultAmpLayers();
			m_mapOptions.setPointData(AerisPointData.NONE);
			m_mapOptions.setPolygonData(AerisPolygonData.NONE);

            //save the map options
            m_mapOptions.saveMapPreferences(getActivity());
		}

		m_aerisMapView.getMap().setMapType(m_mapOptions.getMapType());

        //amp layer(s)
		AerisAmp aerisAmp = m_mapOptions.getAerisAMP();
		/**
		 * CUSTOM / UNDOCUMENTED LAYER
		 */
		/*
		AerisAmpLayer customAmpLayer = new AerisAmpLayer("pressure-msl-nam", "pressure-msl-nam", 80);
		aerisAmp.setLayer(customAmpLayer);
		*/
		/**
		 * SETTING LAYER MODIFIERS
		 */
		/*
        AerisAmpLayer statesAmpLayer = aerisAmp.getLayerFromId("states");
        AerisAmpLayer.Modifier stateModifier = statesAmpLayer.getLayerModifier("States Outlines");
        stateModifier.setModifierOption("outlines", true);
		aerisAmp.setLayer(statesAmpLayer);
		*/

		if (aerisAmp.getActiveMapLayers().size() < 1)
		{
			aerisAmp.setDefaultLayers();
		}
		m_aerisMapView.addLayer(aerisAmp);

        //point data layer(s)
		m_aerisMapView.addLayer(m_mapOptions.getPointData());

		/**
		 * SAMPLE: TROPICAL CYCLONES POINT DATA
		 */
//		m_aerisMapView.addLayer(AerisPointData.TROPICAL_CYCLONES);

		//polygon layer(s)
		/**
		 * SAMPLE: DAY TWO CONVECTIVE
		 */
		/*
		AerisPolygonData aerisPolygonData = m_mapOptions.getPolygon();
		aerisPolygonData.setConvectiveOutlookParameters(new ParameterBuilder().
				withFilter(Filter.DAY_TWO.getCode() + "," + Filter.GEO_POLY.getCode()).
				withCustomParameter("from", "today"));
        m_aerisMapView.addLayer(aerisPolygonData);
        */

		/**
		 * SAMPLE: TROPICAL CYCLONES ERROR CONES
		 */
		AerisPolygonData aerisPolygonData = AerisPolygonData.TROPICAL_CYCLONE_ERROR_CONES;
		m_aerisMapView.addLayer(aerisPolygonData);

//		m_aerisMapView.addLayer(m_mapOptions.getPolygonData());

		//get a new marker option object
		MarkerOptions markerOptions = new MarkerOptions();

		//get a stored location if there is one
		MyPlacesDb db = new MyPlacesDb(getActivity());
		MyPlace place = db.getMyPlace();
		Location myLocation = null;

		if (place == null)
        {
			//we didn't find a stored location, so get the current location
            m_locHelper = new LocationHelper(getActivity());
			myLocation = m_locHelper.getCurrentLocation();

			//move the map to the location
			m_aerisMapView.moveToLocation(myLocation, 9);

			//set the marker location
			if (myLocation != null)
				markerOptions.position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
		}
        else
        {
			//we found a stored location so move the map to it
			m_aerisMapView.moveToLocation(new LatLng(place.latitude, place.longitude),	9);

			//set the marker location
			markerOptions.position(new LatLng(place.latitude, place.longitude));
		}

		//add the marker with specified options
		if ((m_googleMap != null) && (myLocation != null))
			m_googleMap.addMarker(markerOptions);

		//do something when a user makes a long click
		m_aerisMapView.setOnAerisMapLongClickListener(this);

		// setup the custom info window adapter to use
        m_infoAdapter = new TemperatureWindowAdapter(getActivity());
		m_aerisMapView.addWindowInfoAdapter(m_infoAdapter);

		//do something when a user presses an info window from the Aeris Point Data.
		m_aerisMapView.setOnAerisWindowClickListener(this);

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
        if (m_aerisMapView != null)
        {
            if (m_mapOptions != null)
            {
                m_mapOptions.getMapPreferences(getActivity());

				m_aerisMapView.getMap().setMapType(m_mapOptions.getMapType());

				m_aerisMapView.addLayer(m_mapOptions.getAerisAMP());
				m_aerisMapView.addLayer(m_mapOptions.getPointData());
				m_aerisMapView.addLayer(m_mapOptions.getPolygonData());
            }

			//tell the map to redraw itself
			m_aerisMapView.onResume();
        }
	}

	@Override
	public void onPause()
	{
		super.onPause();
		m_aerisMapView.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		m_aerisMapView.onDestroy();
	}

	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		m_aerisMapView.onLowMemory();
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
						m_aerisMapView.getMap(),
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
				m_aerisMapView.getMap(),
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
			m_aerisMapView.moveToLocation(new LatLng(place.latitude, place.longitude),	9);
		}
        else
        {
            m_locHelper = new LocationHelper(getActivity());
			Location myLocation = m_locHelper.getCurrentLocation();
			m_aerisMapView.moveToLocation(myLocation, 9);
		}

		db.close();
	}
}
