package com.diploma.vmpay.driving_style.interfaces;

import android.location.Location;

/**
 * Created by Andrew on 07/09/2016.
 */
public interface ILocationListener
{
	void onLocationChanged(Location location);

	void onLastKnownLocation(Location location);

	void onStatusChanged(String status);

	void onProviderEnabled();

	void onProviderDisabled();

	class DummyLocationListener implements  ILocationListener
	{

		@Override
		public void onLocationChanged(Location location)
		{
		}

		@Override
		public void onLastKnownLocation(Location location)
		{
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
}
