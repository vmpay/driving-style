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


/**
 * Created by Andrew on 14.03.2016.
 */
public class GpsSensor
{
	private LocationManager locationManager;
	private TextView tvEnabledGPS, tvStatusGPS, tvLocationGPS;
	private Context context;

	public GpsSensor(Context context, TextView tvStatusGPS, TextView tvLocationGPS)
	{
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.context = context;
		this.tvLocationGPS = tvLocationGPS;
		this.tvStatusGPS = tvStatusGPS;
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
				1000 * 10, 10, locationListener);
//		locationManager.requestLocationUpdates(
//				LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
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
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			checkEnabled();
		}

		@Override
		public void onProviderEnabled(String provider)
		{
//			checkEnabled();
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
//				tvStatusGPS.setText("Status: " + String.valueOf(status));
				switch(status)
				{
					case 0:
						tvStatusGPS.setText("unavailable");
						break;
					case 1:
						tvStatusGPS.setText("temporarily unavailable");
						break;
					case 2:
						tvStatusGPS.setText("available");
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
//			context.startActivity(new Intent(
//					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
//		tvEnabledGPS.setText("Enabled: "
//				+ locationManager
//				.isProviderEnabled(LocationManager.GPS_PROVIDER));
	}


}
