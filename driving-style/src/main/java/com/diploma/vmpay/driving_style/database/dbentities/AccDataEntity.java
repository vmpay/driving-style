package com.diploma.vmpay.driving_style.database.dbentities;

/**
 * Created by Andrew on 01.03.2016.
 */
public class AccDataEntity
{
	public long trip_id;
	public String time_stamp;
	public double acc_x;
	public double acc_y;
	public double acc_z;

	public AccDataEntity(long trip_id, String time_stamp, double acc_x, double acc_y, double acc_z)
	{
		this.trip_id = trip_id;
		this.time_stamp = time_stamp;
		this.acc_x = acc_x;
		this.acc_y = acc_y;
		this.acc_z = acc_z;
	}
}
