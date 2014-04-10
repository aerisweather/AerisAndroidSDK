package com.hamweather.aeris.tiles;

import java.util.ArrayList;
import java.util.List;

import com.hamweather.aeris.maps.R;

/**
 * Constants for different types of tile map overlays
 * 
 * @author Ben Collins
 * 
 */
public enum AerisTile {

	NONE("None", "", 0), RADAR("Radar", "radar", R.drawable.radar), RADSAT(
			"Radar/Satellite", "radsat", R.drawable.radar), RADALERTS(
			"Radar/Advisory", "radalerts", R.drawable.radar), INFRARED_SAT(
			"Infrared Satellite", "sat", 0), VISIBLE_SAT("Visible Satellite",
			"sat_vistrans", 0), GLOBAL_INFRARED("Global Infrared Satellite",
			"globalsat", 0), ADVISORIES("Advisories", "alerts",
			R.drawable.advisories), CURRENT_TEMP("Current Temperatures",
			"current_temps", R.drawable.current_temps), CURRENT_WINDS(
			"Current Winds", "current_winds", R.drawable.current_winds), CURRENT_DEW(
			"Current Dew Points", "current_dp", R.drawable.current_dewpoint), CURRENT_HUMIDITY(
			"Current Humidity", "current_rh", R.drawable.current_humidity), CURRENT_WINDCHILL(
			"Current Wind Chill", "current_windchill",
			R.drawable.current_windchill), CURRENT_HEATINDEX(
			"Current Heat Index", "current_heat_index",
			R.drawable.current_heatindex), SNOW_DEPTH("Snow Depth",
			"snowdepth_snodas", R.drawable.snowdepth), CURRENT_SEA_SURFACE_TEMPS(
			"Current Sea Surface Temps", "modis_sst", R.drawable.current_sst), CURENT_CHLOROPHYL(
			"Current Chlorophyll", "modis_chlo", R.drawable.current_chlorophyll);

	private String name;
	private String code;
	private int legend;

	/**
	 * Private inner contructor for the Tile Code types
	 * 
	 * @param name
	 *            String name of the tile
	 * @param code
	 *            String code of the tile
	 */
	private AerisTile(String name, String code, int legend) {
		this.name = name;
		this.code = code;
		this.legend = legend;
	}

	public int getLegend() {
		return legend;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Appends the day code to the TileCode.
	 * 
	 * @param day
	 * @return
	 */
	public String getWithAppendedDay(Day day) {
		StringBuilder builder = new StringBuilder(code);
		builder.append("_");
		builder.append(day.getCode());
		return builder.toString();
	}

	public static AerisTile getTileFromName(String name) {
		for (AerisTile tile : values()) {
			if (tile.getName().equals(name)) {
				return tile;
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
		AerisTile[] codes = AerisTile.values();
		List<String> stringCode = new ArrayList<String>();
		for (int i = 0; i < codes.length; i++) {
			stringCode.add(codes[i].name);

		}
		return stringCode;
	}

}
