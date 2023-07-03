package com.xweather.sdkdemo.java.listview; 

import android.view.LayoutInflater;
import android.view.View;
/**
 * Interface for the view holder pattern used in the Custom list view adapters. 
 * @author bcollins
 *
 * @param <T> the object being adapted to a listview
 */
public interface AdapterHolder<T>{
	/**
	 * Inflates the view and the holders child views. 
	 * @param mInflater - inflater to inflate the view
	 * @return - view to display
	 */
	View inflateview(LayoutInflater mInflater);
	/**
	 * Fills a given view with information from the holders data object
	 * @param t - data to fill view with 
	 * @param position - the position of the view in the list
	 */
	void populateView(T t, final int position);
}