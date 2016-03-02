package com.diploma.vmpay.driving_style.database.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.AccDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.ParentModel;
import com.diploma.vmpay.driving_style.database.dbmodels.TripDataView;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Andrew on 01.03.2016.
 */
public class DatabaseManager
{
	private static final String LOG_TAG = "DB";

	DatabaseAccess databaseAccess;
	ParentModel parentModel;
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	Context mContext;

	public DatabaseManager(Context mContext)
	{
		databaseAccess = new DatabaseAccess(mContext);
		this.mContext = mContext;
	}

	// TRIP

	public boolean addTrip(TripEntity tripEntity)
	{
		Log.d("DB", "INSERT user_id " + tripEntity.user_id + " start_time " + tripEntity.start_time
				+ " mark " + tripEntity.mark);
		parentModel = new TripModel(tripEntity);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful");
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	public boolean updateTrip(TripEntity tripEntity)
	{
		Log.d("DB", "INSERT user_id " + tripEntity.user_id + " start_time " + tripEntity.start_time
				+ " mark " + tripEntity.mark);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.START_TIME + "=" + tripEntity.start_time);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful");
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	public boolean deleteTrip(TripEntity tripEntity)
	{
		Log.d("DB", "DELETE id " + tripEntity.user_id);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.USER_ID + "=" + tripEntity.user_id);
		long success = databaseAccess.delete(parentModel);
		if(success > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public long deleteAllTrips()
		{
		parentModel = new TripModel();
		long success = databaseAccess.delete(parentModel);
		return success;
	}

	public List<ContentValues> getAllTrips()
	{
		parentModel = new TripModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT Trips");
		return results;
	}

	public List<ContentValues> getTrip(String start_time)
	{
		Log.d(LOG_TAG, "getTrip: start_time = " + start_time);
		parentModel = new TripModel();
		//parentModel.setWhereClause(TripModel.TripNames.START_TIME + "=" + start_time);
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT Trip");
		return results;
	}

	// ACC DATA

	public boolean addAccData(AccDataEntity accDataEntity)
	{
		Log.d("DB", "INSERT trip_id " + accDataEntity.trip_id + " time_stamp " + accDataEntity.time_stamp);
		parentModel = new AccDataModel(accDataEntity);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful");
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	public boolean deleteAccData(AccDataEntity accDataEntity)
	{
		Log.d("DB", "DELETE id " + accDataEntity.trip_id);
		parentModel = new AccDataModel(accDataEntity);
		parentModel.setWhereClause(AccDataModel.AccDataNames.TRIP_ID+ "=" + accDataEntity.trip_id);
		long success = databaseAccess.delete(parentModel);
		if(success > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public long deleteAllAccDatas()
	{
		parentModel = new AccDataModel();
		long success = databaseAccess.delete(parentModel);
		return success;
	}

	public List<ContentValues> getAccData()
	{
		parentModel = new AccDataModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT AccData");
		return results;
	}

	// TRIP DATA VIEW

	public List<ContentValues> getTripData()
	{
		parentModel = new TripDataView();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT TripData");
		return results;
	}

}
