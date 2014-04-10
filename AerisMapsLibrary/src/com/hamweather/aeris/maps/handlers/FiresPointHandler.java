package com.hamweather.aeris.maps.handlers;

import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.maps.markers.AerisMarkerType;
import com.hamweather.aeris.model.AerisDataJSON;
import com.hamweather.aeris.response.FiresResponse;
import com.hamweather.aeris.tiles.AerisPointData;

public class FiresPointHandler extends AerisPointHandler {

	public FiresPointHandler(AerisMapView mapView) {
		super(mapView, AerisPointData.FIRE);
	}

	@Override
	AerisMarker getMarkerFromData(AerisDataJSON data) {
		FiresResponse fResponse = new FiresResponse(data);
		return new AerisMarker(fResponse.getLocation(), AerisMarkerType.FIRE);
	}

}
