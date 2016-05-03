package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;
import com.diploma.vmpay.driving_style.sensors.AccelerometerSensor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrew on 28.02.2016.
 */
public class AccelerometerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
		CompoundButton.OnCheckedChangeListener
{
	private final String LOG_TAG = "TestActivity";

	private TextView tvAlpha, tvAccValues;
	private SeekBar sbAlpha;
	public ToggleButton tbLaunch;
	private double alpha = 0.8;
	private AccelerometerSensor accelerometerSensor;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "AccelerometerFragment onCreateView()");
		View v = inflater.inflate(R.layout.accelerometer_fragment, container, false);

		tvAlpha = (TextView) v.findViewById(R.id.tvAlpha);
		tvAccValues = (TextView) v.findViewById(R.id.tvAccValues);
		sbAlpha = (SeekBar) v.findViewById(R.id.sbAlpha);
		tbLaunch = (ToggleButton) v.findViewById(R.id.tbAccelerometer);

		sbAlpha.setOnSeekBarChangeListener(this);
		tbLaunch.setOnCheckedChangeListener(this);

		tvAlpha.setText("0.80");

		accelerometerSensor = new AccelerometerSensor(alpha, getActivity(), tvAccValues);

		return v;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		alpha = (double) progress / 100;
		tvAlpha.setText("" + alpha);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		Log.d(LOG_TAG, "AccFragment: onCheckedChanged isChecked = " + isChecked);
		if(isChecked)
		{
			accelerometerSensor = new AccelerometerSensor(alpha, getActivity(), tvAccValues);
			accelerometerSensor.start();
			sbAlpha.setEnabled(false);
			getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		else
		{
			accelerometerSensor.stop();
			sbAlpha.setEnabled(true);
			GpsFragment gpsFragment = (GpsFragment) getFragmentManager().findFragmentById(R.id.llGpsFragment);
			if (!gpsFragment.tbLaunch.isChecked())
			{
				getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}

	public void startRecording(long trip_id)
	{
		accelerometerSensor.startRecording(trip_id);
	}

	public void stopRecording()
	{
		accelerometerSensor.stopRecording();
	}

	@Override
	public void onDestroy()
	{
		accelerometerSensor.stop();
	}
}
