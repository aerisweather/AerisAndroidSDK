package com.hamweather.aeris.tiles;

import java.util.Locale;

import com.hamweather.aeris.communication.UrlBuilder;
import com.hamweather.aeris.logging.Logger;


/**
 * Builds a url to grab a tile from a specific time, code and section of a
 * weather map. 
 * @author Ben Collins
 *
 */
public class BuildTileURL extends UrlBuilder{
	private AerisTile tileCode; 
	private long time; 
	private int x; 
	private int y; 
	private int zoom; 
	private int y2 = Integer.MIN_VALUE; 
	private int x2 = Integer.MIN_VALUE; 
	private String extraFilter; 
	
	/**
	 * Constructor for the Build tile.
	 * @param context	Context to use
	 * @param code		Aeris type to get a tile for
	 * @param time		the time to get the tile at
	 * @param x		tile column of the map
	 * @param y		tile row for the map
	 * @param zoom	zoom level for the tile (1-27)
	 */
	public BuildTileURL(AerisTile code, long time,  int x, int y, int zoom){
		setComponents(code, time, x, y, zoom);
	}
	
	/**
	 * Constructor for the Build tile w/ second x,y to do full tile at once for faster loading
	 * @param context	Context to use
	 * @param code		Aeris type to get a tile for
	 * @param time		the time to get the tile at
	 * @param x			tile column of the map for the upper left
	 * @param y			tile row for the map for the upper left
	 * @param x2		tile column of the map for the bottom right
	 * @param y2		tile row for the map for the bottom right
	 * @param zoom	zoom level for the tile (1-27)
	 */
	public BuildTileURL(AerisTile code, long time,  int x, int y,int x2, int y2, int zoom){
		setComponents(code, time, x, y, zoom);
		this.x2 = x2; 
		this.y2 = y2; 
	}
	
	
	public BuildTileURL withExtraFilter(String extraFilter){
		this.extraFilter = extraFilter; 
		return this; 
	}
	
	/**
	 * Convience method for setting up private variables
	 * @param code
	 * @param time
	 * @param x
	 * @param y
	 * @param zoom
	 */
	private void setComponents(AerisTile code, long time,  int x, int y,int zoom){
		this.tileCode = code; 
		this.time = time;
		this.x = x; 
		this.y = y; 
		this.zoom = zoom;
	}
	
	/**
	 * Builds the Url for the specific section of the tile.

	 * @return		String Url for retrieving the tile
	 */
	public String build(){
		int server = Math.abs(x * y) % 4 + 1;
		StringBuilder builder = new StringBuilder(AerisConstants.getBaseWithServer(server));
		String code = tileCode.getCode(); 
		if(extraFilter != null){
			code += "/" + extraFilter;
		}
		if(x2 == Integer.MIN_VALUE && y2 == Integer.MIN_VALUE){
			builder.append(String.format(Locale.ENGLISH,
					AerisConstants.TILE_FORMAT,clientId,clientSecret,code, zoom, x, y ,time));
		}else{
			builder.append(String.format(Locale.ENGLISH,
					AerisConstants.FULLTILE_FORMAT,clientId,clientSecret,code, zoom, x, y,x2,y2,time));
		}
		Logger.w("AerisSimple", builder.toString());
		return builder.toString();
	}

	public AerisTile getTileCode() {
		return tileCode;
	}
	
	
	
}
