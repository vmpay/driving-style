package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;

/**
 * Created by Andrew on 01.03.2016.
 */
public class AccDataModel extends ParentModel
{
	private long trip_id;
	private String time_stamp;
	private double acc_x;
	private double acc_y;
	private double acc_z;

	public static class AccDataNames
	{
		public final static String ID = "id";
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String ACC_X = "acc_x";
		public final static String ACC_Y = "acc_y";
		public final static String ACC_Z = "acc_z";

		public final static String TABLENAME = "AccDataTable";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TRIP_ID + " INTEGER NOT NULL, " +
				TIME_STAMP + " TEXT NOT NULL, " +
				ACC_X + " REAL NOT NULL, " +
				ACC_Y + " REAL NOT NULL, " +
				ACC_Z + " REAL NOT NULL);";
	}

	public AccDataModel()
	{
		tableName = AccDataNames.TABLENAME;
		columns = new String[] { AccDataNames.ID, AccDataNames.TRIP_ID, AccDataNames.TIME_STAMP,
				AccDataNames.ACC_X, AccDataNames.ACC_Y, AccDataNames.ACC_Z };
	}

	public AccDataModel(AccDataEntity accDataEntity)
	{
		tableName = AccDataNames.TABLENAME;
		columns = new String[] { AccDataNames.ID, AccDataNames.TRIP_ID, AccDataNames.TIME_STAMP,
				AccDataNames.ACC_X, AccDataNames.ACC_Y, AccDataNames.ACC_Z };
		this.trip_id = accDataEntity.trip_id;
		this.time_stamp = accDataEntity.time_stamp;
		this.acc_x = accDataEntity.acc_x;
		this.acc_y = accDataEntity.acc_y;
		this.acc_z = accDataEntity.acc_z;
	}

	@Override
	public ContentValues getInsert()
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(AccDataNames.TRIP_ID, trip_id);
		contentValues.put(AccDataNames.TIME_STAMP, time_stamp);
		contentValues.put(AccDataNames.ACC_X, acc_x);
		contentValues.put(AccDataNames.ACC_Y, acc_y);
		contentValues.put(AccDataNames.ACC_Z, acc_z);
		return contentValues;
	}
}
