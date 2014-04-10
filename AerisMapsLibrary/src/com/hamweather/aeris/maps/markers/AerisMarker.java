package com.hamweather.aeris.maps.markers;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.model.AerisLocation;

/**
 * Marker class for displaying an Aeris marker on the map.
 * 
 * @author bcollins
 * 
 */
public class AerisMarker {

	private AerisLocation location;
	private AerisMarkerType type;

	/**
	 * Constructor to initialize the AerisMarker with a location and an
	 * AerisMarkerType
	 * 
	 * @param location
	 *            location of the marker
	 * @param type
	 *            type of the marker
	 */
	public AerisMarker(AerisLocation location, AerisMarkerType type) {
		this.location = location;
		this.type = type;
	}


	/**
	 * Adds the marker to the list. This does a null check to make sure the
	 * marker was made properly before adding it to the list. If it is not, it
	 * will not be added to the list.
	 * 
	 * @param markers
	 *            markers list to add to
	 */
	public void addMarkerToList(List<AerisMarker> markers) {
		if (location != null && type != null) {
			markers.add(this);
		}
	}

	/**
	 * Get the location of the marker.
	 * 
	 * @return the location
	 */
	public AerisLocation getLocation() {
		return location;
	}

	/**
	 * Get the location as a Google friendly LatLng object.
	 * 
	 * @return LatLng version of the location.
	 */
	public LatLng getLocationAsLatLng() {
		if (location == null) {
			return null;
		}
		return new LatLng(location.lat, location.lon);
	}

	/**
	 * Gets the icon of the AerisMarker.
	 * 
	 * @return the icon
	 */
	public int getIcon() {
		if (type == null) {
			return 0;
		}
		return type.getIcon();
	}

	/**
	 * Gets the marker type of the AerisMarker.
	 * 
	 * @return the marker type
	 */
	public AerisMarkerType getMarkerType() {
		return type;
	}

}
