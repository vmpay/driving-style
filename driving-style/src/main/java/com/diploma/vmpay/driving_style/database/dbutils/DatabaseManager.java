package com.diploma.vmpay.driving_style.database.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;
import com.diploma.vmpay.driving_style.database.dbentities.GpsDataEntity;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.AccDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
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

	public long addTrip(TripEntity tripEntity)
	{
		Log.d("DB", "INSERT trip user_id " + tripEntity.user_id + " start_time " + tripEntity.start_time
				+ " mark " + tripEntity.mark);
		parentModel = new TripModel(tripEntity);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful trip_id = " + success);
			return success;
		}
		Log.d("DB", "Transaction failed");
		return success;
	}

	public boolean updateTrip(TripEntity tripEntity)
	{
		Log.d("DB", "UPDATE user_id " + tripEntity.user_id + " start_time " + tripEntity.start_time
				+ " mark " + tripEntity.mark);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.START_TIME + "='" + tripEntity.start_time + "'");
		long success = databaseAccess.update(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful");
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	public boolean updateTrip(long index, TripEntity tripEntity)
	{
		Log.d("DB", "UPDATE trip_id " + index);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.ID + "=" + index);
		long success = databaseAccess.update(parentModel);
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
		Log.d("DB", "DELETE finish_time " + tripEntity.finish_time);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.FINISH_TIME + "='" + tripEntity.finish_time + "'");
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

	public boolean deleteTrip(long index)
	{
		Log.d("DB", "DELETE ID " + index);
		parentModel = new TripModel();
		parentModel.setWhereClause(TripModel.TripNames.ID + "=" + index);
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
		parentModel.setWhereClause(TripModel.TripNames.START_TIME + "='" + start_time + "'");
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT Trip");
		return results;
	}

	// ACC DATA

	public boolean addAccData(AccDataEntity accDataEntity)
	{
		Log.d("DB", "INSERT accData trip_id " + accDataEntity.trip_id + " time_stamp " + accDataEntity.time_stamp);
		parentModel = new AccDataModel(accDataEntity);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful acc_id = " + success);
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

	// GPS DATA

	public boolean addGpsData(GpsDataEntity gpsDataEntity)
	{
		Log.d("DB", "INSERT accData trip_id " + gpsDataEntity.trip_id + " time_stamp " + gpsDataEntity.time_stamp);
		parentModel = new GpsDataModel(gpsDataEntity);
		long success = databaseAccess.insert(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful acc_id = " + success);
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	public boolean deleteGpsData(GpsDataEntity gpsDataEntity)
	{
		Log.d("DB", "DELETE id " + gpsDataEntity.trip_id);
		parentModel = new GpsDataModel(gpsDataEntity);
		parentModel.setWhereClause(AccDataModel.AccDataNames.TRIP_ID+ "=" + gpsDataEntity.trip_id);
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

	public long deleteAllGpsDatas()
	{
		parentModel = new GpsDataModel();
		long success = databaseAccess.delete(parentModel);
		return success;
	}

	public List<ContentValues> getGpsData()
	{
		parentModel = new GpsDataModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DA", "SELECT AccData");
		return results;
	}
}
