package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.GpsDataEntity;

/**
 * Created by Andrew on 15.03.2016.
 */
public class GpsDataModel  extends ParentModel
{
	private long trip_id;
	private String time_stamp;
	private double latitude;
	private double longitude;
	private double altitude;
	private float speed;

	public static class GpsDataNames
	{
		public final static String ID = "id";
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String LATITUDE = "latitude";
		public final static String LONGITUDE = "longitude";
		public final static String ALTITUDE = "altitude";
		public final static String SPEED = "speed";

		public final static String TABLENAME = "GPS_DATA_TABLE";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TRIP_ID + " INTEGER NOT NULL, " +
				TIME_STAMP + " TEXT NOT NULL, " +
				LATITUDE + " REAL NOT NULL, " +
				LONGITUDE + " REAL NOT NULL, " +
				ALTITUDE + " REAL NOT NULL, " +
				SPEED + " REAL NOT NULL);";
	}

	public GpsDataModel()
	{
		tableName = GpsDataNames.TABLENAME;
		columns = new String[] { GpsDataNames.ID, GpsDataNames.TRIP_ID, GpsDataNames.TIME_STAMP,
				GpsDataNames.LATITUDE, GpsDataNames.LONGITUDE, GpsDataNames.ALTITUDE,
				GpsDataNames.SPEED };
	}

	public GpsDataModel(GpsDataEntity gpsDataEntity)
	{
		tableName = GpsDataNames.TABLENAME;
		columns = new String[] { GpsDataNames.ID, GpsDataNames.TRIP_ID, GpsDataNames.TIME_STAMP,
				GpsDataNames.LATITUDE, GpsDataNames.LONGITUDE, GpsDataNames.ALTITUDE,
				GpsDataNames.SPEED };
		this.trip_id = gpsDataEntity.trip_id;
		this.time_stamp = gpsDataEntity.time_stamp;
		this.latitude = gpsDataEntity.latitude;
		this.longitude = gpsDataEntity.longitude;
		this.altitude = gpsDataEntity.altitude;
		this.speed = gpsDataEntity.speed;
	}

	@Override
	public ContentValues getInsert()
	{
		contentValues = new ContentValues();
		contentValues.put(GpsDataNames.TRIP_ID, trip_id);
		contentValues.put(GpsDataNames.TIME_STAMP, time_stamp);
		contentValues.put(GpsDataNames.LATITUDE, latitude);
		contentValues.put(GpsDataNames.LONGITUDE, longitude);
		contentValues.put(GpsDataNames.ALTITUDE, altitude);
		contentValues.put(GpsDataNames.SPEED, speed);
		return contentValues;
	}
}
