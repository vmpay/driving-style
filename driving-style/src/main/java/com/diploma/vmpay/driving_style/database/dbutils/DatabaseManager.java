package com.diploma.vmpay.driving_style.database.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	Context mContext;

	public DatabaseManager(Context mContext)
	{
		databaseAccess = new DatabaseAccess(mContext);
		this.mContext = mContext;
	}

	// TRIP

	@Deprecated
	public long addTrip(TripEntity tripEntity)
	{
		Log.d("DB", "INSERT trip user_id " + tripEntity.userId + " start_time " + tripEntity.startTime
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

	@Deprecated
	public boolean updateTrip(TripEntity tripEntity)
	{
		Log.d("DB", "UPDATE user_id " + tripEntity.userId + " start_time " + tripEntity.startTime
				+ " mark " + tripEntity.mark);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.START_TIME + "='" + tripEntity.startTime + "'");
		long success = databaseAccess.update(parentModel);
		if(success > 0)
		{
			Log.d("DB", "Transaction successful");
			return true;
		}
		Log.d("DB", "Transaction failed");
		return false;
	}

	@Deprecated
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

	@Deprecated
	public boolean deleteTrip(TripEntity tripEntity)
	{
		Log.d("DB", "DELETE finish_time " + tripEntity.finishTime);
		parentModel = new TripModel(tripEntity);
		parentModel.setWhereClause(TripModel.TripNames.FINISH_TIME + "='" + tripEntity.finishTime + "'");
		long success = databaseAccess.delete(parentModel);
		return success > 0;
	}

	@Deprecated
	public boolean deleteTrip(long index)
	{
		Log.d("DB", "DELETE ID " + index);
		parentModel = new TripModel();
		parentModel.setWhereClause(TripModel.TripNames.ID + "=" + index);
		long success = databaseAccess.delete(parentModel);
		return success > 0;
	}

	@Deprecated
	public long deleteAllTrips()
	{
		parentModel = new TripModel();
		return (long) databaseAccess.delete(parentModel);
	}

	@Deprecated
	public List<ContentValues> getAllTrips()
	{
		parentModel = new TripModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DM", "SELECT Trips");
		return results;
	}

	@Deprecated
	public List<ContentValues> getTrip(String start_time)
	{
		Log.d(LOG_TAG, "getTrip: start_time = " + start_time);
		parentModel = new TripModel();
		parentModel.setWhereClause(TripModel.TripNames.START_TIME + "='" + start_time + "'");
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DM", "SELECT Trip");
		return results;
	}

	@Deprecated
	public boolean exportTrips(String fileName)
	{
		parentModel = new TripModel();
		Cursor cursor = databaseAccess.selectCursor(parentModel);
		boolean result = databaseAccess.exportToCSV(cursor, fileName);
		Log.d("DM", "EXPORT TripModel to " + fileName + ".CSV successfully " + result);
		return result;
	}

	// ACC DATA

	@Deprecated
	public boolean addAccData(AccDataEntity accDataEntity)
	{
		Log.d("DB", "INSERT accData trip_id " + accDataEntity.tripId + " time_stamp " + accDataEntity.timeStamp);
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

	@Deprecated
	public boolean addAccDataAsync(AccDataEntity accDataEntity)
	{
		Log.d("DB", "INSERT accData trip_id " + accDataEntity.tripId + " time_stamp " + accDataEntity.timeStamp);
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

	@Deprecated
	public boolean deleteAccData(AccDataEntity accDataEntity)
	{
		Log.d("DB", "DELETE id " + accDataEntity.tripId);
		parentModel = new AccDataModel(accDataEntity);
		parentModel.setWhereClause(AccDataModel.AccDataNames.TRIP_ID + "=" + accDataEntity.tripId);
		long success = databaseAccess.delete(parentModel);
		return success > 0;
	}

	@Deprecated
	public long deleteAllAccDatas()
	{
		parentModel = new AccDataModel();
		return (long) databaseAccess.delete(parentModel);
	}

	@Deprecated
	public List<ContentValues> getAccData()
	{
		parentModel = new AccDataModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DM", "SELECT AccData");
		return results;
	}

	@Deprecated
	public boolean exportAccData(String fileName)
	{
		parentModel = new AccDataModel();
		Cursor cursor = databaseAccess.selectCursor(parentModel);
		boolean result = databaseAccess.exportToCSV(cursor, fileName);
		Log.d("DM", "EXPORT AccDataModel to " + fileName + ".CSV successfully " + result);
		return result;
	}

	// TRIP DATA VIEW

	public List<ContentValues> getTripData()
	{
		parentModel = new TripDataView();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DM", "SELECT TripData");
		return results;
	}

	public boolean exportTripData(String filename)
	{
		parentModel = new TripDataView();
		Cursor cursor = databaseAccess.selectCursor(parentModel);
		boolean result = databaseAccess.exportToCSV(cursor, filename);
		Log.d("DM", "EXPORT TripData success "  + result);
		return result;
	}

	// GPS DATA

	@Deprecated
	public boolean addGpsData(GpsDataEntity gpsDataEntity)
	{
		Log.d("DB", "INSERT accData trip_id " + gpsDataEntity.tripId + " time_stamp " + gpsDataEntity.timeStamp);
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

	@Deprecated
	public boolean deleteGpsData(GpsDataEntity gpsDataEntity)
	{
		Log.d("DB", "DELETE id " + gpsDataEntity.tripId);
		parentModel = new GpsDataModel(gpsDataEntity);
		parentModel.setWhereClause(AccDataModel.AccDataNames.TRIP_ID + "=" + gpsDataEntity.tripId);
		long success = databaseAccess.delete(parentModel);
		return success > 0;
	}

	@Deprecated
	public long deleteAllGpsDatas()
	{
		parentModel = new GpsDataModel();
		return (long) databaseAccess.delete(parentModel);
	}

	@Deprecated
	public List<ContentValues> getGpsData()
	{
		parentModel = new GpsDataModel();
		List<ContentValues> results = databaseAccess.select(parentModel);
		Log.d("DM", "SELECT AccData");
		return results;
	}

	@Deprecated
	public boolean exportGpsData(String fileName)
	{
		parentModel = new GpsDataModel();
		Cursor cursor = databaseAccess.selectCursor(parentModel);
		boolean result = databaseAccess.exportToCSV(cursor, fileName);
		Log.d("DM", "EXPORT GpsDataModel to " + fileName + ".CSV successfully " + result);
		return result;
	}
}
