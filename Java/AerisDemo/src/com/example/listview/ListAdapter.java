package com.example.listview;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Abstract class for display custom ListViews
 * 
 * @author bcollins
 * 
 * @param <T>
 *            Object that is being adapted to a ListView item
 */
public abstract class ListAdapter<T> extends IndexMonitorAdapter {

	/**
	 * List of Data items to display
	 */
	private List<T> items;
	/**
	 * Inflater to inflate each custom view
	 */
	private LayoutInflater mInflater;

	private int selectedDrawable;

	/**
	 * Constructor for the MenardsListAdapter
	 * 
	 * @param items
	 * @param activity
	 */
	public ListAdapter(List<T> items, Activity activity, int selectedDrawable) {
		this.items = items;
		mInflater = LayoutInflater.from(activity);
		this.selectedDrawable = selectedDrawable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (items == null)
			return 0;
		return items.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public T getItem(int position) {
		if (items == null)
			return null;
		return items.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Fills old view with data, or creates a new view.
	 * 
	 * @param position
	 *            - position to create view for
	 * @param convertView
	 *            - view to create/recycle
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public View getItemView(int position, View convertView) {
		T item = items.get(position);
		AdapterHolder<T> holder = null;

		if (convertView == null) {
			holder = getHolder();
			convertView = holder.inflateview(mInflater);
			convertView.setTag(holder);

		} else {
			holder = (AdapterHolder<T>) convertView.getTag();
		}
		// set the color if selected.
		if (this.selectedIndex == position) {
			convertView.setBackgroundResource(selectedDrawable);
		} else {
			// 0 removes the resource
			convertView.setBackgroundResource(0);
		}

		holder.populateView((T) item, position);
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView);
	}

	/**
	 * Gets the adapter ViewHolder for this adapter.
	 * 
	 * @return
	 */
	public abstract AdapterHolder<T> getHolder();

	/**
	 * Sets the list to another list.
	 * 
	 * @param list
	 */
	public void setList(List<T> list) {
		items.clear();
		if (list == null)
			return;
		items.addAll(list);
	}

	/**
	 * Deletes item in the list
	 * 
	 * @param position
	 */
	public void deleteItemInList(int position) {
		items.remove(position);
	}

}