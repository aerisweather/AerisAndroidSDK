package com.example.demoaerisproject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class MyLocsActivity extends Activity {

	private ListView myLocsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		this.setContentView(R.layout.activity_my_location);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		myLocsListView = (ListView) this.findViewById(R.id.lvMyLocs);

	}
}
