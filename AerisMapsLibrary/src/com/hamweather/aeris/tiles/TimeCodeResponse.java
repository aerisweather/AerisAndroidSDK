package com.hamweather.aeris.tiles;

import java.util.List;

import com.google.gson.Gson;


/**
 * JSON wrapper for the time code response
 * @author Ben Collins
 *
 */
public class TimeCodeResponse {

	public List<FileJSON> files; 
	
	/**
	 * Converts the string response to a TimeCodeResponse. 
	 * @param resp	response to convert
	 * @return		TimeCodeResponse object with converted values inserted. 
	 */
	public static TimeCodeResponse convertFromString(String resp){
		return new Gson().fromJson(resp, TimeCodeResponse.class);
	}
}
