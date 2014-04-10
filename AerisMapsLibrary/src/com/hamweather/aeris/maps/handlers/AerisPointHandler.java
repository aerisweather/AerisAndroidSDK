package com.hamweather.aeris.maps.handlers;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisPointData;

/**
 * Aeris abstract handler for helping handle the response from the
 * AerisCallback, and then notifying the mapview to display the result.
 * 
 * @author bcollins
 * 
 */
public abstract class AerisPointHandler implements AerisCallback {

	protected WeakReference<AerisMapView> mapView;
	protected AerisPointData pointData;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hamweather.aeris.communication.AerisCallback#onResult(com.hamweather
	 * .aeris.communication.EndpointType,
	 * com.hamweather.aeris.model.AerisResponse)
	 */
	@Override
	public void onResult(EndpointType endpoint, AerisResponse response) {
		handleResponse(response);
	}

	/**
	 * Handler constructor. Requires an instance of the AerisMapView to populate
	 * with Markers on hanlding a response.
	 * 
	 * @param mapView
	 *            MapView to populate on finish.
	 * @param pointData
	 *            point data type to populate.
	 */
	public AerisPointHandler(AerisMapView mapView, AerisPointData pointData) {
		this.mapView = new WeakReference<AerisMapView>(mapView);
		this.pointData = pointData;
	}

	/**
	 * Handles the response from the server.
	 * 
	 * @param response
	 */
	public void handleResponse(AerisResponse response) {
		if (mapView.get() == null) {
			mapView.clear();
			return;
		}
		List<AerisMarker> markers = new ArrayList<AerisMarker>();
		if (response.isSuccessful() && response.getError() == null
				&& response.getListOfResponse() != null) {

			for (AerisDataJSON data : response.getListOfResponse()) {
				AerisMarker marker = getMarkerFromData(data);
				if (marker != null) {
					marker.addMarkerToList(markers);
				}
			}

		}
		mapView.get().displayAerisMarkers(markers, pointData);
		mapView.clear();
	}

	/**
	 * Gets the AerisMarker from the data object. Subclass implementation should
	 * know how to interact with data and pass back an Aeris Marker.
	 * 
	 * @param data
	 *            AerisDataJSON to use.
	 * @return AerisMarker based on the data.
	 */
	abstract AerisMarker getMarkerFromData(AerisDataJSON data);

}
