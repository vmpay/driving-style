package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 15.03.2016.
 */
public class GpsDataEntity
{
	public long trip_id;
	public String time_stamp;
	public double latitude;
	public double longitude;
	public double altitude;
	public float speed;

	public GpsDataEntity(long trip_id, String time_stamp, double latitude, double longitude,
	              double altitude, float speed)
	{
		this.trip_id = trip_id;
		this.time_stamp = time_stamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.speed = speed;
	}
}
