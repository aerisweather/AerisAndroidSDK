package com.hamweather.aeris.tiles;

import java.util.Locale;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.hamweather.aeris.communication.UrlBuilder;

public class BuildAnimationOverlayUrl extends UrlBuilder{

	private AerisTile tile; 
	private long time; 
	private LatLng northeast;
	private LatLng southwest; 
	private int width; 
	private int height; 
	
	public BuildAnimationOverlayUrl(Context context, AerisTile tile, long time, VisibleRegion region, int width, int height) {
		this.tile = tile; 
		this.time = time;
		this.width = width;
		this.height = height; 
		this.northeast = region.latLngBounds.northeast; 
		this.southwest = region.latLngBounds.southwest; 
	}

	@Override
	public String build() {
		StringBuilder builder = new StringBuilder(AerisConstants.BASETILE_URL);
		builder.append(String.format(Locale.ENGLISH,AerisConstants.ANIMATION_TILE_FORMAT,
				clientId, clientSecret,tile.getCode(),width, height,
				southwest.latitude, southwest.longitude, northeast.latitude, 
				northeast.longitude, time));
		return builder.toString();
	}

}
