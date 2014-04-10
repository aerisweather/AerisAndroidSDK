package com.hamweather.aeris.maps.markers;

import com.hamweather.aeris.maps.R;

/**
 * Enum that breaks down the marker types and associated them with the drawable.
 * 
 * @author bcollins
 * 
 */
public enum AerisMarkerType {
	/*
	 * Earhquake enums
	 */
	QUAKE_MINI(R.drawable.quake_mini, "mini"), QUAKE_MINOR(
			R.drawable.quake_minor, "minor"), QUAKE_LIGHT(
			R.drawable.quake_light, "light"), QUAKE_MODERATE(
			R.drawable.quake_moderate, "moderate"), QUAKE_STRONG(
			R.drawable.quake_strong, "strong"), QUAKE_MAJOR(
			R.drawable.quake_major, "major"), QUAKE_GREAT(
			R.drawable.quake_great, "great"),
	/*
	 * Lightning Enums
	 */
	LIGHTNING_0_TO_15(R.drawable.lightning_white, ""), LIGHTNING_15_TO_30(
			R.drawable.lightning_yellow, ""), LIGHTNING_30_TO_45(
			R.drawable.lightning_red, ""), LIGHTNING_45_TO_60(
			R.drawable.lightning_orange, ""), LIGHTNING_60_PLUS(
			R.drawable.lightning_blue, ""),
	/*
	 * Records enums
	 */
	RECORDS_HIGHTEMP(R.drawable.record_maxtemp, ""), RECORDS_LOWTEMP(
			R.drawable.record_mintemp, ""), RECORDS_HIGHMIN(
			R.drawable.record_himin, ""), RECORDS_LOWMAX(
			R.drawable.record_lomax, ""), RECORDS_RAIN(R.drawable.record_rain,
			""), RECORDS_SNOW(R.drawable.record_snow, ""),

	/*
	 * Start to the Reports Enums
	 */
	REPORT_HIGHWIND(R.drawable.report_wind, "O|D|N|G|A"), REPORT_RAIN_FLOOD(
			R.drawable.report_rain, "E|F|R"), REPORT_ICE(R.drawable.report_ice,
			"s|5"), REPORT_TORNADO(R.drawable.report_tornado, "T|C|W"), REPORT_LIGHTNING(
			R.drawable.report_tstorm, "L"), REPORT_AVALANCHE(
			R.drawable.report_avalanche, "V"), REPORT_HAIL(
			R.drawable.report_hail, "H"), REPORT_SNOW(R.drawable.report_snow,
			"B|S"), REPORT_FOG(R.drawable.report_fog, "E"), REPORT_SURF(
			R.drawable.report_wave, "4|v"),
	// REPORT_DUST(R.drawable.report_dust,
	// ""),
	/*
	 * Start to the cells enums
	 */
	CELL_GENERAL(R.drawable.point_green, ""), CELL_HAIL(
			R.drawable.point_yellow, ""), CELL_ROTATING(
			R.drawable.point_orange, ""), CELL_TORNADO(R.drawable.point_red, ""),
	/*
	 * Fire enum
	 */
	FIRE(R.drawable.fire, "")

	;
	private int icon;
	private String type;

	private AerisMarkerType(int icon, String type) {
		this.icon = icon;
		this.type = type;
	}

	/**
	 * Gets the cell marker type from the storm cell values.
	 * 
	 * @param tvs
	 * @param mda
	 * @param hailProb
	 * @return AerisMarkerType from the cell information
	 */
	public static AerisMarkerType cellFromValues(int tvs, int mda, int hailProb) {
		if (tvs == 1 || mda >= 10) {
			return CELL_TORNADO;
		} else if (mda > 2) {
			return CELL_ROTATING;
		} else if (hailProb >= 70) {
			return CELL_HAIL;
		} else {
			return CELL_GENERAL;
		}
	}

	public static AerisMarkerType lightningFromTime(long time) {
		long now = System.currentTimeMillis();
		long difference = now - time;
		long minute = 1000 * 60;
		if (difference <= minute * 15) {
			return LIGHTNING_0_TO_15;
		} else if (difference <= minute * 30) {
			return LIGHTNING_15_TO_30;
		} else if (difference <= minute * 45) {
			return LIGHTNING_30_TO_45;
		} else if (difference <= minute * 60) {
			return LIGHTNING_45_TO_60;
		} else {
			return LIGHTNING_60_PLUS;
		}

	}

	public static AerisMarkerType reportFromCode(String code) {
		AerisMarkerType[] types = { REPORT_AVALANCHE, REPORT_FOG, REPORT_HAIL,
				REPORT_HIGHWIND, REPORT_ICE, REPORT_LIGHTNING,
				REPORT_RAIN_FLOOD, REPORT_SNOW, REPORT_SURF, REPORT_TORNADO };
		return getTypeFromArray(types, code);
	}

	public static AerisMarkerType quakeFromCode(String code) {
		AerisMarkerType[] types = { QUAKE_GREAT, QUAKE_LIGHT, QUAKE_MAJOR,
				QUAKE_MINI, QUAKE_MINOR, QUAKE_MODERATE, QUAKE_STRONG,
				QUAKE_GREAT };
		return getTypeFromArrayNoSplit(types, code);
	}

	private static AerisMarkerType getTypeFromArrayNoSplit(
			AerisMarkerType[] types, String code) {
		for (AerisMarkerType type : types) {

			if (type.getType().equals(code)) {
				return type;
			}

		}
		return null;
	}



	private static AerisMarkerType getTypeFromArray(AerisMarkerType[] types,
			String code) {
		for (AerisMarkerType type : types) {
			String temp[] = type.getType().split("\\|", -1);
			if (temp != null) {
				for (String s : temp) {
					if (s.equals(code)) {
						return type;
					}
				}
			}
		}
		return null;
	}

	public String getType() {
		return this.type;
	}

	public int getIcon() {
		return icon;
	}
}
