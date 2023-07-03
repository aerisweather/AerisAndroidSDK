package com.xweather.sdkdemo.java.listview;

import android.widget.BaseAdapter;

/**
 * Abstract class that adds index functionality to the BaseAdapter
 * 
 * @author bcollins
 * 
 */
public abstract class IndexMonitorAdapter extends BaseAdapter {
	/**
	 * Index kept of the qtyPressedIndex
	 */
	protected int qtyPressedIndex = -1;
	/**
	 * Index of the selection
	 */
	protected int selectedIndex = -1;

	/**
	 * Sets the selected index
	 * 
	 * @param selectedIndex
	 *            - index that was selected
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * Gets the selected index
	 * 
	 * @return
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * Gets the qty selected index
	 * 
	 * @return
	 */
	public int getQtyPressedIndex() {
		return qtyPressedIndex;
	}

	/**
	 * Sets the index of the qty pressed
	 * 
	 * @param qtyPressedIndex
	 */
	public void setQtyPressedIndex(int qtyPressedIndex) {
		this.qtyPressedIndex = qtyPressedIndex;
	}
}