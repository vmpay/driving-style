package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 01.03.2016.
 */
@Deprecated
public class TripEntity
{
	public long userId;
	public long startTime;
	public long finishTime;
	public double mark;

	public TripEntity(long userId, long startTime, long finishTime, double mark)
	{
		this.userId = userId;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.mark = mark;
	}
}
