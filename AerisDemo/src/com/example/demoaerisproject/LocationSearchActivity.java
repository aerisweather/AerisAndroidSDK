package com.example.demoaerisproject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.AerisDialog.AerisDialogOKListener;
import com.example.edithelp.EditTextEnterListener;
import com.example.edithelp.EnterPressedDelegate;
import com.example.listview.PlacesAdapter;
import com.hamweather.aeris.communication.AerisProgressListener;
import com.hamweather.aeris.communication.loaders.PlacesTask;
import com.hamweather.aeris.communication.loaders.PlacesTaskCallback;
import com.hamweather.aeris.communication.parameter.ParameterBuilder;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.communication.parameter.QueryParameter;
import com.hamweather.aeris.logging.Logger;
import com.hamweather.aeris.model.AerisError;
import com.hamweather.aeris.model.Place;
import com.hamweather.aeris.response.PlacesResponse;
import com.hamweather.aeris.util.ValidationUtil;

public class LocationSearchActivity extends Activity implements
		OnClickListener, TextWatcher, PlacesTaskCallback,
		AerisProgressListener, OnItemClickListener, AerisDialogOKListener,
		EnterPressedDelegate {

	private static final int PLACE_DIALOG = 1;
	private static final int TEXT_CHANGED_MSG = 1;
	private EditText searchEditText;
	private ImageButton searchButton;
	private PlacesAdapter searchAdapter;
	private ListView searchListView;
	private PlacesTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		this.setContentView(R.layout.activity_location_search);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		searchEditText = (EditText) this.findViewById(R.id.etSearch);
		searchButton = (ImageButton) this.findViewById(R.id.ibSearch);
		searchButton.setOnClickListener(this);
		searchListView = (ListView) this.findViewById(R.id.lvSearch);
		searchEditText.addTextChangedListener(this);
		searchListView.setOnItemClickListener(this);
		searchEditText.setOnKeyListener(new EditTextEnterListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuLocateMe:
			if (task != null) {
				task.cancel(true);
			}
			task = new PlacesTask(this, this);
			task.withDebug(true);
			task.withProgress(this);
			ParameterBuilder builder = new ParameterBuilder().withLimit(25)
					.withRadius("50mi");
			task.requestClosest(new PlaceParameter(this), builder.build());
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
			return true;
		case R.id.menuMyLocs:
			startActivity(new Intent(this, MyLocsActivity.class));
			return true;
		case android.R.id.home:
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		typeHandler.removeMessages(TEXT_CHANGED_MSG);
		if (s.length() > 2) {
			typeHandler
					.sendMessageDelayed(
							Message.obtain(typeHandler, TEXT_CHANGED_MSG,
									s.toString()), 800);
		} else {
			if (searchAdapter != null) {
				searchAdapter.setList(new ArrayList<PlacesResponse>());
				searchAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	private void performPlaceSearch(String text) {
		if (task != null) {
			task.cancel(true);
		}
		task = new PlacesTask(this, this);
		task.withDebug(true);
		task.withProgress(this);
		text = text.trim();
		ParameterBuilder builder = new ParameterBuilder();
		if (ValidationUtil.isValidCoordinate(text)) {
			String[] temp = text.split(",");
			try {
				double latitude = Double.parseDouble(temp[0]);
				double longitude = Double.parseDouble(temp[1]);
				builder.withRadius("50mi");
				task.requestClosest(new PlaceParameter(latitude, longitude),
						builder.build());
			} catch (NumberFormatException ex) {
				Toast.makeText(this, "Places search based on lat/lng failed!",
						Toast.LENGTH_SHORT).show();
				return;
			}

		} else if (ValidationUtil.isValidZipcode(text)) {
			task.requestSearch(new QueryParameter("zipcode:" + text));
		} else if (!ValidationUtil.isNumber(text)
				&& ValidationUtil.isValidPlaceString(text)) {
			String temp[] = text.split(",");
			builder.withLimit(50).withFilter("cities").withSort("pop:-1");
			if (temp.length == 1) {
				builder.withQuery("name:^" + text.toLowerCase());
				task.requestSearch(builder.build());
			} else {
				String name = temp[0].trim();
				String state = null;
				if (temp.length > 1) {
					state = temp[1].trim().toLowerCase();
					if (state.length() == 1) {
						state = "^" + state;
					}
				}
				String country = null;
				if (temp.length > 2) {
					country = temp[2].trim().toLowerCase();
					if (country.length() == 1) {
						country = "^" + country;
					}
				}
				StringBuilder queryBuilder = new StringBuilder("name:");
				queryBuilder.append(name);
				if (state != null) {
					queryBuilder.append(",");
					queryBuilder.append("state:");
					queryBuilder.append(state);
				}
				if (country != null) {
					queryBuilder.append(",");
					queryBuilder.append("country:");
					queryBuilder.append(country);
				}
				builder.withQuery(queryBuilder.toString());
				task.requestSearch(builder.build());
			}

		} else {
			Logger.i("TEST", "Failed validation");
			return;
		}

	}

	@Override
	public void onPlacesFailed(AerisError error) {
		Logger.i("TEST", error.description);
	}

	@Override
	public void onPlacesLoaded(List<PlacesResponse> responses) {
		searchAdapter = new PlacesAdapter(responses, this);
		searchListView.setAdapter(searchAdapter);
	}

	private TypeHandlingListener typeHandler = new TypeHandlingListener(this);

	private static class TypeHandlingListener extends Handler {

		private final WeakReference<LocationSearchActivity> target;

		TypeHandlingListener(LocationSearchActivity activity) {
			target = new WeakReference<LocationSearchActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			LocationSearchActivity activity = target.get();
			if (activity != null) {
				activity.performPlaceSearch((String) msg.obj);
			}
		}
	}

	@Override
	public void hideProgress() {
		if (this != null)
			setProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void showProgress() {
		if (this != null)
			setProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PlacesResponse place = searchAdapter.getItem(position);
		AerisDialog dialog = new AerisDialog();
		dialog.withListener(this)
				.withMessage("Would you like to add this as your locations?")
				.withObject(place).withType(PLACE_DIALOG);
		dialog.show(getFragmentManager(), "myloc");

	}

	@Override
	public void onOKPressed(int type, Object object) {
		if (type == PLACE_DIALOG) {
			PlacesResponse response = (PlacesResponse) object;
			Place place = response.getPlace();
			if (place != null) {
				MyPlacesDb db = new MyPlacesDb(this);
				long id = db.insertPlaces(place.name, place.state,
						place.country, true);
				Toast.makeText(this, "Inserted row= " + id, Toast.LENGTH_LONG)
						.show();
				db.close();
			}

		}
	}

	@Override
	public void onEnterPressed(int viewId, EditText edit) {
		typeHandler.removeMessages(TEXT_CHANGED_MSG);
		performPlaceSearch(edit.getEditableText().toString());
	}

}
