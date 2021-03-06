package com.diploma.vmpay.driving_style.database.dbmodels;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 16.04.2016.
 */
public class UserModel extends ParentModel
{
	private String login;
	private String name = "";
	private String password;
	private double averageMark;
	private long lastConnectionTimestamp;

	public static class UserNames
	{
		public final static String ID = ParentModel.ID;
		public final static String LOGIN = "login";
		public final static String NAME = "name";
		public final static String PASSWORD = "password";
		public final static String AVERAGE_MARK = "average_mark";
		public final static String LAST_CONNECTION_TIMESTAMP = "last_connection_timestamp";

		public final static String TABLENAME = "user_table";
		public final static String CREATE_TABLE = "CREATE TABLE " + TABLENAME +
				" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				LOGIN + " TEXT UNIQUE NOT NULL, " +
				NAME + " TEXT NOT NULL, " +
				PASSWORD + " TEXT NOT NULL, " +
				AVERAGE_MARK + " REAL NOT NULL, " +
				LAST_CONNECTION_TIMESTAMP + " INTEGER NOT NULL);";
	}

	public UserModel()
	{
		tableName = UserNames.TABLENAME;
		columns = new String[] { UserNames.ID, UserNames.LOGIN, UserNames.NAME, UserNames.PASSWORD,
				UserNames.AVERAGE_MARK, UserNames.LAST_CONNECTION_TIMESTAMP };
	}

	public UserModel(String login, String password, double averageMark)
	{
		this();
		this.login = login;
		this.password = password;
		this.averageMark = averageMark;
	}

	public static List<UserModel> buildFromContentValuesList(List<ContentValues> contentValuesList)
	{
		List<UserModel> userModelList = new ArrayList<>();
		for(ContentValues contentValues : contentValuesList)
		{
			userModelList.add(buildFromContentValues(contentValues));
		}
		return userModelList;
	}

	public static UserModel buildFromContentValues(ContentValues contentValues)
	{
		UserModel userModel = new UserModel();

		userModel.setId(contentValues.getAsLong(UserNames.ID));
		userModel.setLogin(contentValues.getAsString(UserNames.LOGIN));
		userModel.setName(contentValues.getAsString(UserNames.NAME));
		userModel.setPassword(contentValues.getAsString(UserNames.PASSWORD));
		userModel.setAverageMark(contentValues.getAsDouble(UserNames.AVERAGE_MARK));
		userModel.setAverageMark(contentValues.getAsLong(UserNames.LAST_CONNECTION_TIMESTAMP));

		return userModel;
	}

	@Override
	public ContentValues getInsert()
	{
		contentValues = new ContentValues();
		contentValues.put(UserNames.LOGIN, login);
		contentValues.put(UserNames.NAME, name.equals("") ? login : name);
		contentValues.put(UserNames.PASSWORD, password);
		contentValues.put(UserNames.AVERAGE_MARK, averageMark);
		contentValues.put(UserNames.LAST_CONNECTION_TIMESTAMP, lastConnectionTimestamp);
		return contentValues;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public double getAverageMark()
	{
		return averageMark;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setAverageMark(double averageMark)
	{
		this.averageMark = averageMark;
	}

	public long getLastConnectionTimestamp()
	{
		return lastConnectionTimestamp;
	}

	public void setLastConnectionTimestamp(long lastConnectionTimestamp)
	{
		this.lastConnectionTimestamp = lastConnectionTimestamp;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
