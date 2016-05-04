package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.GpsDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 15.03.2016.
 */
public class GpsDataModel  extends ParentModel
{
	private long tripId;
	private long timeStamp;
	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;

	public static class GpsDataNames
	{
		public final static String ID = ParentModel.ID;
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String LATITUDE = "latitude";
		public final static String LONGITUDE = "longitude";
		public final static String ALTITUDE = "altitude";
		public final static String SPEED = "speed";

		public final static String TABLENAME = "gps_data_table";
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

	@Deprecated
	public GpsDataModel(GpsDataEntity gpsDataEntity)
	{
		this();
		this.tripId = gpsDataEntity.tripId;
		this.timeStamp = gpsDataEntity.timeStamp;
		this.latitude = gpsDataEntity.latitude;
		this.longitude = gpsDataEntity.longitude;
		this.altitude = gpsDataEntity.altitude;
		this.speed = gpsDataEntity.speed;
	}

	public GpsDataModel(long tripId, long timeStamp, double latitude, double longitude, double altitude, double speed)
	{
		this();
		this.tripId = tripId;
		this.timeStamp = timeStamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.speed = speed;
	}

	public static List<GpsDataModel> buildFromContentValuesList(List<ContentValues> contentValuesList)
	{
		List<GpsDataModel> gpsDataModelList = new ArrayList<>();
		for(ContentValues contentValues : contentValuesList)
		{
			gpsDataModelList.add(buildFromContentValues(contentValues));
		}
		return gpsDataModelList;
	}

	public static GpsDataModel buildFromContentValues(ContentValues contentValues)
	{
		GpsDataModel gpsDataModel = new GpsDataModel();

		gpsDataModel.setId(contentValues.getAsLong(GpsDataNames.ID));
		gpsDataModel.setTripId(contentValues.getAsLong(GpsDataNames.TRIP_ID));
		gpsDataModel.setLatitude(contentValues.getAsDouble(GpsDataNames.LATITUDE));
		gpsDataModel.setLongitude(contentValues.getAsDouble(GpsDataNames.LONGITUDE));
		gpsDataModel.setAltitude(contentValues.getAsDouble(GpsDataNames.ALTITUDE));
		gpsDataModel.setSpeed(contentValues.getAsDouble(GpsDataNames.SPEED));

		return gpsDataModel;
	}

	@Override
	public ContentValues getInsert()
	{
		contentValues = new ContentValues();
		contentValues.put(GpsDataNames.TRIP_ID, tripId);
		contentValues.put(GpsDataNames.TIME_STAMP, timeStamp);
		contentValues.put(GpsDataNames.LATITUDE, latitude);
		contentValues.put(GpsDataNames.LONGITUDE, longitude);
		contentValues.put(GpsDataNames.ALTITUDE, altitude);
		contentValues.put(GpsDataNames.SPEED, speed);
		return contentValues;
	}

	public void setTripId(long tripId)
	{
		this.tripId = tripId;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public void setAltitude(double altitude)
	{
		this.altitude = altitude;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
}
