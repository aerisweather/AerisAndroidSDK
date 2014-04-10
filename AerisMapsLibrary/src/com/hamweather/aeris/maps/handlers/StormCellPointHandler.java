package com.hamweather.aeris.maps.handlers;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.maps.markers.AerisMarkerType;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.model.CellObservation;
import com.hamweather.aeris.response.StormCellResponse;
import com.hamweather.aeris.tiles.AerisPointData;

public class StormCellPointHandler extends AerisPointHandler {

	public StormCellPointHandler(AerisMapView mapView) {
		super(mapView, AerisPointData.STORM_CELLS);
	}

	@Override
	AerisMarker getMarkerFromData(AerisDataJSON data) {
		StormCellResponse response = new StormCellResponse(data);
		if (response.getObservation() != null
				&& response.getObservation().mda != null
				&& response.getObservation().tvs != null) {
			CellObservation ob = response.getObservation();
			return new AerisMarker(response.getLocation(),
					AerisMarkerType.cellFromValues(ob.tvs.intValue(),
							ob.mda.intValue(), ob.hail.prob.intValue()));
		} else {
			return null;
		}
	}

}
