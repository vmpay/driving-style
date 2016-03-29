package com.diploma.vmpay.driving_style.database.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.diploma.vmpay.driving_style.database.dbmodels.AccDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.ParentModel;
import com.diploma.vmpay.driving_style.database.dbmodels.TripDataView;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase)
		{
			sqLiteDatabase.execSQL(TripModel.TripNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(AccDataModel.AccDataNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(TripDataView.TripDataNames.CREATE_TABLE);
			sqLiteDatabase.execSQL(GpsDataModel.GpsDataNames.CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
		{
			//TODO: refactor if we need to save previous data
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
		} catch(SQLException e)
		{
			e.printStackTrace();
		} catch(RuntimeException e)
		{
			e.printStackTrace();
		}
		return success;
	}

	public long update(ParentModel parentModel)
	{
		long success = database.update(parentModel.getTableName(), parentModel.getInsert(),
				parentModel.getWhereClause(), null);
		return success;
	}

	public int delete(ParentModel parentModel)
	{
		int success = database.delete(parentModel.getTableName(), parentModel.getWhereClause(), null);
		return success;
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
	 * @param cursor data to export into @link{fileName}
	 * @param fileName name of file to export into
	 * @return
	 */
	public boolean exportToCSV(Cursor cursor, String fileName) {
		ContextWrapper contextWrapper = new ContextWrapper(mContext);
		String path = contextWrapper.getFilesDir() + File.separator + fileName + ".csv";
		Log.d("DB", "Path: " + path);
		File file = new File(path);
		try {
			if (!file.exists()) {
				if (!file.createNewFile()) {
					return false;
				}
			}
			CSVWriter writer = new CSVWriter(new FileWriter(path));
			int columnCount = cursor.getColumnCount();
			String[] columnNames = cursor.getColumnNames();
			writer.writeNext(columnNames);
			if (cursor.getCount() == 0) {
				writer.close();
				cursor.close();
				return false;
			}
			cursor.moveToFirst();
			do {
				String[] rows = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					switch (cursor.getType(i)) {
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
			} while (cursor.moveToNext());
			writer.close();
			cursor.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			cursor.close();
			return false;
		}
	}
}
