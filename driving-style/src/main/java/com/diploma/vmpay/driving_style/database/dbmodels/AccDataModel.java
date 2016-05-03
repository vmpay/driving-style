package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 01.03.2016.
 */
public class AccDataModel extends ParentModel
{
	private long tripId;
	private long timeStamp;
	private double accX;
	private double accY;
	private double accZ;

	public static class AccDataNames
	{
		public final static String ID = ParentModel.ID;
		public final static String TRIP_ID = "trip_id";
		public final static String TIME_STAMP = "time_stamp";
		public final static String ACC_X = "acc_x";
		public final static String ACC_Y = "acc_y";
		public final static String ACC_Z = "acc_z";

		public final static String TABLENAME = "acc_data_table";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TRIP_ID + " INTEGER NOT NULL, " +
				TIME_STAMP + " INTEGER NOT NULL, " +
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

	@Deprecated
	public AccDataModel(AccDataEntity accDataEntity)
	{
		this();
		this.tripId = accDataEntity.tripId;
		this.timeStamp = accDataEntity.timeStamp;
		this.accX = accDataEntity.accX;
		this.accY = accDataEntity.accY;
		this.accZ = accDataEntity.accZ;
	}

	public AccDataModel(long tripId, long timeStamp, double accX, double accY, double accZ)
	{
		this();
		this.tripId = tripId;
		this.timeStamp = timeStamp;
		this.accX = accX;
		this.accY = accY;
		this.accZ = accZ;
	}

	public static List<AccDataModel> buildFromContentValuesList(List<ContentValues> contentValuesList)
	{
		List<AccDataModel> accDataModelList = new ArrayList<>();
		for(ContentValues contentValues : contentValuesList)
		{
			accDataModelList.add(buildFromContentValues(contentValues));
		}
		return accDataModelList;
	}

	public static AccDataModel buildFromContentValues(ContentValues contentValues)
	{
		AccDataModel accDataModel = new AccDataModel();

		accDataModel.setId(contentValues.getAsLong(AccDataNames.ID));
		accDataModel.setTripId(contentValues.getAsLong(AccDataNames.TRIP_ID));
		accDataModel.setTimeStamp(contentValues.getAsLong(AccDataNames.TIME_STAMP));
		accDataModel.setAccX(contentValues.getAsLong(AccDataNames.ACC_X));
		accDataModel.setAccY(contentValues.getAsLong(AccDataNames.ACC_Y));
		accDataModel.setAccZ(contentValues.getAsLong(AccDataNames.ACC_Z));

		return accDataModel;
	}

	@Override
	public ContentValues getInsert()
	{
		contentValues = new ContentValues();
		contentValues.put(AccDataNames.TRIP_ID, tripId);
		contentValues.put(AccDataNames.TIME_STAMP, timeStamp);
		contentValues.put(AccDataNames.ACC_X, accX);
		contentValues.put(AccDataNames.ACC_Y, accY);
		contentValues.put(AccDataNames.ACC_Z, accZ);
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

	public void setAccX(double accX)
	{
		this.accX = accX;
	}

	public void setAccY(double accY)
	{
		this.accY = accY;
	}

	public void setAccZ(double accZ)
	{
		this.accZ = accZ;
	}
}
