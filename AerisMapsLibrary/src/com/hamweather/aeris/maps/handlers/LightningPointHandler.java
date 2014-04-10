package com.hamweather.aeris.maps.handlers;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.maps.markers.AerisMarkerType;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.response.StormReportsResponse;
import com.hamweather.aeris.tiles.AerisPointData;

public class LightningPointHandler extends AerisPointHandler {

	public LightningPointHandler(AerisMapView mapView) {
		super(mapView, AerisPointData.LIGHTNING_STRIKES);
	}

	@Override
	AerisMarker getMarkerFromData(AerisDataJSON data) {
		StormReportsResponse reports = new StormReportsResponse(data);
		if (reports.getReport() != null
				&& reports.getReport().timestamp != null) {
			return new AerisMarker(
					reports.getLocation(),
					AerisMarkerType.lightningFromTime(reports.getReport().timestamp
							.longValue()));
		} else {
			return null;
		}
	}

}
