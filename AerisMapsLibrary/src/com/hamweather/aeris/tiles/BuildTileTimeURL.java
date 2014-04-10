package com.hamweather.aeris.tiles;

import java.util.Locale;

import com.hamweather.aeris.communication.UrlBuilder;


/**
 * Creates Time Url for a specific type code
 * @author Ben Collins
 *
 */
public class BuildTileTimeURL extends UrlBuilder{
	private AerisTile tileCode; 
	
	/**
	 * Constructor for the URL. 
	 * @param code		Aeris code tile to use
	 * @param context	the context to user with
	 */
	public BuildTileTimeURL(AerisTile code){
		this.tileCode = code; 
	}
	
	/**
	 * Constructs the url. 
	 * @return
	 */
	public String build(){
		StringBuilder builder = new StringBuilder(AerisConstants.BASETILE_URL);
		builder.append(String.format(Locale.ENGLISH, AerisConstants.TIME_FORMAT,
				clientId,clientSecret, tileCode.getCode()));
		return builder.toString(); 
	}
}
