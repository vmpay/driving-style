package com.diploma.vmpay.driving_style.database.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbentities.AsyncExportEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.AccDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.ParentModel;
import com.diploma.vmpay.driving_style.database.dbmodels.TripDataView;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.interfaces.DatabaseInterface;
import com.opencsv.CSVWriter;


/**
 * Created by Andrew on 01.03.2016.
 */
public class DatabaseAccess
{
	public final static String DATABASE_NAME = "driving_style_database";
	private int DATABASE_VERSION;

	public DatabaseAccess(Context mContext)
	{
		this.mContext = mContext;
		this.DATABASE_VERSION = 2;
		openDatabase();
	}

	private DbHelper dbHelper;
	public SQLiteDatabase database;
	private Context mContext;
	private DatabaseInterface mCallback;

	class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase)
		{
			sqLiteDatabase.execSQL(UserModel.UserNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(TripModel.TripNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(AccDataModel.AccDataNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(TripDataView.TripDataNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(GpsDataModel.GpsDataNames.CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
		{
			//TODO: refactor if we need to save previous data
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserModel.UserNames.TABLENAME);
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TripModel.TripNames.TABLENAME);
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccDataModel.AccDataNames.TABLENAME);
			sqLiteDatabase.execSQL("DROP VIEW IF EXISTS " + TripDataView.TripDataNames.TABLENAME);
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GpsDataModel.GpsDataNames.TABLENAME);
			onCreate(sqLiteDatabase);
		}

	}

	public void openDatabase()
	{
		dbHelper = new DbHelper(mContext);
		database = dbHelper.getWritableDatabase();
	}

	public void closeDatabase()
	{
		dbHelper.close();
	}

	public long insert(ParentModel parentModel)
	{
		ContentValues contentValues = parentModel.getInsert();
		long success = 0;
		try
		{
			success = database.insertWithOnConflict(parentModel.getTableName(), null,
					contentValues, SQLiteDatabase.CONFLICT_IGNORE);
		} catch(RuntimeException e)
		{
			e.printStackTrace();
		}
		return success;
	}

	public void asyncInsert(ParentModel parentModel)
	{
		new AsyncInsert().execute(parentModel);
	}

	public long update(ParentModel parentModel)
	{
		return (long) database.update(parentModel.getTableName(), parentModel.getInsert(),
				parentModel.getWhereClause(), null);
	}

	public int delete(ParentModel parentModel)
	{
		return database.delete(parentModel.getTableName(), parentModel.getWhereClause(), null);
	}

	public List<ContentValues> select(ParentModel parentModel)
	{
		Cursor cursor = database.query(parentModel.getTableName(), parentModel.getColumns(),
				parentModel.getWhereClause(), null, null, null, null);
		List<ContentValues> results = null;

		if(cursor != null)
		{
			results = new ArrayList<>();
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				ContentValues contentValues = new ContentValues();
				for(int i = 0; i < parentModel.getColumns().length; i++)
				{
					int columnIndex = cursor.getColumnIndex(parentModel.getColumns()[i]);
					String value = cursor.getString(columnIndex);
					if(value != null)
					{
						contentValues.put(parentModel.getColumns()[i], value);
					}
				}
				results.add(contentValues);
			}
			cursor.close();
		}
		else
		{
			Log.d("DB", "null cursor");
		}
		return results;
	}

	public Cursor selectCursor(ParentModel parentModel)
	{
		return database.query(parentModel.getTableName(), parentModel.getColumns(),
				parentModel.getWhereClause(), null, null, null, null);
	}

	/**
	 * Export data from db table to CSV file
	 *
	 * @param cursor   data to export into @link{fileName}
	 * @param fileName name of file to export into
	 * @return boolean result of the transaction
	 */
	public boolean exportToCSV(Cursor cursor, String fileName)
	{
		String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Download" + File.separator + fileName + ".csv";
		Log.d("DB", "Path: " + path);
		File file = new File(path);
		try
		{
			if(file.exists())
			{
				if(!file.createNewFile())
				{
					return false;
				}
			}
			if(cursor.getCount() == 0)
			{
				cursor.close();
				return false;
			}
			CSVWriter writer = new CSVWriter(new FileWriter(path));
			int columnCount = cursor.getColumnCount();
			String[] columnNames = cursor.getColumnNames();
			writer.writeNext(columnNames);
			cursor.moveToFirst();
			do
			{
				String[] rows = new String[columnCount];
				for(int i = 0; i < columnCount; i++)
				{
					switch(cursor.getType(i))
					{
						case Cursor.FIELD_TYPE_INTEGER:
							rows[i] = String.valueOf(cursor.getInt(i));
							break;
						case Cursor.FIELD_TYPE_FLOAT:
							rows[i] = String.valueOf(cursor.getFloat(i));
							break;
						case Cursor.FIELD_TYPE_STRING:
							rows[i] = cursor.getString(i);
							break;
					}
				}
				writer.writeNext(rows);
			} while(cursor.moveToNext());
			writer.close();
			cursor.close();
			return true;
		} catch(IOException e)
		{
			e.printStackTrace();
			cursor.close();
			return false;
		}
	}

	public void exportAsyncToCSV(Cursor cursor, String fileName)
	{
		AsyncExportEntity asyncExportEntity = new AsyncExportEntity(cursor, fileName);
		AsyncExport asyncExport = new AsyncExport();
		asyncExport.execute(asyncExportEntity);
	}

	private class AsyncInsert extends AsyncTask<ParentModel, Void, ParentModel>
	{

		@Override
		protected ParentModel doInBackground(ParentModel... params)
		{
			if(insert(params[0]) < 0)
			{
				params[0].setWhereClause(params[0].ID + "=" + params[0].getId());
				update(params[0]);
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(ParentModel parentModel)
		{
			if (parentModel instanceof TripModel)
			{
				if (mCallback != null)
				{
					mCallback.onAsyncInsertFinished();
				}
			}
		}
	}

	private class AsyncExport extends AsyncTask<AsyncExportEntity, Void, AsyncExportEntity>
	{
		private Cursor cursor;
		private String fileName;

		@Override
		protected AsyncExportEntity doInBackground(AsyncExportEntity... params)
		{
			cursor = params[0].getCursor();
			fileName = params[0].getFileName();
			String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Download" + File.separator + fileName + ".csv";
			Log.d("DB", "Path: " + path);
			File file = new File(path);
			try
			{
				if(file.exists())
				{
					if(!file.createNewFile())
					{
						return params[0];
					}
				}
				if(cursor.getCount() == 0)
				{
					cursor.close();
					return params[0];
				}
				CSVWriter writer = new CSVWriter(new FileWriter(path));
				int columnCount = cursor.getColumnCount();
				String[] columnNames = cursor.getColumnNames();
				writer.writeNext(columnNames);
				cursor.moveToFirst();
				do
				{
					String[] rows = new String[columnCount];
					for(int i = 0; i < columnCount; i++)
					{
						switch(cursor.getType(i))
						{
							case Cursor.FIELD_TYPE_INTEGER:
								rows[i] = String.valueOf(cursor.getInt(i));
								break;
							case Cursor.FIELD_TYPE_FLOAT:
								rows[i] = String.valueOf(cursor.getFloat(i));
								break;
							case Cursor.FIELD_TYPE_STRING:
								rows[i] = cursor.getString(i);
								break;
						}
					}
					writer.writeNext(rows);
				} while(cursor.moveToNext());
				writer.close();
				cursor.close();
				params[0].setResult(true);
				return params[0];
			} catch(IOException e)
			{
				e.printStackTrace();
				cursor.close();
				return params[0];
			}
		}

		@Override
		protected void onPostExecute(AsyncExportEntity asyncExportEntity)
		{
			if (mCallback != null)
			{
				mCallback.onAsyncExportFinished(asyncExportEntity);
			}
		}
	}

	public void setCallback(DatabaseInterface callback)
	{
		mCallback= callback;
	}

	public void removeCallback()
	{
		mCallback = null;
	}

}
