package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.hamweather.aeris.model.Place;
import com.hamweather.aeris.response.PlacesResponse;

public class MyPlacesDb extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "AerisPlaces.db";
	public static final int DB_VERSION = 1;

	public MyPlacesDb(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MyPlace.CREATE_TABLE_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static final class MyPlace implements android.provider.BaseColumns {
		/** The name of this table */
		public static final String TABLE = "aeris_places";

		static final String CREATE_TABLE_STMT = "CREATE TABLE " + MyPlace.TABLE
				+ "(" + " " + BaseColumns._ID + " " + PlacesColumns._ID_TYPE
				+ "," + " " + PlacesColumns.NAME + " "
				+ PlacesColumns.NAME_TYPE + ", " + PlacesColumns.STATE + " "
				+ PlacesColumns.STATE_TYPE + ", " + PlacesColumns.COUNTRY + " "
				+ PlacesColumns.COUNTRY_TYPE + ", " + PlacesColumns.MY_CITY
				+ " " + PlacesColumns.MY_CITY_TYPE + ");";
	}

	public static class PlacesColumns {
		static final String _ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
		public static final String NAME = "name";
		static final String NAME_TYPE = "TEXT NOT NULL";
		public static final String STATE = "state";
		static final String STATE_TYPE = "TEXT";
		public static final String COUNTRY = "country";
		static final String COUNTRY_TYPE = "TEXT";
		public static final String MY_CITY = "mycity";
		static final String MY_CITY_TYPE = "BOOLEAN";

	}

	public long insertPlaces(PlacesResponse response, boolean myPlace) {
		SQLiteDatabase sqldb = getWritableDatabase();
		ContentValues args = new ContentValues();
		Place place = response.getPlace();
		args.put(PlacesColumns.NAME, place.name);
		if (place.state != null)
			args.put(PlacesColumns.STATE, place.state);
		if (place.country != null)
			args.put(PlacesColumns.COUNTRY, place.country);

		args.put(PlacesColumns.MY_CITY, myPlace);
		Cursor cursor = sqldb.query(MyPlace.TABLE,
				new String[] { PlacesColumns.NAME }, PlacesColumns.NAME
						+ "=? AND " + PlacesColumns.COUNTRY + "=?",
				new String[] { place.name, place.country }, null, null, null);
		long id = -1;
		if (cursor != null && cursor.moveToFirst()) {
			id = sqldb.update(MyPlace.TABLE, args, PlacesColumns.NAME
					+ "=? AND " + PlacesColumns.COUNTRY + "=?", new String[] {
					place.name, place.country });
		} else {
			id = sqldb.insert(MyPlace.TABLE, null, args);
		}
		if (myPlace) {
			ContentValues changeArgs = new ContentValues();
			changeArgs.put(PlacesColumns.MY_CITY, false);
			// get all rows, update them to false minus the correct one.
			sqldb.update(MyPlace.TABLE, args, PlacesColumns.NAME + "!=? OR "
					+ PlacesColumns.COUNTRY + "!=?", new String[] { place.name,
					place.country });
		}
		return id;
	}

	public boolean deletePlace(PlacesResponse data) {
		Place place = data.getPlace();
		if (place != null && place.name != null && place.country != null) {
			SQLiteDatabase sqldb = getWritableDatabase();
			int affected = sqldb.delete(MyPlace.TABLE, PlacesColumns.NAME
					+ "=? AND " + PlacesColumns.COUNTRY + "=?", new String[] {
					place.name, place.country });
			return affected > 0;
		}
		return false;
	}

	public Cursor getMyPlaceCursor() {
		SQLiteDatabase sqldb = getWritableDatabase();
		Cursor cursor = sqldb.query(MyPlace.TABLE, null, PlacesColumns.MY_CITY
				+ "=1", null, null, null, null);
		return cursor;
	}

	public Place getMyPlace() {
		Cursor cursor = getMyPlaceCursor();
		if (cursor != null && cursor.moveToFirst()) {
			Place place = new Place();
			place.name = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.NAME));
			place.state = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.STATE));
			place.country = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.COUNTRY));
			return place;
		}
		return null;
	}

	public List<Place> getPlaces() {
		SQLiteDatabase sqldb = getWritableDatabase();
		Cursor cursor = sqldb.query(MyPlace.TABLE, null, null, null, null,
				null, null);
		List<Place> retval = new ArrayList<Place>();
		if (cursor != null && cursor.moveToFirst()) {
			Place place = new Place();
			place.name = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.NAME));
			place.state = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.STATE));
			place.country = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.COUNTRY));
			retval.add(place);
		}
		return retval;
	}

}
