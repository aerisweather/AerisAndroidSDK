package com.hamweather.aeris.maps;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.android.gms.maps.GoogleMap;
import com.hamweather.aeris.tiles.AerisPointData;
import com.hamweather.aeris.tiles.AerisTile;

public class AerisMapOptions {
	private static final String OPACITY_KEY = "opacity_key";

	private static final String MAP_TYPE = "map_type";

	private static final String TILE_KEY = "tile_key";

	private static final String ANIMTION_KEY = "animtion_key";
	private static final String POINT_DATA_KEY = "point_data_key";

	private static final String PREF_NAME = "map_preferences";

	private AerisTile tile;
	private int opacity;
	private float animationSpeed;
	private int googleMapType;
	private AerisPointData pointData;

	public AerisMapOptions() {
	}

	public AerisMapOptions withAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
		return this;
	}

	public AerisMapOptions withTile(AerisTile tile) {
		this.tile = tile;
		return this;
	}

	public AerisMapOptions withOpacity(int opacity) {
		this.opacity = opacity;
		return this;
	}

	public AerisMapOptions withMapType(int mapType) {
		this.googleMapType = mapType;
		return this;
	}

	public AerisMapOptions withPointData(AerisPointData data) {
		this.pointData = data;
		return this;
	}

	public AerisPointData getPointData() {
		return pointData;
	}

	public AerisTile getTile() {
		return tile;
	}

	public float getAnimationSpeed() {
		return animationSpeed;
	}

	public int getOpacity() {
		return opacity;
	}

	public int getMapType() {
		return googleMapType;
	}

	public void setPreference(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putFloat(ANIMTION_KEY, animationSpeed);
		editor.putInt(TILE_KEY, tile.ordinal());
		editor.putInt(MAP_TYPE, googleMapType);
		if (pointData != null) {
			editor.putInt(POINT_DATA_KEY, pointData.ordinal());
		}
		editor.putInt(OPACITY_KEY, opacity);
		editor.commit();
	}

	public static AerisMapOptions getPreference(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		AerisMapOptions options = new AerisMapOptions();
		options.withOpacity(preferences.getInt(OPACITY_KEY, 205));
		int tileInt = preferences.getInt(TILE_KEY, -1);
		if (tileInt > -1) {
			options.withTile(AerisTile.values()[tileInt]);
		} else {
			// default to radar
			options.withTile(AerisTile.RADAR);
		}

		int pointDataCardinal = preferences.getInt(POINT_DATA_KEY, -1);
		if (pointDataCardinal > -1) {
			options.withPointData(AerisPointData.values()[pointDataCardinal]);
		} else {
			options.withPointData(AerisPointData.NONE);
		}

		options.withMapType(preferences.getInt(MAP_TYPE,
				GoogleMap.MAP_TYPE_NORMAL));
		options.withAnimationSpeed(preferences.getFloat(ANIMTION_KEY, 100f));
		return options;
	}
}
