package com.diploma.vmpay.driving_style.sensors;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;

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
	private ToggleButton tbLaunch;
	private double alpha = 0.8;
	private AccelerometerSensor accelerometerSensor;
	private SimpleDateFormat start_time, finish_time;
	private DatabaseManager databaseManager;
	private TripEntity tripEntity;
	private List<ContentValues> resultTable;
	private long trip_id = -1;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() AccelerometerFragment");
		View v = inflater.inflate(R.layout.accelerometer_fragment, container, false);

		tvAlpha = (TextView) v.findViewById(R.id.tvAlpha);
		tvAccValues = (TextView) v.findViewById(R.id.tvAccValues);
		sbAlpha = (SeekBar) v.findViewById(R.id.sbAlpha);
		tbLaunch = (ToggleButton) v.findViewById(R.id.tbAccelerometer);

		sbAlpha.setOnSeekBarChangeListener(this);
		tbLaunch.setOnCheckedChangeListener(this);

		tvAlpha.setText("0.80");

		//accelerometerSensor = new AccelerometerSensor(alpha, getActivity(), tvAccValues);

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
		databaseManager = new DatabaseManager(getActivity());
		if(isChecked)
		{
			accelerometerSensor = new AccelerometerSensor(alpha, getActivity(), tvAccValues);
			start_time = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy");
			tripEntity = new TripEntity(0, start_time.format(new Date()), start_time.format(new Date()), -1);
			databaseManager.addTrip(tripEntity);
			resultTable = databaseManager.getTrip(start_time.format(new Date()));
			if (resultTable.size() != 0)
				trip_id = resultTable.get(0).getAsLong(TripModel.TripNames.ID);
			accelerometerSensor.Start(trip_id);
			sbAlpha.setEnabled(false);
		}
		else
		{
			accelerometerSensor.Stop();
			finish_time = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy");
			tripEntity = new TripEntity(0, start_time.format(new Date()), finish_time.format(new Date()), -1);
			databaseManager.addTrip(tripEntity);
			sbAlpha.setEnabled(true);
		}
	}

}
