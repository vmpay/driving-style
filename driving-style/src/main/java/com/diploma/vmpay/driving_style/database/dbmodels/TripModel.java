package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 01.03.2016.
 */
public class TripModel extends ParentModel
{
	private long userId;
	private long startTime;
	private long finishTime;
	private double mark;

	public static class TripNames
	{
		public final static String ID = ParentModel.ID;
		public final static String USER_ID = "user_id";
		public final static String START_TIME = "start_time";
		public final static String FINISH_TIME = "finish_time";
		public final static String MARK = "mark";

		public final static String TABLENAME = "trip_table";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				USER_ID + " INTEGER NOT NULL, " +
				START_TIME + " INTEGER UNIQUE NOT NULL, " +
				FINISH_TIME + " INTEGER NOT NULL, " +
				MARK + " REAL NOT NULL);";
	}

	public TripModel()
	{
		tableName = TripNames.TABLENAME;
		columns = new String[] { TripNames.ID, TripNames.USER_ID, TripNames.START_TIME,
				TripNames.FINISH_TIME, TripNames.MARK };
	}

	@Deprecated
	public TripModel(TripEntity tripEntity)
	{
		this();
		this.userId = tripEntity.userId;
		this.startTime = tripEntity.startTime;
		this.finishTime = tripEntity.finishTime;
		this.mark = tripEntity.mark;
	}

	public TripModel(long userId, long startTime, long finishTime, long mark)
	{
		this();
		this.userId = userId;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.mark = mark;
	}

	@Override
	public ContentValues getInsert()
	{
		contentValues = new ContentValues();
		contentValues.put(TripNames.USER_ID, userId);
		contentValues.put(TripNames.START_TIME, startTime);
		contentValues.put(TripNames.FINISH_TIME, finishTime);
		contentValues.put(TripNames.MARK, mark);
		return contentValues;
	}

	public static List<TripModel> buildFromContentValuesList(List<ContentValues> contentValuesList)
	{
		List<TripModel> tripModelList = new ArrayList<>();
		for(ContentValues contentValues : contentValuesList)
		{
			tripModelList.add(buildFromContentValues(contentValues));
		}
		return tripModelList;
	}

	public static TripModel buildFromContentValues(ContentValues contentValues)
	{
		TripModel tripModel = new TripModel();

		tripModel.setId(contentValues.getAsLong(TripNames.ID));
		tripModel.setUserId(contentValues.getAsLong(TripNames.USER_ID));
		tripModel.setStartTime(contentValues.getAsLong(TripNames.START_TIME));
		tripModel.setFinishTime(contentValues.getAsLong(TripNames.FINISH_TIME));
		tripModel.setMark(contentValues.getAsDouble(TripNames.MARK));

		return tripModel;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	public void setFinishTime(long finishTime)
	{
		this.finishTime = finishTime;
	}

	public void setMark(double mark)
	{
		this.mark = mark;
	}

	public long getFinishTime()
	{
		return finishTime;
	}
}
