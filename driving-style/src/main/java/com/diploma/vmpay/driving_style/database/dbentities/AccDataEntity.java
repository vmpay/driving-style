package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 01.03.2016.
 */
@Deprecated
public class AccDataEntity
{
	public long tripId;
	public long timeStamp;
	public double accX;
	public double accY;
	public double accZ;

	public AccDataEntity(long tripId, long timeStamp, double accX, double accY, double accZ)
	{
		this.tripId = tripId;
		this.timeStamp = timeStamp;
		this.accX = accX;
		this.accY = accY;
		this.accZ = accZ;
	}
}
