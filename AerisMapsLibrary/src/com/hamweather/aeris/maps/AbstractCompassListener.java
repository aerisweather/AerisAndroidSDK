package com.hamweather.aeris.maps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;

import com.hamweather.aeris.location.LocationHelper;


/**
 * Compass listener for determining the direction to true north. 
 * @author Ben Collins
 *
 */
abstract class AbstractCompassListener implements SensorEventListener{
	protected static final int TIMING_RESTRICTION = 10;
	protected Compass compass; 
	protected float[] valuesAccelerometer;
	protected float[] valuesMagneticField;
	protected float[] matrixR = new float[9];
	protected float[] matrixI= new float[9];
	protected float[] matrixValues= new float[3];
	protected long lastUpdate ; 
	
	protected int currentAccuracy; 
	protected Context context; 
	
	protected boolean performSmoothing = true;
	protected double SmoothFactorCompass = 0.4;
	protected double SmoothThresholdCompass = 40.0;
	protected double oldAzimuth = 0.0;	
	
	
	/**
	 * Constructor for the True north compass. 
	 * @param compass		Compass to pass the updated value to. 
	 * @param map			Map to use for obtaining current location
	 */
	public AbstractCompassListener(final Compass compass,  Context context){
		this.compass = compass;
		this.context = context; 
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int arg1) {
		this.currentAccuracy = arg1; 
	}

	/*
	 * (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()){
		  case Sensor.TYPE_ACCELEROMETER:
			  valuesAccelerometer = event.values.clone();
			  break;
		  case Sensor.TYPE_MAGNETIC_FIELD:
			  valuesMagneticField = event.values.clone();
			  break;
		  }
		 if(System.currentTimeMillis() - lastUpdate < TIMING_RESTRICTION){
			 return;
		 }
		Location myLocation = new LocationHelper(context).getCurrentLocation(); 
		if(valuesAccelerometer != null && valuesMagneticField != null
				&& myLocation != null){

			boolean success = SensorManager.getRotationMatrix(
				       matrixR,
				       matrixI,
				       valuesAccelerometer,
				       valuesMagneticField);
			
			if (success) {
				double azimuth = calcAzimuth(myLocation);
  
				lastUpdate = System.currentTimeMillis(); 
				
				if (performSmoothing) {
					Double tAzimuth = oldAzimuth;
					oldAzimuth = smoothCompassReading(azimuth, oldAzimuth);
					if (oldAzimuth != tAzimuth) compass.updateData((float) oldAzimuth);
				}
				else compass.updateData((float) azimuth);
				  
				
			}	
		}
	}

	
	/**
	 * Calculate the Azimuth. 
	 * @param myLocation	Location object
	 */
	protected double calcAzimuth(Location myLocation) {
		return 0.0;		
	}
	
	/**
	 * Handle smoothing of the compass angle to lower jitter
	 * @param azimuth	new compass bearing
	 * @param oldAzimuth	previous compass bearing
	 */	
	protected double smoothCompassReading(double azimuth, double oldAzimuth) {
		if (Math.abs(azimuth - oldAzimuth) < 180) {
			if (Math.abs(azimuth - oldAzimuth) > SmoothThresholdCompass) {
				oldAzimuth = azimuth;
			}
			else {
				oldAzimuth = oldAzimuth + SmoothFactorCompass * (azimuth - oldAzimuth);
			}					  
		}
		else {
			if (360.0 - Math.abs(azimuth - oldAzimuth) > SmoothThresholdCompass) {
				oldAzimuth = azimuth;
			}
			else {
				if (oldAzimuth > azimuth) {
					oldAzimuth = (oldAzimuth + SmoothFactorCompass * ((360 + azimuth - oldAzimuth) % 360) + 360) % 360;
				} 
				else {
					oldAzimuth = (oldAzimuth - SmoothFactorCompass * ((360 - azimuth + oldAzimuth) % 360) + 360) % 360;
				}
			}
		}
		
		return oldAzimuth;
		  		
	}
	

}
