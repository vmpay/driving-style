package com.diploma.vmpay.driving_style.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 28.02.2016.
 */
public class AccelerometerFragment  extends Fragment
{
	private final String LOG_TAG = "TestActivity";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() AccelerometerFragment");
		View v = inflater.inflate(R.layout.accelerometer_fragment, container, false);
		//View v = inflater.inflate(R.layout.accelerometer_fragment, container, false);

		return v;
	}
}
