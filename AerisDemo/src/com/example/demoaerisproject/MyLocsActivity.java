package com.example.demoaerisproject;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.db.MyPlace;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.AerisDialog.AerisDialogOKListener;

public class MyLocsActivity extends Activity implements OnLongClickListener,
		OnCheckedChangeListener, AerisDialogOKListener {

	private RadioGroup locationGroup;
	private List<MyPlace> places;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		this.setContentView(R.layout.activity_my_location);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		locationGroup = (RadioGroup) this.findViewById(R.id.rgLocations);
		locationGroup.setOnCheckedChangeListener(this);
		addRadioButtons();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addRadioButtons() {
		locationGroup.removeAllViews();
		MyPlacesDb db = new MyPlacesDb(this);
		places = db.getPlaces();
		for (int i = 0; i < places.size(); i++) {
			MyPlace place = places.get(i);
			RadioButton button = (RadioButton) getLayoutInflater().inflate(
					R.layout.aeris_radio_button, null);
			final float scale = getResources().getDisplayMetrics().density;
			int pixels = (int) (getResources().getInteger(
					R.integer.min_click_height)
					* scale + 0.5f);
			button.setText(place.getTextDisplay("Null"));
			button.setTag(i);
			locationGroup.addView(button, LayoutParams.MATCH_PARENT, pixels);
			button.setOnLongClickListener(this);
		}

		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).myLoc) {
				setCheckedAtIndex(i, locationGroup);
				break;
			}
		}

	}

	private void setCheckedAtIndex(int index, RadioGroup group) {
		RadioButton radioButton = (RadioButton) group.getChildAt(index);
		radioButton.setChecked(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int radioButtonID = group.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) group
				.findViewById(radioButtonID);
		int index = group.indexOfChild(radioButton);
		MyPlacesDb db = new MyPlacesDb(this);
		MyPlace place = places.get(index);
		db.insertPlaces(place.name, place.state, place.country, place.latitude,
				place.longitude, true);
		db.close();
	}

	@Override
	public boolean onLongClick(View v) {
		MyPlace place = places.get((Integer) v.getTag());
		AerisDialog dialog = new AerisDialog()
				.withMessage("Delete this location?").withListener(this)
				.withType(1).withObject(place);
		dialog.show(getFragmentManager(), "delete_location");
		return false;
	}

	@Override
	public void onOKPressed(int type, Object object) {
		if (type == 1) {
			MyPlace place = (MyPlace) object;
			MyPlacesDb db = new MyPlacesDb(this);
			db.deletePlace(place);
			db.close();
			addRadioButtons();
		}
	}
}
