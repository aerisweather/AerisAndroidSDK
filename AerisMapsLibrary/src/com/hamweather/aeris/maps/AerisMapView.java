package com.hamweather.aeris.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.hamweather.aeris.communication.AerisCommunicationTask;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.logging.Logger;
import com.hamweather.aeris.maps.markers.AerisMarker;
import com.hamweather.aeris.tiles.AerisPointData;
import com.hamweather.aeris.tiles.AerisTile;
import com.hamweather.aeris.tiles.AerisTileProvider;
import com.hamweather.aeris.tiles.RadarAnimationTileProvider;
import com.hamweather.aeris.util.MathUtil;

/**
 * Aeris map view that provides features to automatically query Aeris API for
 * tiles, point data information on warnings, lightning, fires, and storms.
 * 
 * TODO: Polygon points, animation support, animation control support, support
 * multiple map types other than just google
 * 
 * @author Ben Collins
 * 
 */
public class AerisMapView extends FrameLayout implements OnCameraChangeListener {

	private static final String TAG = AerisMapView.class.getSimpleName();

	/**
	 * Aeris map types to initialize with. Currently only google is supported.
	 * 
	 * @author bcollins
	 * 
	 */
	public enum AerisMapType {
		/**
		 * Google map type. Documented <a
		 * href="https://developers.google.com/maps/documentation/android/"
		 * >Here.<a/>
		 */
		GOOGLE;
	};

	public static final String ZOOM_INTENT = "zoom_extra";

	private GoogleMap googleMap;
	private TileOverlay tileOverlay;
	private AerisTile tile;
	private TileOverlay animationOverlay;
	private MapView mapView;
	private ImageView animationView;
	private AerisMapType type;
	private ImageView legendsImageView;
	private ImageView pointsLegendsImageView;
	private List<Marker> pointMarkers;
	private long moveTimer = 0;
	private AerisCommunicationTask task;

