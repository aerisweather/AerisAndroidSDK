package com.hamweather.aeris.maps.handlers;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.maps.markers.AerisMarkerType;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.response.StormReportsResponse;
import com.hamweather.aeris.tiles.AerisPointData;

public class ReportsPointHandler extends AerisPointHandler {

	public ReportsPointHandler(AerisMapView mapView) {
		super(mapView, AerisPointData.STORM_REPORTS);
	}

	@Override
	AerisMarker getMarkerFromData(AerisDataJSON data) {
		StormReportsResponse reports = new StormReportsResponse(data);
		if (reports.getReport() != null && reports.getReport().code != null) {
			return new AerisMarker(reports.getLocation(),
					AerisMarkerType.reportFromCode(reports.getReport().code));

		} else {
			return null;
		}
	}

}
