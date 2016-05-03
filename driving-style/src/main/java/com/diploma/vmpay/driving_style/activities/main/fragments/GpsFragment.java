package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.AccelerometerFragment;
import com.diploma.vmpay.driving_style.sensors.GpsSensor;

/**
 * Created by Andrew on 14.03.2016.
 */
public class GpsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
	private final String LOG_TAG = "TestActivity";

	private TextView tvStatusGps, tvLocationGps;
	public ToggleButton tbLaunch;
	private GpsSensor gpsSensor;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "GpsFragment onCreateView()");
		View v = inflater.inflate(R.layout.gps_fragment, container, false);

		tvStatusGps = (TextView) v.findViewById(R.id.tvGpsStatus);
		tvLocationGps = (TextView) v.findViewById(R.id.tvGpsValues);

		tbLaunch = (ToggleButton) v.findViewById(R.id.tbGPS);

		tbLaunch.setOnCheckedChangeListener(this);

		gpsSensor = new GpsSensor(getActivity(), tvStatusGps, tvLocationGps);

		return v;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if(isChecked)
		{
			gpsSensor.start();
			getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}else
		{
			gpsSensor.stop();
			AccelerometerFragment accelerometerFragment = (AccelerometerFragment) getFragmentManager()
					.findFragmentById(R.id.llAccelerometerFragment);
			if (!accelerometerFragment.tbLaunch.isChecked())
			{
				getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}

	public void startRecording(long trip_id)
	{
		gpsSensor.startRecording(trip_id);
	}

	public void stopRecording()
	{
		gpsSensor.stopRecording();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		gpsSensor.stop();
	}
}
