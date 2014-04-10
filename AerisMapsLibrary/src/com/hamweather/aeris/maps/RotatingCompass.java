package com.hamweather.aeris.maps;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Compass listener for determining the direction to true north. 
 * @author Ben Collins
 *
 */
public class RotatingCompass extends AbstractCompass{
	
	public RotatingCompass(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * Setups up the compass and needed listeners for the magnetic location
	 * @param view		Compass view to use 
	 */
	public void initCompass(OnClickListener listener){
		super.initCompass(listener);
		compassListener = new TrueNorthListener(this, getContext()); 
	}


}
