package com.hamweather.aeris.tiles;


/**
 * Day constant for the day codes. 
 * @author Ben Collins
 *
 */
public enum Day {
	ONE_DAY("1day"),
	THREE_DAY("3day"),
	SEVEN_DAY_("7day"),
	FOURTEEN_DAY("14day");
	
	private String code; 
	
	private Day(String code){
		this.code = code; 

	}
	
	public String getCode(){
		return code; 
	}
}
