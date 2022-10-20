package com.example.db;

import android.content.Context;
import android.database.Cursor;

public class MyLocLoader extends SimpleCursorLoader {

	public MyLocLoader(Context context) {
		super(context);
	}

	@Override
	public Cursor loadInBackground() {
		MyPlacesDb db = new MyPlacesDb(getContext());
		Cursor cursor = db.getMyPlaceCursor();
		return cursor;
	}

}
