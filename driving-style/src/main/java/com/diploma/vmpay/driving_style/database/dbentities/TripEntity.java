package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 01.03.2016.
 */
public class TripEntity
{
	public long user_id;
	public String start_time;
	public String finish_time;
	public double mark;

	public TripEntity(long user_id, String start_time, String finish_time, double mark)
	{
		this.user_id = user_id;
		this.start_time = start_time;
		this.finish_time = finish_time;
		this.mark = mark;
	}
}
