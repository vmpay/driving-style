package com.diploma.vmpay.driving_style;

/**
 * Created by Andrew on 03.05.2016.
 */
public class AppConstants
{
	public static class ExportFileNames
	{
		public static final String USER = "User";
		public static final String TRIP = "Trip";
		public static final String ACCELEROMETER = "Acc";
		public static final String GPS = "Gps";
	}

	public static class SharedPreferencesNames
	{
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
	}

	public static class FacebookNames
	{
		public static final String USER_FRIENDS_PERMISSION = "user_friends";
		public static final String USER_EMAIL_PERMISSION = "email";
	}

	public static class DateFormat
	{
		public static final String DATE_AND_HOUR = "yyyy-MM-dd'T'HH:mm:ss";
		public static final String DATE = "yyyy-MM-dd";
		public static final String HOUR = "HH:mm";
	}

	public enum TripType
	{
		UNKNOWN,
		PRACTICE,
		EXAM,
		SCENARIO
	}

	public enum ScenarioType
	{
		UNKNOWN,
		BRAKING,
		TURNING,
		CHANGING_LANE
	}
}
