package com.hamweather.aeris.tiles;

import java.util.Locale;

/**
 * Holds references to server constants variables
 * 
 * @author Ben Collins
 * 
 */
public class AerisConstants {
	protected static final String BASETILE_URL = "http://tile.aerisapi.com/";
	protected static final String BASETILE_URL_W_SERVER = "http://tile%d.aerisapi.com/";
	protected static final String TILE_FORMAT = "%s_%s/%s/%d/%d/%d/%d.png";
	protected static final String FULLTILE_FORMAT = "%s_%s/%s/%d/%d/%d/%d/%d/%d.png";
	protected static final String OLD_TILE_FORMAT = "tiles/archive/%s/%s/z%d/x%d/y%d/%s.png";
	protected static final String TIME_FORMAT = "%s_%s/%s.json";
	protected static final String DEV_TILE_SERVER = "http://tiledev.aerisapi.com/";
	protected static final String ANIMATION_TILE_FORMAT = "%s_%s/%s/%dx%d/%f/%f/%f/%f/%d.png?";

	protected static String getBaseWithServer(int i) {
		return String.format(Locale.ENGLISH, BASETILE_URL_W_SERVER, i);
	}

}
