package com.xweather.sdkdemo.java.db;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.xweather.sdkdemo.java.demoaerisproject.BaseApplication;
import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.xweather.sdkdemo.java.preference.PrefManager;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;

public class MyPlacesDb extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "AerisPlaces.db";
	public static final int DB_VERSION = 1;

	private Context context;

	public MyPlacesDb(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
		this.context = context;
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
		db.execSQL(MyPlaceTable.CREATE_TABLE_STMT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static final class MyPlaceTable implements
			android.provider.BaseColumns {
		/** The name of this table */
		public static final String TABLE = "aeris_places";

		static final String CREATE_TABLE_STMT = "CREATE TABLE "
				+ MyPlaceTable.TABLE + "(" + " " + BaseColumns._ID + " "
				+ PlacesColumns._ID_TYPE + "," + " " + PlacesColumns.NAME + " "
				+ PlacesColumns.NAME_TYPE + ", " + PlacesColumns.STATE + " "
				+ PlacesColumns.STATE_TYPE + ", " + PlacesColumns.COUNTRY + " "
				+ PlacesColumns.COUNTRY_TYPE + ", " + PlacesColumns.LATITUDE
				+ " " + PlacesColumns.LATITUDE_TYPE + ", "
				+ PlacesColumns.LONGITUDE + " " + PlacesColumns.LONGITUDE_TYPE
				+ ", " + PlacesColumns.MY_CITY
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
		public static final String LATITUDE = "latitude";
		static final String LATITUDE_TYPE = "REAL";
		public static final String LONGITUDE = "longitude";
		static final String LONGITUDE_TYPE = "REAL";

	}

	public long insertPlaces(String name, String state, String country,
			double lat, double lon,
			boolean myPlace) {
		SQLiteDatabase sqldb = getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(PlacesColumns.NAME, name);
		if (state != null)
			args.put(PlacesColumns.STATE, state);
		if (country != null)
			args.put(PlacesColumns.COUNTRY, country);
		args.put(PlacesColumns.LATITUDE, lat);
		args.put(PlacesColumns.LONGITUDE, lon);
		args.put(PlacesColumns.MY_CITY, myPlace);
		Cursor cursor = sqldb.query(MyPlaceTable.TABLE,
				new String[] { PlacesColumns.NAME }, PlacesColumns.NAME
						+ "=? AND " + PlacesColumns.COUNTRY + "=?",
				new String[] { name, country }, null, null, null);
		long id = -1;
		if (cursor != null && cursor.moveToFirst()) {
			id = sqldb.update(MyPlaceTable.TABLE, args, PlacesColumns.NAME
					+ "=? AND " + PlacesColumns.COUNTRY + "=?", new String[] {
					name, country });
		} else {
			id = sqldb.insert(MyPlaceTable.TABLE, null, args);
		}
		cursor.close();
		if (myPlace) {
			ContentValues changeArgs = new ContentValues();
			changeArgs.put(PlacesColumns.MY_CITY, false);
			// get all rows, update them to false minus the correct one.
			sqldb.update(MyPlaceTable.TABLE, changeArgs, PlacesColumns.NAME
					+ "!=? OR " + PlacesColumns.COUNTRY + "!=?", new String[] {
					name, country });
			BaseApplication.enableNotificationService(context);
			MyPlacesSubject.getInstance().notifyObservers(null);
		}
		return id;
	}

	public boolean deletePlace(MyPlace place) {

		if (place != null && place.name != null && place.country != null) {
			SQLiteDatabase sqldb = getWritableDatabase();
			int affected = sqldb.delete(MyPlaceTable.TABLE, PlacesColumns.NAME
					+ "=? AND " + PlacesColumns.COUNTRY + "=?", new String[] {
					place.name, place.country });
			return affected > 0;
		}
		return false;
	}

	public Cursor getMyPlaceCursor() {
		SQLiteDatabase sqldb = getWritableDatabase();
		Cursor cursor = sqldb.query(MyPlaceTable.TABLE, null,
				PlacesColumns.MY_CITY + "=1", null, null, null, null);
		return cursor;
	}

	@SuppressLint("Range")
	public PlaceParameter getMyPlaceParameter() {
		Cursor cursor = getMyPlaceCursor();
		if (cursor != null && cursor.moveToFirst()) {
			MyPlace place = new MyPlace();
			place.name = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.NAME));
			place.state = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.STATE));
			place.country = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.COUNTRY));
			cursor.close();
			if (place.state != null) {
				return new PlaceParameter(String.format("%s,%s,%s", place.name,
						place.state, place.country));
			} else {
				return new PlaceParameter(String.format("%s,%s", place.name,
						place.country));
			}

		}
		return null;
	}
	@SuppressLint("Range")
	public MyPlace getMyPlace() {
		Cursor cursor = getMyPlaceCursor();
		if (cursor != null && cursor.moveToFirst()) {
			MyPlace place = new MyPlace();
			place.name = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.NAME));
			place.state = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.STATE));
			place.country = cursor.getString(cursor
					.getColumnIndex(PlacesColumns.COUNTRY));
			place.latitude = cursor.getDouble(cursor
					.getColumnIndex(PlacesColumns.LATITUDE));
			place.longitude = cursor.getDouble(cursor
					.getColumnIndex(PlacesColumns.LONGITUDE));
			cursor.close();
			return place;
		}
		return null;
	}
	@SuppressLint("Range")
	public List<MyPlace> getPlaces() {
		SQLiteDatabase sqldb = getWritableDatabase();
		Cursor cursor = sqldb.query(MyPlaceTable.TABLE, null, null, null, null,
				null, null);
		List<MyPlace> retval = new ArrayList<MyPlace>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				MyPlace place = new MyPlace();
				place.name = cursor.getString(cursor
						.getColumnIndex(PlacesColumns.NAME));
				place.state = cursor.getString(cursor
						.getColumnIndex(PlacesColumns.STATE));
				place.country = cursor.getString(cursor
						.getColumnIndex(PlacesColumns.COUNTRY));
				place.myLoc = cursor.getInt(cursor
						.getColumnIndex(PlacesColumns.MY_CITY)) == 1;
				place.latitude = cursor.getDouble(cursor
						.getColumnIndex(PlacesColumns.LATITUDE));
				place.longitude = cursor.getDouble(cursor
						.getColumnIndex(PlacesColumns.LONGITUDE));
				retval.add(place);
			}
			cursor.close();
		}
		return retval;
	}

}
