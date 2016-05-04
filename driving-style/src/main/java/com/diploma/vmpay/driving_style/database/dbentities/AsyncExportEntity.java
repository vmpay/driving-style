package com.diploma.vmpay.driving_style.database.dbentities;

import android.database.Cursor;

/**
 * Created by Andrew on 04.05.2016.
 */
public class AsyncExportEntity
{
	private Cursor cursor;
	private String fileName;
	private boolean result;

	public AsyncExportEntity(Cursor cursor, String fileName)
	{
		this.cursor = cursor;
		this.fileName = fileName;
		this.result = false;
	}

	public Cursor getCursor()
	{
		return cursor;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setResult(boolean result)
	{
		this.result = result;
	}

	public boolean isResult()
	{
		return result;
	}
}