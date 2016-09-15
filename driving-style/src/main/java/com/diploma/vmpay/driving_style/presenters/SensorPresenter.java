package com.diploma.vmpay.driving_style.presenters;

import android.location.Location;
import android.util.Log;

import com.diploma.vmpay.driving_style.controller.ActualUserWrapper;
import com.diploma.vmpay.driving_style.interfaces.IAccelerometerListener;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.diploma.vmpay.driving_style.interfaces.ILocationListener;
import com.diploma.vmpay.driving_style.sensors.AccelerometerSensor;
import com.diploma.vmpay.driving_style.sensors.LocationSensor;

/**
 * Created by Andrew on 07/09/2016.
 */
public class SensorPresenter implements ILocationListener, IAccelerometerListener
{
	private final static String TAG = "SensorPresenter";

	private ActualUserWrapper actualUserWrapper;
	private IDatabaseClient databaseClient;
	private AccelerometerSensor accelerometerSensor;
	private LocationSensor locationSensor;

	public SensorPresenter(ActualUserWrapper actualUserWrapper, IDatabaseClient databaseClient, AccelerometerSensor accelerometerSensor, LocationSensor locationSensor)
	{
		this.actualUserWrapper = actualUserWrapper;
		this.databaseClient = databaseClient;
		this.accelerometerSensor = accelerometerSensor;
		this.locationSensor = locationSensor;
	}


	@Override
	public void onAccData(double x, double y, double z)
	{
		Log.v(TAG, "onAccData: x " + x + "y " + y + "z " + z);
	}

	@Override
	public void onLocationChanged(Location location)
	{
		Log.v(TAG, "onLocationChanged: location" + location.toString());
	}

	@Override
	public void onLastKnownLocation(Location location)
	{
		Log.v(TAG, "onLastKnownLocation: location " + location.toString());
	}

	@Override
	public void onStatusChanged(String status)
	{

	}

	@Override
	public void onProviderEnabled()
	{

	}

	@Override
	public void onProviderDisabled()
	{

	}
}
