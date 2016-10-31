package com.diploma.vmpay.driving_style.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import com.diploma.vmpay.driving_style.database.dbmodels.ParentModel;

import java.util.List;

/**
 * Created by Andrew on 03.05.2016.
 */
public interface IDatabaseClient
{
	void openDatabase();

	void closeDatabase();

	long insert(ParentModel databaseModel);

	void asyncInsert(ParentModel databaseModel);

//	DbOperationStatus insertOrUpdate(ParentModel databaseModel);

	long update(ParentModel databaseModel);

	int delete(ParentModel databaseModel);

	List<ContentValues> select(ParentModel databaseModel);

	Cursor selectCursor(ParentModel parentModel);

//	List<ContentValues> select(ParentModel databaseModel, int limit);

	void exportAsyncToCSV(Cursor cursor, String fileName);

	void dropDatabase();
}
