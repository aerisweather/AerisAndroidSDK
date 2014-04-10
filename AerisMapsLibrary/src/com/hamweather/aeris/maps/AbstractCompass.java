package com.hamweather.aeris.maps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Compass abstract class
 * 
 * @author Lee Huffman (based off Ben Collins)
 * 
 */
abstract class AbstractCompass extends ImageView implements Compass {
	protected Sensor accelerSensor;
	protected Sensor magneticSensor;
	protected SensorEventListener compassListener;
	protected SensorManager sensorManager;
	protected float lastPosition = 0; 

	public AbstractCompass(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.weathernation.maps.Compass#updateData(float)
	 */
	@Override
	public void updateData(final float position) {
		final RotateAnimation rotateAnim = new RotateAnimation(lastPosition, position,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//		Logger.e("TEST", "Position: " + position);
		lastPosition = position; 
		rotateAnim.setDuration(0);
		rotateAnim.setRepeatCount(0);
		rotateAnim.setFillAfter(true);
		startAnimation(rotateAnim);

	}
	

	/**
	 * Setups up the compass and needed listeners for the magnetic location
	 * 
	 * @param view
	 *            Compass view to use
	 */
	public void initCompass(OnClickListener listener) {
		if (listener != null) {
			this.setOnClickListener(listener);
		}
		sensorManager = (SensorManager) getContext().getSystemService(
				Context.SENSOR_SERVICE);
		magneticSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		accelerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	/**
	 * Registers the proper listeners for the the compass
	 */
	public void registerSensorListeners() {
		sensorManager.registerListener(compassListener, magneticSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(compassListener, accelerSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	/**
	 * Unregisters the magnetic listeners
	 */
	public void unregisterListener() {
		sensorManager.unregisterListener(compassListener);
	}

}
