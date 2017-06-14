package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;

/**
 * Created by Andrew on 01.03.2016.
 */
public class TripDataView extends ParentModel
{
	private long userId;
	private long startTime;
	private long finishTime;
	private double mark;

	public TripDataView()
	{
		tableName = TripDataNames.TABLENAME;
		columns = new String[] { TripDataNames.ID,
				TripDataNames.LOGIN, TripDataNames.START_TIME,
				TripDataNames.FINISH_TIME, TripDataNames.MARK, //TripDataNames.ACC_ID,
				TripDataNames.TIME_STAMP,
				TripDataNames.ACC_X, TripDataNames.ACC_Y, TripDataNames.ACC_Z,
				TripDataNames.LATITUDE, TripDataNames.LONGITUDE, TripDataNames.ALTITUDE,
				TripDataNames.SPEED
		};
	}

	public TripDataView(TripEntity tripEntity)
	{
		tableName = TripDataNames.TABLENAME;
		columns = new String[] { TripDataNames.ID, TripDataNames.USER_ID, TripDataNames.START_TIME,
				TripDataNames.FINISH_TIME, TripDataNames.MARK, TripDataNames.ACC_ID,
				TripDataNames.TIME_STAMP,
				TripDataNames.ACC_X, TripDataNames.ACC_Y, TripDataNames.ACC_Z };
		this.userId = tripEntity.userId;
		this.startTime = tripEntity.startTime;
		this.finishTime = tripEntity.finishTime;
		this.mark = tripEntity.mark;
	}

	@Override
	public ContentValues getInsert()
	{
		return null;
	}
	
	public static class TripDataNames
	{
		public final static String ID = ParentModel.ID;
		public final static String USER_ID = "user_id";
		public final static String LOGIN = "login";
		public final static String START_TIME = "start_time";
		public final static String FINISH_TIME = "finish_time";
		public final static String MARK = "mark";
		public final static String ACC_ID = "id";
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String ACC_X = "acc_x";
		public final static String ACC_Y = "acc_y";
		public final static String ACC_Z = "acc_z";
		public final static String LATITUDE = "latitude";
		public final static String LONGITUDE = "longitude";
		public final static String ALTITUDE = "altitude";
		public final static String SPEED = "speed";

		public final static String TABLENAME = "trip_data_view";
		public final static String CREATE_TABLE = "CREATE VIEW " + TABLENAME +
				" AS SELECT " +
				TripModel.TripNames.TABLENAME + "." + TripModel.TripNames.ID + ", " +
				UserModel.UserNames.LOGIN + ", " +
				TripModel.TripNames.USER_ID + ", " +
				TripModel.TripNames.START_TIME + ", " +
				TripModel.TripNames.FINISH_TIME + ", " +
				TripModel.TripNames.MARK + ", " +
				AccDataModel.AccDataNames.TABLENAME + "." + AccDataModel.AccDataNames.TIME_STAMP + ", " +
				AccDataModel.AccDataNames.ACC_X + ", " +
				AccDataModel.AccDataNames.ACC_Y + ", " +
				AccDataModel.AccDataNames.ACC_Z + ", " +
				GpsDataModel.GpsDataNames.LONGITUDE + ", " +
				GpsDataModel.GpsDataNames.LATITUDE + ", " +
				GpsDataModel.GpsDataNames.ALTITUDE + ", " +
				GpsDataModel.GpsDataNames.SPEED +
				" FROM " + TripModel.TripNames.TABLENAME +
				" INNER JOIN " + UserModel.UserNames.TABLENAME +
				" ON " + TripModel.TripNames.TABLENAME + "." + TripModel.TripNames.USER_ID +
				" = " + UserModel.UserNames.TABLENAME + "." + UserModel.UserNames.ID +
				" INNER JOIN " + AccDataModel.AccDataNames.TABLENAME +
				" ON " + TripModel.TripNames.TABLENAME + "." + TripModel.TripNames.ID +
				" = " + AccDataModel.AccDataNames.TABLENAME + "." + AccDataModel.AccDataNames.TRIP_ID +
				" LEFT JOIN " + GpsDataModel.GpsDataNames.TABLENAME +
				" ON " + AccDataModel.AccDataNames.TABLENAME + "." + AccDataModel.AccDataNames.TIME_STAMP +
				" = " + GpsDataModel.GpsDataNames.TABLENAME + "." + GpsDataModel.GpsDataNames.TIME_STAMP;

	}
}
