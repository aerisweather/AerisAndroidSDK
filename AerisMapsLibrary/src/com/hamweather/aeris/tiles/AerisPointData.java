package com.hamweather.aeris.tiles;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.hamweather.aeris.communication.Action;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.communication.Endpoint;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.parameter.FilterParameter;
import com.hamweather.aeris.communication.parameter.FromParameter;
import com.hamweather.aeris.communication.parameter.LimitParameter;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.communication.parameter.SortParameter;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.R;
import com.hamweather.aeris.maps.handlers.AerisPointHandler;
import com.hamweather.aeris.maps.handlers.EarthquakesPointHandler;
import com.hamweather.aeris.maps.handlers.FiresPointHandler;
import com.hamweather.aeris.maps.handlers.LightningPointHandler;
import com.hamweather.aeris.maps.handlers.ReportsPointHandler;
import com.hamweather.aeris.maps.handlers.StormCellPointHandler;

public enum AerisPointData {
	NONE(0, "None"), FIRE(0, "Wildfires"), STORM_CELLS(R.drawable.stormcells,
			"Storm Cells"), STORM_REPORTS(R.drawable.stormreports,
			"Storm Reports"), CLIMATE_RECORDS(R.drawable.records,
			"Climate Records"), EARHQUAKES(R.drawable.earthquakes,
			"Earthquakes"), LIGHTNING_STRIKES(R.drawable.lightning,
			"Lightning Strikes");

	private static final int DEFAULT_LIMIT = 500;

	private int legend;
	public String name;

	private AerisPointData(int legend, String name) {
		this.legend = legend;
		this.name = name;
	}

	public int getLegend() {
		return legend;
	}

	public AerisRequest getRequest(AerisMapView mapView) {
		VisibleRegion region = mapView.getMap().getProjection()
				.getVisibleRegion();
		LatLng northeast = region.latLngBounds.northeast;
		LatLng southwest = region.latLngBounds.southwest;
		return getRequest(northeast.latitude, northeast.longitude,
				southwest.latitude, southwest.longitude);
	}

	public AerisRequest getRequest(double nLat, double wLong, double sLat,
			double eLong) {
		AerisRequest request = null;
		Action action = Action.WITHIN;
		PlaceParameter place = new PlaceParameter(nLat, wLong, sLat, eLong);
		LimitParameter limit = new LimitParameter(DEFAULT_LIMIT);
		FromParameter from = new FromParameter("-24hours");
		SortParameter sort = new SortParameter("dt:-1");
		Endpoint endpoint = null;
		switch (this) {
		case FIRE:
			endpoint = new Endpoint(EndpointType.FIRES);
			request = new AerisRequest(endpoint, action, place, limit);
			break;
		case STORM_REPORTS:
			endpoint = new Endpoint(EndpointType.STORMREPORTS);
			request = new AerisRequest(endpoint, action, place, limit, from,
					sort);
			break;
		case EARHQUAKES:
			endpoint = new Endpoint(EndpointType.EARTHQUAKES);
			request = new AerisRequest(endpoint, action, place, limit, from);
			break;
		case LIGHTNING_STRIKES:
			endpoint = new Endpoint(EndpointType.STORMREPORTS);
			from = new FromParameter("-5hours");
			request = new AerisRequest(endpoint, action, place, limit, from,
					new FilterParameter("lightning"));
			break;
		case STORM_CELLS:
			endpoint = new Endpoint(EndpointType.STORMECELLS);
			sort = new SortParameter("tvs:-1,mda:-1,hail:-1");
			request = new AerisRequest(endpoint, action, place, limit, from,
					sort);
		default:
			break;

		}

		request.withDebugOutput(true);
		return request;
	}

	public AerisPointHandler getHandler(AerisMapView mapView) {
		switch (this) {
		case FIRE:
			return new FiresPointHandler(mapView);
		case EARHQUAKES:
			return new EarthquakesPointHandler(mapView);
		case STORM_REPORTS:
			return new ReportsPointHandler(mapView);
		case LIGHTNING_STRIKES:
			return new LightningPointHandler(mapView);
		case STORM_CELLS:
			return new StormCellPointHandler(mapView);
		default:
			return null;
		}

	}

	public static AerisPointData getPointDataFromName(String name) {
		for (AerisPointData data : values()) {
			if (data.name.equals(name)) {
				return data;
			}
		}
		return null;
	}

	/**
	 * Gets a list of the String for displaying in lists.
	 * 
	 * @return
	 */
	public static List<String> getStringList() {
		AerisPointData[] codes = AerisPointData.values();
		List<String> stringCode = new ArrayList<String>();
		for (int i = 0; i < codes.length; i++) {
			stringCode.add(codes[i].name);
		}
		return stringCode;
	}

}
