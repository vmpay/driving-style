package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 15.03.2016.
 */
@Deprecated
public class GpsDataEntity
{
	public long tripId;
	public long timeStamp;
	public double latitude;
	public double longitude;
	public double altitude;
	public float speed;

	public GpsDataEntity(long tripId, long timeStamp, double latitude, double longitude,
	              double altitude, float speed)
	{
		this.tripId = tripId;
		this.timeStamp = timeStamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.speed = speed;
	}
}