	/**
	 * Constructor for the AerisMapView. This is used to inflate the
	 * AerisMapView from a xml file.
	 * 
	 * @param context
	 *            Context for which this mapview will belong to
	 * @param attrs
	 *            Attribute set to use
	 */
	public AerisMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_aerismapview, this, true);
		animationView = (ImageView) this.findViewById(R.id.ivAnimation);
		legendsImageView = (ImageView) this.findViewById(R.id.ivLegend);
		pointsLegendsImageView = (ImageView) this
				.findViewById(R.id.ivPointLegend);
		mapView = (MapView) this.findViewById(R.id.mvAeris);

	}

	/**
	 * Must be called to setup the AerisMapView. THis is to ensure the oncreate
	 * is passed, if needed, to the MapView object of the desired type
	 * 
	 * @param savedInstanceState
	 *            the saved instance state from activity/fragment
	 * @param type
	 *            type of mapview to use.
	 */
	public void init(Bundle savedInstanceState, AerisMapType type) {

		this.type = type;
		switch (type) {

		case GOOGLE:
			mapView.onCreate(savedInstanceState);
			try {
				MapsInitializer.initialize(getContext());
			} catch (GooglePlayServicesNotAvailableException e) {
				Logger.e(TAG, e.getMessage(), e);
			}
			googleMap = mapView.getMap();
			googleMap.setOnCameraChangeListener(this);
			break;
		}

	}

	/**
	 * Sets that the compass enabled
	 * 
	 * @param enabled
	 *            boolean to enable the compass
	 */
	public void setCompassEnabled(boolean enabled) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.getUiSettings().setCompassEnabled(enabled);
		}
	}

	/**
	 * Set the my location button enabled
	 * 
	 * @param enabled
	 *            boolean to enable the my location button
	 */
	public void setMyLocationButtonEnabled(boolean enabled) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.getUiSettings().setMyLocationButtonEnabled(enabled);
		}
	}

	/**
	 * Sets whether the zoom controls are enabled.
	 * 
	 * @param enabled
	 */
	public void setZoomControlsEnabled(boolean enabled) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.getUiSettings().setZoomControlsEnabled(enabled);
		}
	}

	/**
	 * Sets whether all gestures are enabled.
	 * 
	 * @param enabled
	 *            boolean to enable all gestures or not.
	 */
	public void setAllGesturesEnabled(boolean enabled) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.getUiSettings().setAllGesturesEnabled(enabled);
		}
	}

	/**
	 * Sets whether to display the users location on the map
	 * 
	 * @param enabled
	 *            whetehr or not to display the users location
	 */
	public void setMyLocationEnabled(boolean enabled) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.setMyLocationEnabled(enabled);
		}
	}

	/**
	 * Animates the camera to the given Location
	 * 
	 * @param location
	 *            Location to move to
	 * @param zoomLevel
	 *            Zoom level to move to
	 */
	public void moveToLocation(Location location) {
		if (type == AerisMapType.GOOGLE) {
			moveToLocation(location, googleMap.getCameraPosition().zoom);
		}
	}

	/**
	 * Animates the camera to the given Location
	 * 
	 * @param location
	 *            Location to move to
	 * @param zoomLevel
	 *            Zoom level to move to
	 */
	public void moveToLocation(Location location, float zoomLevel) {
		if (type == AerisMapType.GOOGLE) {
			if (location != null) {
				CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(
						new LatLng(location.getLatitude(), location
								.getLongitude()), zoomLevel);
				googleMap.moveCamera(upd);
			}
		}
	}

	/**
	 * Animates the camera to the given Latlng without changing zoom level.
	 * 
	 * @param loc
	 *            LatLng to move to
	 */
	public void moveToLocation(LatLng loc) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,
					googleMap.getCameraPosition().zoom));
		}

	}

	/**
	 * Animates the camera to the given Latlnb
	 * 
	 * @param loc
	 *            LatLng to move to
	 * @param zoomLevel
	 *            Zoom level to move to
	 */
	public void moveToLocation(LatLng loc, float zoomLevel) {
		if (type == AerisMapType.GOOGLE) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,
					zoomLevel));
		}
	}

	/**
	 * Changes the map to use the new options. TODO: Affect animation speed
	 * 
	 * @param options
	 *            options to use in showing.
	 */
	public void displayMapWithOptions(AerisMapOptions options) {
		if (type == AerisMapType.GOOGLE) {
			this.setTiles(options.getTile(), options.getOpacity());
			googleMap.setMapType(options.getMapType());
		}
	}

	/**
	 * Sets the tiles of the weather overlays.
	 * 
	 * @param tile
	 *            The tile type to set as.
	 */
	public void setTiles(AerisTile tile, int opacity) {
		if (type == AerisMapType.GOOGLE) {
			if (tileOverlay != null) {
				tileOverlay.remove();
				tileOverlay = null;
			}
			if (tile != AerisTile.NONE) {
				this.tile = tile;
				tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions()
						.tileProvider(new AerisTileProvider(tile, opacity)));

			}
			if (tile.getLegend() != 0) {
				legendsImageView.setImageResource(tile.getLegend());
				legendsImageView.setVisibility(View.VISIBLE);
			} else {
				legendsImageView.setVisibility(View.GONE);
			}
		}

	}

	/**
	 * Loads the point data for the given point data type.
	 * 
	 * @param data
	 *            the point data type to load
	 */
	public void loadPointData(AerisPointData data) {
		if (data.getLegend() != 0) {
			pointsLegendsImageView.setImageResource(data.getLegend());
			pointsLegendsImageView.setVisibility(View.VISIBLE);
		} else {
			pointsLegendsImageView.setVisibility(View.GONE);
		}
		if (data != AerisPointData.NONE) {
			clearOldMarkers();
			AerisRequest request = data.getRequest(this);
			if (task != null) {
				task.cancel(true);
			}
			task = new AerisCommunicationTask(mapView.getContext(),
					data.getHandler(this), request);
			// TODO: add default progress listener and custom progress listener
			// options
			task.execute();
		}
	}

	/**
	 * Sets the animation tile
	 * 
	 * @param tile
	 *            sets the radar animations tile
	 */
	public void setAnimationTile(RadarAnimationTileProvider tile) {
		if (type == AerisMapType.GOOGLE) {
			if (animationOverlay != null) {
				animationOverlay.remove();
			}
			animationOverlay = googleMap
					.addTileOverlay(new TileOverlayOptions().tileProvider(tile));
		}
	}

	/**
	 * Converts the latlng to a map tile coordinate
	 * 
	 * @param latlng
	 *            The latlng to find a point at
	 * @param zoom
	 *            the zoom to find a point at
	 * @return Point that contains the x,y map tile coordinate
	 */
	public static Point fromLatLng(LatLng latlng, int zoom) {
		PointF point = normalizedMercatorCoords(toMercatorCoords(latlng));
		double scale = Math.pow(2, zoom);
		double x = (point.x * scale);
		double y = (point.y * scale);
		return new Point((int) x, (int) y);
	}

	/**
	 * Converts the latlng to mercator coordinates
	 * 
	 * @param latlng
	 *            the latlng to convert
	 * @return point that has the x,y for the latlng
	 */
	public static PointF toMercatorCoords(LatLng latlng) {
		float longitude = (float) latlng.longitude;
		float lat = (float) latlng.latitude;
		longitude += 180;
		longitude /= 360;
		lat = (float) ((MathUtil.asinh(Math.tan(Math.toRadians(lat))))
				/ Math.PI / 2);

		return new PointF(longitude, lat);
	}

	/**
	 * Normalizes a Mercator point.
	 * 
	 * @param p
	 *            point to normalize.
	 * @return
	 */
	public static PointF normalizedMercatorCoords(PointF p) {
		// p.x += .5;
		p.y = (float) Math.abs(p.y - .5);
		return p;
	}

	/**
	 * Gets the map boundaries of the current AerisMapview
	 * 
	 * @return returns two points in a array, the first being the northeast
	 *         point, and the second the southwest point
	 */
	public Point[] getMapBoundaries() {
		CameraPosition current = googleMap.getCameraPosition();
		if (current.tilt > 0 || current.bearing > 0) {
			googleMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(new CameraPosition.Builder().tilt(0)
							.bearing(0).target(current.target)
							.zoom(current.zoom).build()));
		}
		VisibleRegion vr = googleMap.getProjection().getVisibleRegion();
		LatLng northeast = vr.latLngBounds.northeast;
		LatLng southwest = vr.latLngBounds.southwest;
		int zoom = getZoomForTiles();
		return new Point[] { fromLatLng(northeast, zoom),
				fromLatLng(southwest, zoom) };
	}

	/**
	 * Gets the zoom level for the tiles
	 * 
	 * @return the zoom level as an int for the map tiles
	 */
	public int getZoomForTiles() {
		return (int) (googleMap.getCameraPosition().zoom);
	}

	/**
	 * Gets the bounds for a specific tile.
	 * 
	 * @param x
	 *            x value of the tile
	 * @param y
	 *            y value of the tile.
	 * @param zoom
	 *            zoom of the tile
	 * @return LatLng bounds for a tile
	 */
	public static LatLngBounds getTileRect(int x, int y, int zoom) {
		double tiles = Math.pow(2, zoom);
		double lngWidth = 360 / tiles;
		double lng = -180 + (x * lngWidth);

		double latHeightMerc = 1.0 / tiles;
		double topLatMerc = y * latHeightMerc;
		double bottomLatMerc = topLatMerc + latHeightMerc;

		double bottomLat = (180 / Math.PI)
				* ((2 * Math
						.atan(Math.exp(Math.PI * (1 - (2 * bottomLatMerc))))) - (Math.PI / 2));
		double topLat = (180 / Math.PI)
				* ((2 * Math.atan(Math.exp(Math.PI * (1 - (2 * topLatMerc))))) - (Math.PI / 2));

		return new LatLngBounds(new LatLng(bottomLat, lng), new LatLng(topLat,
				lng + lngWidth));
	}

	/**
	 * Gets the Aeris Tile that is being displayed.
	 * 
	 * @return the tile
	 */
	public AerisTile getTile() {
		return tile;
	}

	/**
	 * Gets the tile overlay that is being used if one. Null, otherwise.
	 * 
	 * @return The tile overlay
	 */
	public TileOverlay getTileOverlay() {
		return tileOverlay;
	}

	/**
	 * Gets the animation view from the AerisMapView.
	 * 
	 * @return
	 */
	public ImageView getAnimationView() {
		return animationView;
	}

	public void setTileOverlayVisible(boolean visible) {
		if (tileOverlay != null) {
			tileOverlay.setVisible(visible);
		}
	}

	/**
	 * Gets the google map.
	 * 
	 * @return
	 */
	public GoogleMap getMap() {
		return googleMap;
	}

	/**
	 * Mimic call to pass to mapview from fragment/activity.
	 */
	public void onLowMemory() {
		mapView.onLowMemory();
	}

	/**
	 * Mimic call to pass to map view from fragment/activity.
	 * 
	 * @param bundle
	 *            bundle from fragment/activity
	 */
	public void onSaveInstanceState(Bundle state) {
		mapView.onSaveInstanceState(state);
	}

	/**
	 * Mimic call to pass to mapview from fragment/activity.
	 */
	public void onPause() {
		if (task != null) {
			task.cancel(true);
		}
		mapView.onPause();
	}

	/**
	 * Mimic call to pass to mapview from fragment/activity.
	 */
	public void onResume() {
		mapView.onResume();
		AerisMapOptions options = AerisMapOptions.getPreference(mapView
				.getContext());
		displayMapWithOptions(options);
		loadPointData(options.getPointData());
	}

	/**
	 * Mimic call to pass to mapview from fragment/activity.
	 */
	public void onDestroy() {
		mapView.onDestroy();
	}

	// TODO: Add marker on click handling actions
	public void displayAerisMarkers(List<AerisMarker> markers,
			AerisPointData pointData) {
		if (markers != null && markers.size() > 0) {
			for (AerisMarker marker : markers) {
				pointMarkers.add(googleMap.addMarker(new MarkerOptions()
						.position(marker.getLocationAsLatLng()).icon(
								BitmapDescriptorFactory.fromResource(marker
										.getIcon()))));
			}

		}

	}

	/**
	 * Clears old point data markers from the map
	 */
	public void clearOldMarkers() {
		if (pointMarkers != null) {
			for (Marker m : pointMarkers) {
				m.remove();
			}
			pointMarkers.clear();
		} else {
			pointMarkers = new ArrayList<Marker>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.maps.GoogleMap.OnCameraChangeListener#onCameraChange
	 * (com.google.android.gms.maps.model.CameraPosition)
	 */
	@Override
	public void onCameraChange(CameraPosition arg0) {
		long thisTime = System.currentTimeMillis();
		// TODO: Make time to perform load data after moving to a engine value

		if (thisTime - moveTimer > 2500) {
			AerisMapOptions options = AerisMapOptions
					.getPreference(getContext());
			AerisPointData data = options.getPointData();
			if (data != null) {
				this.loadPointData(data);
			}
		}
		moveTimer = thisTime;
	}

}
