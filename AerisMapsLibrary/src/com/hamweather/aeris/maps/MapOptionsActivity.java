package com.hamweather.aeris.maps;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.hamweather.aeris.maps.AerisMapOptions;
import com.hamweather.aeris.tiles.AerisPointData;
import com.hamweather.aeris.tiles.AerisTile;

public class MapOptionsActivity extends Activity implements
		OnCheckedChangeListener {

	private static final int MAX_OPACITY = 255;
	RadioGroup overlayGroup;
	RadioGroup mapOptionsGroup;
	RadioGroup pointDataGroup;
	SeekBar opacitySeekBar;
	SeekBar animationSeekBar;
	private AerisMapOptions options;
	private List<String> overlays;
	private List<String> pointDatas;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_options);
		overlayGroup = (RadioGroup) findViewById(R.id.mapoptions_rg_overlays);
		pointDataGroup = (RadioGroup) findViewById(R.id.mapoptions_rg_point_data);
		mapOptionsGroup = (RadioGroup) findViewById(R.id.mapoptions_rg_maptype);
		mapOptionsGroup.setOnCheckedChangeListener(this);
		overlayGroup.setOnCheckedChangeListener(this);
		pointDataGroup.setOnCheckedChangeListener(this);
		opacitySeekBar = (SeekBar) findViewById(R.id.mapoptions_sb_opacity);
		animationSeekBar = (SeekBar) findViewById(R.id.mapoptions_sb_animation);
		options = AerisMapOptions.getPreference(this);
		addRadioButtons();
		initViews();
	}

	private void addRadioButtons() {
		overlays = AerisTile.getStringList();
		for (String overlay : overlays) {
			RadioButton button = (RadioButton) getLayoutInflater().inflate(
					R.layout.aeris_radio_button, null);
			final float scale = getResources().getDisplayMetrics().density;
			int pixels = (int) (getResources().getInteger(
					R.integer.min_click_height)
					* scale + 0.5f);
			button.setText(overlay);
			overlayGroup.addView(button, LayoutParams.MATCH_PARENT, pixels);

		}
		pointDatas = AerisPointData.getStringList();
		for (String pointData : pointDatas) {
			RadioButton button = (RadioButton) getLayoutInflater().inflate(
					R.layout.aeris_radio_button, null);
			final float scale = getResources().getDisplayMetrics().density;
			int pixels = (int) (getResources().getInteger(
					R.integer.min_click_height)
					* scale + 0.5f);
			button.setText(pointData);
			pointDataGroup.addView(button, LayoutParams.MATCH_PARENT, pixels);

		}
	}

	private void initViews() {
		animationSeekBar.setMax(100);
		opacitySeekBar.setMax(MAX_OPACITY);
		animationSeekBar.setProgress((int) options.getAnimationSpeed());
		opacitySeekBar.setProgress(options.getOpacity());
		for (int i = 0; i < overlays.size(); i++) {
			if (overlays.get(i).equals(options.getTile().getName())) {
				setCheckedAtIndex(i, overlayGroup);
				break;
			}
		}

		for (int i = 0; i < pointDatas.size(); i++) {
			if (pointDatas.get(i).equals(options.getPointData().name)) {
				setCheckedAtIndex(i, pointDataGroup);
				break;
			}
		}

		if (options.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
			setCheckedAtIndex(0, mapOptionsGroup);
		} else if (options.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
			setCheckedAtIndex(1, mapOptionsGroup);
		} else if (options.getMapType() == GoogleMap.MAP_TYPE_HYBRID) {
			setCheckedAtIndex(2, mapOptionsGroup);
		}

	}

	private void setCheckedAtIndex(int index, RadioGroup group) {
		RadioButton radioButton = (RadioButton) group.getChildAt(index);
		radioButton.setChecked(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			this.finish();
			return true;
		} else if (itemId == R.id.menu_save) {
			options.withOpacity(opacitySeekBar.getProgress());
			options.withAnimationSpeed(animationSeekBar.getProgress());
			options.setPreference(this);
			setResult(RESULT_OK);
			this.finish();
			return false;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int radioButtonID = group.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) group
				.findViewById(radioButtonID);
		int index = group.indexOfChild(radioButton);
		if (group.getId() == R.id.mapoptions_rg_overlays) {
			options.withTile(AerisTile.getTileFromName(overlays.get(index)));
		} else if (group.getId() == R.id.mapoptions_rg_maptype) {
			String buttonText = radioButton.getText().toString();
			if (buttonText.equals(getString(R.string.map_options_standard))) {
				options.withMapType(GoogleMap.MAP_TYPE_NORMAL);
			} else if (buttonText
					.equals(getString(R.string.map_options_satellite))) {
				options.withMapType(GoogleMap.MAP_TYPE_SATELLITE);
			} else if (buttonText
					.equals(getString(R.string.map_options_hybrid))) {
				options.withMapType(GoogleMap.MAP_TYPE_HYBRID);
			}
		} else if (group.getId() == R.id.mapoptions_rg_point_data) {
			options.withPointData(AerisPointData
					.getPointDataFromName(pointDatas.get(index)));
		}

	}

}