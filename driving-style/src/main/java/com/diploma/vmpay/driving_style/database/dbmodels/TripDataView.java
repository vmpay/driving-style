package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;

/**
 * Created by Andrew on 01.03.2016.
 */
public class TripDataView extends ParentModel
{
	private long user_id;
	private String start_time;
	private String finish_time;
	private double mark;

	public static class TripDataNames
	{
		public final static String ID = "trip_id";
		public final static String USER_ID = "user_id";
		public final static String START_TIME = "start_time";
		public final static String FINISH_TIME = "finish_time";
		public final static String MARK = "mark";
		public final static String ACC_ID = "id";
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String ACC_X = "acc_x";
		public final static String ACC_Y = "acc_y";
		public final static String ACC_Z = "acc_z";

		public final static String TABLENAME = "TRIP_DATA_VIEW";
		public final static String CREATE_TABLE = "CREATE VIEW " + TABLENAME +
				" AS SELECT " +
				TripModel.TripNames.TABLENAME + "." + TripModel.TripNames.ID + ", " +
				TripModel.TripNames.USER_ID + ", " +
				TripModel.TripNames.START_TIME + ", " +
				TripModel.TripNames.FINISH_TIME + ", " +
				TripModel.TripNames.MARK + ", " +
				//AccDataModel.AccDataNames.ID + ", " +
				AccDataModel.AccDataNames.TIME_STAMP + ", " +
				AccDataModel.AccDataNames.ACC_X + ", " +
				AccDataModel.AccDataNames.ACC_Y + ", " +
				AccDataModel.AccDataNames.ACC_Z +
				" FROM " + TripModel.TripNames.TABLENAME +
				" INNER JOIN " + AccDataModel.AccDataNames.TABLENAME +
				" ON " + TripModel.TripNames.TABLENAME + "." + TripModel.TripNames.ID +
				" = " + AccDataModel.AccDataNames.TABLENAME + "." + AccDataModel.AccDataNames.TRIP_ID;
	}

	public TripDataView()
	{
		tableName = TripDataNames.TABLENAME;
		columns = new String[] { TripDataNames.ID,
				TripDataNames.USER_ID, TripDataNames.START_TIME,
				TripDataNames.FINISH_TIME, TripDataNames.MARK, //TripDataNames.ACC_ID,
				TripDataNames.TIME_STAMP,
				TripDataNames.ACC_X, TripDataNames.ACC_Y, TripDataNames.ACC_Z };
	}

	public TripDataView(TripEntity tripEntity)
	{
		tableName = TripDataNames.TABLENAME;
		columns = new String[] { TripDataNames.ID, TripDataNames.USER_ID, TripDataNames.START_TIME,
				TripDataNames.FINISH_TIME, TripDataNames.MARK, TripDataNames.ACC_ID, TripDataNames.TIME_STAMP,
				TripDataNames.ACC_X, TripDataNames.ACC_Y, TripDataNames.ACC_Z };
		this.user_id = tripEntity.user_id;
		this.start_time = tripEntity.start_time;
		this.finish_time = tripEntity.finish_time;
		this.mark = tripEntity.mark;
	}
	
	@Override
	public ContentValues getInsert()
	{
		return null;
	}
}
