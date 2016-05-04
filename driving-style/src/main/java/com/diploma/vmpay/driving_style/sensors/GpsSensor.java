package com.diploma.vmpay.driving_style.sensors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;

import java.util.Date;


/**
 * Created by Andrew on 14.03.2016.
 */
public class GpsSensor
{
	private LocationManager locationManager;
	private TextView tvEnabledGPS, tvStatusGPS, tvLocationGPS;
	private Context context;
	private boolean recordingFlag = false;
	private long trip_id = -1;
	private DatabaseAccess databaseAccess;

	public GpsSensor(Context context, TextView tvStatusGPS, TextView tvLocationGPS)
	{
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.context = context;
		this.tvLocationGPS = tvLocationGPS;
		this.tvStatusGPS = tvStatusGPS;
		databaseAccess = new DatabaseAccess(this.context);
	}

	public boolean start()
	{
		if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return false;
		}
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000 * 1, 1, locationListener);
		checkEnabled();
		return true;
	}

	public boolean stop()
	{
		if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return false;
		}
		locationManager.removeUpdates(locationListener);
		return true;
	}

	private LocationListener locationListener = new LocationListener()
	{

		@Override
		public void onLocationChanged(Location location)
		{
			showLocation(location);
			if (recordingFlag)
			{
				databaseAccess.insert(new GpsDataModel(trip_id, new Date().getTime(),
						location.getLatitude(), location.getLongitude(), location.getAltitude(),
						location.getSpeed()));
			}
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			checkEnabled();
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
					!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
					Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			{
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			showLocation(locationManager.getLastKnownLocation(provider));
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			if(provider.equals(LocationManager.GPS_PROVIDER))
			{
				switch(status)
				{
					case 0:
						tvStatusGPS.setText(context.getResources().getString(R.string.unavailable));
						break;
					case 1:
						tvStatusGPS.setText(context.getResources().getString(R.string.temporarily_unavailable));
						break;
					case 2:
						tvStatusGPS.setText(context.getResources().getString(R.string.available));
						break;
				}

			}
		}
	};

	private void showLocation(Location location)
	{
		if(location == null)
		{
			return;
		}
		if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
		{
			tvLocationGPS.setText(formatLocation(location));
		}
	}

	private String formatLocation(Location location) {
		if (location == null)
			return "";
		return String.format(
				" %1$.4f\n %2$.4f\n %3$.4f",
				location.getLatitude(), location.getLongitude(), location.getAltitude());
	}

	private void checkEnabled()
	{
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Toast.makeText(context, "Enable GPS service, please.", Toast.LENGTH_SHORT).show();
			context.startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	public void startRecording(long trip_id)
	{
		this.trip_id = trip_id;
		recordingFlag = true;
	}

	public void stopRecording()
	{
		recordingFlag = false;
	}
}
