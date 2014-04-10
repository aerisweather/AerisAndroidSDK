package com.hamweather.aeris.maps.handlers;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.maps.markers.AerisMarkerType;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.response.EarthquakesResponse;
import com.hamweather.aeris.tiles.AerisPointData;

public class EarthquakesPointHandler extends AerisPointHandler {

	public EarthquakesPointHandler(AerisMapView mapView) {
		super(mapView, AerisPointData.EARHQUAKES);
	}

	@Override
	AerisMarker getMarkerFromData(AerisDataJSON data) {
		EarthquakesResponse reports = new EarthquakesResponse(data);
		if (reports.getReport() != null && reports.getReport().type != null) {
			return new AerisMarker(reports.getLocation(),
					AerisMarkerType.quakeFromCode(reports.getReport().type));
		} else {
			return null;
		}
	}

}
