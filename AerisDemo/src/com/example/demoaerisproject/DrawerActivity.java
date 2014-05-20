package com.example.demoaerisproject;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customendpoint.CustomSunmoonFragment;
import com.example.db.MyLocLoader;
import com.example.db.MyPlacesDb.PlacesColumns;
import com.example.fragment.ExtForecastFragment;
import com.example.fragment.HeadlessFragment;
import com.example.fragment.MapFragment;
import com.example.fragment.NearbyObsFragment;
import com.example.fragment.ObservationFragment;
import com.example.fragment.OverviewFragment;
import com.example.fragment.RecentObsFragment;
import com.example.fragment.RefreshInterface;
import com.example.fragment.WeekendFragment;
import com.example.menudrawer.NavDrawerItem;
import com.example.menudrawer.NavDrawerListAdapter;
import com.hamweather.aeris.logging.Logger;
import com.hamweather.aeris.model.Place;

public class DrawerActivity extends Activity implements OnItemClickListener,
		OnClickListener, LoaderCallbacks<Cursor> {
	private static final int MY_LOC_LOADER = 0;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Fragment currentFragment;
	private LinearLayout mDrawer;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TextView myLocTextView;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.menu_drawer);
		HeadlessFragment.getFragment(this);

		this.setProgressBarIndeterminateVisibility(false);
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawer = (LinearLayout) findViewById(R.id.llDrawer);
		myLocTextView = (TextView) findViewById(R.id.tvDrawerLocation);
		findViewById(R.id.tvDrawerSearch).setOnClickListener(this);
		findViewById(R.id.tvDrawerMyLocs).setOnClickListener(this);
		navDrawerItems = new ArrayList<NavDrawerItem>();

		for (int i = 0; i < navMenuTitles.length; i++) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], -1));
		}

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(HeadlessFragment.getFragment(this).getCurrentFragment());
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getLoaderManager().initLoader(MY_LOC_LOADER, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			this.startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.action_refresh:
			if (currentFragment != null) {
				try {
					RefreshInterface refresh = (RefreshInterface) currentFragment;
					refresh.refreshPressed();
				} catch (ClassCastException ex) {
					Logger.e("Refresh", ex.getMessage(), ex);
				}
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	public void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {

		case 0:
			fragment = new ObservationFragment();
			break;
		case 1:
			fragment = new ExtForecastFragment();
			break;
		case 2:
			fragment = new RecentObsFragment();
			break;
		case 3:
			fragment = new NearbyObsFragment();
			break;
		case 4:
			fragment = new OverviewFragment();
			break;
		case 5:
			fragment = new WeekendFragment();
			break;
		case 6:
			fragment = new MapFragment();
			break;
		case 7:
			fragment = new CustomSunmoonFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			currentFragment = fragment;
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			HeadlessFragment.getFragment(this).setCurrentFragment(position);
			mDrawerLayout.closeDrawer(mDrawer);
			setTitle(navMenuTitles[position]);

		} else {
			Toast.makeText(this, "This feature has not been implented yet.",
					Toast.LENGTH_SHORT).show();
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		displayView(arg2);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvDrawerSearch) {
			startActivity(new Intent(this, LocationSearchActivity.class));
		} else if (v.getId() == R.id.tvDrawerMyLocs) {
			startActivity(new Intent(this, MyLocsActivity.class));
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		switch (id) {
		case MY_LOC_LOADER:
			return new MyLocLoader(this);
		default:

			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null && cursor.moveToFirst()) {
			Place place = new Place();
			place.name = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.NAME));
			place.state = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.STATE));
			place.country = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.COUNTRY));
			String text = "My Location Not Set";
			if (place.state != null && place.state.length() > 0) {
				text = String.format("%s, %s, %s",
						WordUtils.capitalize(place.name, ' '),
						place.state.toUpperCase(), place.country.toUpperCase());
			} else {
				text = String.format("%s, %s",
						WordUtils.capitalize(place.name, ' ', '-'),
						place.country.toUpperCase());
			}
			myLocTextView.setText(text);
		}
		getLoaderManager().destroyLoader(loader.getId());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
