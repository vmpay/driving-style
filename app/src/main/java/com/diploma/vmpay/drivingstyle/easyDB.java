package com.diploma.vmpay.drivingstyle;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class easyDB {

    final String LOG_TAG = "easyDBLog";
    private DBHelper DbHelper;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " ("
                    + FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FeedEntry.COLUMN_NAME_TRIP_ID + " INTEGER,"
                    + FeedEntry.COLUMN_NAME_NUMBER + " INTEGER,"
                    + FeedEntry.COLUMN_NAME_ACCELERATION_X + " FLOAT,"
                    + FeedEntry.COLUMN_NAME_ACCELERATION_Y + " FLOAT,"
                    + FeedEntry.COLUMN_NAME_ACCELERATION_Z + " FLOAT"
                    + ");";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    easyDB(Context context){
        //Log.d(LOG_TAG, "конструктор класса");
        DbHelper = new DBHelper(context);
    }

    public long InsertRow(int tripid, int number, float linear_acceleration[]){
        // Gets the data repository in write mode
        Log.d (LOG_TAG, "InsertRow runs..." + tripid + number + linear_acceleration[0]);
        SQLiteDatabase db =  DbHelper.getWritableDatabase();
        Log.d (LOG_TAG, "InsertRow: DB connection created");
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TRIP_ID, tripid);
        values.put(FeedEntry.COLUMN_NAME_NUMBER, number);
        values.put(FeedEntry.COLUMN_NAME_ACCELERATION_X, linear_acceleration[0]);
        values.put(FeedEntry.COLUMN_NAME_ACCELERATION_Y, linear_acceleration[1]);
        values.put(FeedEntry.COLUMN_NAME_ACCELERATION_Z, linear_acceleration[2]);
        //values.put(FeedEntry.COLUMN_NAME_CONTENT, content);
        Log.d (LOG_TAG, "InsertRow: row is formatted");
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null, //FeedEntry.COLUMN_NAME_NULLABLE,
                values);
        Log.d (LOG_TAG, "InsertRow finished successful");
        //DBHelper.close();
        return newRowId;
    }

    public Cursor ReadDB(){
        //Log.d (LOG_TAG, "ReadDB runs...");
        SQLiteDatabase db = DbHelper.getReadableDatabase();
        //Log.d (LOG_TAG, "ReadDB: DB connection created");

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TRIP_ID,
                FeedEntry.COLUMN_NAME_NUMBER,
                FeedEntry.COLUMN_NAME_ACCELERATION_X,
                FeedEntry.COLUMN_NAME_ACCELERATION_Y,
                FeedEntry.COLUMN_NAME_ACCELERATION_Z,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_TRIP_ID + " DESC";
        //Log.d (LOG_TAG, "ReadDB: query is formatted");
        //Cursor c = db.query("mytable", null, null, null, null, null, null);
        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,//selection,                                // The columns for the WHERE clause
                null,//selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Log.d (LOG_TAG, "ReadDB: query is successful");

        /*if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int PKid = c.getColumnIndex("_id");
            int tripID = c.getColumnIndex("tripid");
            int number = c.getColumnIndex("number");
            int accelerationx = c.getColumnIndex("accelerationx");
            int accelerationy = c.getColumnIndex("accelerationy");
            int accelerationz = c.getColumnIndex("accelerationz");

            Log.d (LOG_TAG, "GetColumnIndex was successful: " + PKid + tripID + number + accelerationx +accelerationy + accelerationz);
            do {
                Log.d (LOG_TAG, "DO entered");
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(PKid) +
                                ", TripID = " + c.getInt(tripID) +
                                ", " + c.getInt(number) +
                                ". X: " + c.getFloat(accelerationx) +
                                ". Y: " + c.getFloat(accelerationy) +
                                ". Z: " + c.getFloat(accelerationz));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
                Log.d (LOG_TAG, "DO exited");
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");*/


        //DBHelper.close();
        return c;
    }

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "points";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_TRIP_ID = "tripid";
        public static final String COLUMN_NAME_ACCELERATION_X = "accelerationx";
        public static final String COLUMN_NAME_ACCELERATION_Y = "accelerationy";
        public static final String COLUMN_NAME_ACCELERATION_Z = "accelerationz";
    }

    class DBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "DrivingStyle.db";

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //Log.d(LOG_TAG, "конструктор суперкласса");
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            //Log.d(LOG_TAG, "Удаляем старую таблицу");
            //db.execSQL(SQL_DELETE_ENTRIES);
            //Log.d(LOG_TAG, "Создаем новую таблицу");
            db.execSQL(SQL_CREATE_ENTRIES);
            /*db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");*/
            Log.d(LOG_TAG, "--- onCreate database --- successful");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
