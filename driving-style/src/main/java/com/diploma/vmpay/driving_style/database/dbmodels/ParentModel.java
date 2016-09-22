package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

/**
 * Created by Andrew on 01.03.2016.
 */
public abstract class ParentModel
{
	protected String tableName;
	protected String[] columns;
	protected String whereClause = null;
	protected ContentValues contentValues;

	private long id;
	public static final String ID = "id";

	public String getTableName()
	{
		return tableName;
	}

	public String[] getColumns()
	{
		return columns;
	}

	public String getWhereClause()
	{
		return whereClause;
	}

	public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}

	public ContentValues getContentValues()
	{
		return contentValues;
	}

	public void setContentValues(ContentValues contentValues) { this.contentValues = contentValues; }

	public abstract ContentValues getInsert();

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setThisIdClause()
	{
		whereClause = ID + "=" + id;
	}
}
