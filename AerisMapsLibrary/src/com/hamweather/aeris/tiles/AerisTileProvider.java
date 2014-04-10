package com.hamweather.aeris.tiles;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Locale;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.hamweather.aeris.logging.Logger;
import com.hamweather.aeris.util.NetworkUtils;

/**
 * Handles adding tiles layers using the Aeris API to grab tiles with the
 * associated mapview.
 * 
 * @author Ben Collins
 * 
 * 
 *         TODO: Add Opacity
 */
public class AerisTileProvider implements TileProvider {
	private static final String TAG = AerisTileProvider.class.getSimpleName();

	/**
	 * the tile code
	 */
	private AerisTile tileCode;

	/**
	 * the tile time codes
	 */
	private TimeCodeResponse timeResponse = null;

	private int opacity;
	private String extraFilter;

	/**
	 * Custom tile provider for the weather tiles.
	 * 
	 * @param tileCode
	 *            - tile code type to use with the mapview.
	 */
	public AerisTileProvider(AerisTile tileCode,  int opacity) {
		this.tileCode = tileCode;
		this.opacity = opacity;
	}

	/**
	 * Custom tile provider for the weather tiles.
	 * 
	 * @param tileCode
	 *            - tile code type to use with the mapview.
	 * @param context
	 *            - the context to pull variables from
	 * @param opacity
	 *            - opacity the tile should be shown at
	 * @param extraFilter
	 *            - extra filter to use when getting the tiles. Needed for
	 *            trailtype tiles
	 */
	public AerisTileProvider(AerisTile tileCode, int opacity,
			String extraFilter) {
		this.tileCode = tileCode;
		this.opacity = opacity;
		this.extraFilter = extraFilter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.gms.maps.model.TileProvider#getTile(int, int,
	 * int)
	 */
	@Override
	public Tile getTile(int x, int y, int z) {
		JSONObject json;
		Tile tile = null;
		Logger.d(TAG,
				String.format(Locale.ENGLISH, "x:%d, y:%d, z:%d", x, y, z));

		try {
			// if we havent fetched the time file lets do that first
			if (timeResponse == null) {
				json = NetworkUtils.getJSON(new URL(new BuildTileTimeURL(
						tileCode).build()));
				timeResponse = TimeCodeResponse.convertFromString(json
						.toString());
			}
			BuildTileURL tileURL = new BuildTileURL(tileCode,
					timeResponse.files.get(0).time, x, y, z);
			if (extraFilter != null) {
				tileURL.withExtraFilter(extraFilter);
			}
			Bitmap mapBitmap;
			mapBitmap = NetworkUtils.loadBitmap(tileURL.build(), opacity);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			mapBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] bytes = stream.toByteArray();
			Logger.d(TAG, "Width: " + mapBitmap.getWidth() + " Height: "
					+ mapBitmap.getHeight());
			tile = new Tile(mapBitmap.getWidth(), mapBitmap.getHeight(), bytes);
		} catch (Exception e) {
			Logger.e(TAG, e.getMessage(),e);
		}
		return tile;
	}

}
