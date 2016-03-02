package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;

/**
 * Created by Andrew on 01.03.2016.
 */
public class TripModel extends ParentModel
{
	private long user_id;
	private String start_time;
	private String finish_time;
	private double mark;

	public static class TripNames
	{
		public final static String ID = "id";
		public final static String USER_ID = "user_id";
		public final static String START_TIME = "start_time";
		public final static String FINISH_TIME = "finish_time";
		public final static String MARK = "mark";

		public final static String TABLENAME = "triptable";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				USER_ID + " INTEGER NOT NULL, " +
				START_TIME + " TEXT UNIQUE NOT NULL, " +
				FINISH_TIME + " TEXT NOT NULL, " +
				MARK + " REAL NOT NULL);";
	}

	public TripModel()
	{
		tableName = TripNames.TABLENAME;
		columns = new String[] { TripNames.ID, TripNames.USER_ID, TripNames.START_TIME,
				TripNames.FINISH_TIME, TripNames.MARK };
	}

	public TripModel(TripEntity tripEntity)
	{
		tableName = TripNames.TABLENAME;
		columns = new String[] { TripNames.ID, TripNames.USER_ID, TripNames.START_TIME,
				TripNames.FINISH_TIME, TripNames.MARK };
		this.user_id = tripEntity.user_id;
		this.start_time = tripEntity.start_time;
		this.finish_time = tripEntity.finish_time;
		this.mark = tripEntity.mark;
	}

	@Override
	public ContentValues getInsert()
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(TripNames.USER_ID, user_id);
		contentValues.put(TripNames.START_TIME, start_time);
		contentValues.put(TripNames.FINISH_TIME, finish_time);
		contentValues.put(TripNames.MARK, mark);
		return contentValues;
	}

}
