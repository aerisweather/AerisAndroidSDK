package com.hamweather.aeris.maps;

import android.content.Context;
import android.location.Location;


/**
 * Listener for simple bearing compass. 
 * @author  Lee Huffman
 *
 */
public class BearingCompassListener extends TrueNorthListener {

	protected Location destLocation;
	
	public BearingCompassListener(Compass compass, Context context) {
		super(compass, context);
	}

	@Override
	protected double calcAzimuth(Location myLocation) {
		double azimuth = super.calcAzimuth(myLocation);
		if (destLocation != null) {
			azimuth += myLocation.bearingTo(destLocation);
		}
	  				
		return azimuth;
	}

	/**
	 * Sets the destination location we are pointing too
	 * @param loc	Destination location object
	 */		
	public void setDestLocation(Location loc) {
		destLocation = loc;
	}
	

}
