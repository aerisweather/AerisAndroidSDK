package com.hamweather.aeris.maps;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;

/**
 * Compass listener for determining the direction to a lat/lon
 * @author Ben Collins
 *
 */
public class BearingCompass extends AbstractCompass {
	public BearingCompass(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Initializes the compass with the needed listeners
	 * @param listener	listener to use when clicking the compass
	 */
	public void initCompass(OnClickListener listener){
		super.initCompass(listener);
		compassListener = new BearingCompassListener(this, getContext()); 
	}
	
	/**
	 * Sets the destination location we are pointing too
	 * @param destLoc	array of the latitude and longitude
	 */	
	public void setDestLocation(double[] destLoc) {
		setDestLocation(destLoc[0],destLoc[1]);
	}
	
	/**
	 * Sets the destination location we are pointing too
	 * @param lat	The destination latitude
	 * @param lon	The destination longitude
	 */		
	public void setDestLocation(double lat, double lon) {
		Location loc = new Location("");
		loc.setLatitude(lat);
		loc.setLongitude(lon);
		((BearingCompassListener) compassListener).setDestLocation(loc);
	}
	
	/**
	 * Sets the destination location we are pointing too
	 * @param loc	Destination location object
	 */		
	public void setDestLocation(Location loc){
		((BearingCompassListener) compassListener).setDestLocation(loc);
	}
	

}
