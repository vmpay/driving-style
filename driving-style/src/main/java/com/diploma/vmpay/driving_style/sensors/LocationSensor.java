package com.diploma.vmpay.driving_style.sensors;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.ContextWrapper;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;
import com.diploma.vmpay.driving_style.interfaces.ILocationListener;
import com.diploma.vmpay.driving_style.utils.ListenerList;

import java.util.Date;


/**
 * Created by Andrew on 14.03.2016.
 */
public class LocationSensor
{
	private final String LOG_TAG = "LocationSensor";

	private LocationManager locationManager;
	private TextView tvEnabledGPS, tvStatusGPS, tvLocationGPS;
	private Context context;
	private boolean recordingFlag = false;
	private long trip_id = -1;
	private DatabaseAccess databaseAccess;
	private ContextWrapper contextWrapper;
	private ListenerList<ILocationListener> locationListenerListenerList;

	public LocationSensor(ContextWrapper contextWrapper, ListenerList<ILocationListener> locationListenerListenerList)
	{
		this.contextWrapper = contextWrapper;
		this.locationListenerListenerList = locationListenerListenerList;

		locationManager = (LocationManager) contextWrapper.getContext().getSystemService(Context.LOCATION_SERVICE);
	}

	@Deprecated
	public LocationSensor(Context context, TextView tvStatusGPS, TextView tvLocationGPS)
	{
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.context = context;
		this.tvLocationGPS = tvLocationGPS;
		this.tvStatusGPS = tvStatusGPS;
		databaseAccess = new DatabaseAccess(this.context);
	}

	/**
	 * Launches location sensor
	 * @param minTime in ms, time between updating position
	 * @param minDistance in m, distance between updating position
	 */

	public void start(long minTime, float minDistance)
	{
		if(ActivityCompat.checkSelfPermission(contextWrapper.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				contextWrapper.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			Log.v(LOG_TAG, "start: Application has no permission to acquire GPS data");
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
		}
		else
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					minTime, minDistance, gpsLocationListener);
		}
	}

	@Deprecated
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
				1000 * 1, 1, gpsLocationListener);
		isGpsAvailable();
		return true;
	}

	public void finish()
	{
		if(ActivityCompat.checkSelfPermission(contextWrapper.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				contextWrapper.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			Log.v(LOG_TAG, "finish: Application has no permission to acquire GPS data");
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
		}
		else
		{
			locationManager.removeUpdates(gpsLocationListener);
		}
	}

	private LocationListener gpsLocationListener = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
		{
			for(ILocationListener listener : locationListenerListenerList.getListCopy())
			{
				listener.onLocationChanged(location);
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle bundle)
		{
			if(provider.equals(LocationManager.GPS_PROVIDER))
			{
				String result = context.getResources().getString(R.string.available);
				switch(status)
				{
					case 0:
						result = context.getResources().getString(R.string.unavailable);
						break;
					case 1:
						result = context.getResources().getString(R.string.temporarily_unavailable);
						break;
					case 2:
						result = context.getResources().getString(R.string.available);
						break;
				}
				for(ILocationListener listener : locationListenerListenerList.getListCopy())
				{
					listener.onStatusChanged(result);
				}
			}
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			if(provider.equals(LocationManager.GPS_PROVIDER))
			{
				for(ILocationListener listener : locationListenerListenerList.getListCopy())
				{
					listener.onProviderEnabled();
				}
				if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
						!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
						context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
				{
					Log.v(LOG_TAG, "finish: Application has no permission to acquire GPS data");
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
				}
				else
				{
					Location location = locationManager.getLastKnownLocation(provider);
					for(ILocationListener listener : locationListenerListenerList.getListCopy())
					{
						listener.onLastKnownLocation(location);
					}
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			if(provider.equals(LocationManager.GPS_PROVIDER))
			{
				for(ILocationListener listener : locationListenerListenerList.getListCopy())
				{
					listener.onProviderDisabled();
				}
			}
		}
	};

	@Deprecated
	private LocationListener locationListener = new LocationListener()
	{

		@Override
		public void onLocationChanged(Location location)
		{
			showLocation(location);
			if(recordingFlag)
			{
				databaseAccess.insert(new GpsDataModel(trip_id, new Date().getTime(),
						location.getLatitude(), location.getLongitude(), location.getAltitude(),
						location.getSpeed()));
			}
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			isGpsAvailable();
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

	private String formatLocation(Location location)
	{
		if(location == null)
		{
			return "";
		}
		return String.format(
				" %1$.4f\n %2$.4f\n %3$.4f",
				location.getLatitude(), location.getLongitude(), location.getAltitude());
	}

	@Deprecated
	private void isGpsAvailable()
	{
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Toast.makeText(context, "Enable GPS service, please.", Toast.LENGTH_SHORT).show();
			context.startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	public boolean isAvailable()
	{
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
