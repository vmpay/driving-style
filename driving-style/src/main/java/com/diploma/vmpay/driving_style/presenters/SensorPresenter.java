package com.diploma.vmpay.driving_style.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
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
	private final static String LOG_TAG = "SensorPresenter";

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
		Log.v(LOG_TAG, "onAccData: x " + x + "y " + y + "z " + z);
	}

	@Override
	public void onLocationChanged(Location location)
	{
		Log.v(LOG_TAG, "onLocationChanged: location" + location.toString());
	}

	@Override
	public void onLastKnownLocation(Location location)
	{
		Log.v(LOG_TAG, "onLastKnownLocation: location " + location.toString());
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

	public void enableGpsDialog(final Activity activity)
	{
		if(!locationSensor.isAvailable())
		{
			Toast.makeText(activity, activity.getResources().getText(R.string.enable_gps), Toast.LENGTH_SHORT).show();
			Log.v(LOG_TAG, "alert dialog");
			final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(activity.getResources().getText(R.string.enable_gps_dialog))
					.setCancelable(false)
					.setPositiveButton(activity.getResources().getText(R.string.yes), new DialogInterface.OnClickListener()
					{
						public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id)
						{
							Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							activity.startActivity(intent);
							dialog.dismiss();
						}
					})
					.setNegativeButton(activity.getResources().getText(R.string.no), new DialogInterface.OnClickListener()
					{
						public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id)
						{
							dialog.dismiss();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
		}
	}
}
