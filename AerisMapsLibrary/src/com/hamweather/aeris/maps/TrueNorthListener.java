package com.hamweather.aeris.maps;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.SensorManager;
import android.location.Location;


/**
 * Compass listener for determining the direction to true north. 
 * @author Ben Collins
 *
 */
public class TrueNorthListener extends AbstractCompassListener{

	public TrueNorthListener(Compass compass, Context context) {
		super(compass,  context);
	}

	@Override
	protected double calcAzimuth(Location myLocation) {
		//need it to adjust to true north from magnetic north
		GeomagneticField geoField = new GeomagneticField( Double
            .valueOf( myLocation.getLatitude() ).floatValue(), Double
            .valueOf( myLocation.getLongitude() ).floatValue(),
            Double.valueOf( myLocation.getAltitude() ).floatValue(),
            System.currentTimeMillis() );

		  SensorManager.getOrientation(matrixR, matrixValues);				
		  double azimuth = Math.toDegrees(matrixValues[0]) + geoField.getDeclination();;
	  				  
		  if(azimuth < 0) azimuth += 360; 
		  azimuth *= -1;
		  
		  return azimuth;
	}

}
