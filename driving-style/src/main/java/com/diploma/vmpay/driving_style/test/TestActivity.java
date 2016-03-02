package com.diploma.vmpay.driving_style.test;

import android.content.ContentValues;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.TripDataView;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;
import com.diploma.vmpay.driving_style.sensors.AccelerometerFragment;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
	private final String LOG_TAG = "TestActivity";

	private Button btnStart;
	private List<ContentValues> tripDataView;
	private DatabaseManager databaseManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() TestActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		//TODO: check sensors availability

		btnStart = (Button) findViewById(R.id.btnStartRecording);

		btnStart.setOnClickListener(this);

		AccelerometerFragment accelerometerFragment = new AccelerometerFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.llAccelerometerFragment, accelerometerFragment, "SettingsMainFragment");
		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnStartRecording:
				databaseManager = new DatabaseManager(this);
				tripDataView = databaseManager.getTripData();
				for (int i = 0; i < tripDataView.size(); i++)
				{
					Log.d(LOG_TAG, "TA: trip_id " + //tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ID) +
							" user_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.USER_ID) +
							" start_time " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.START_TIME) +
							" finish_time " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.FINISH_TIME) +
							"\nmark " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.MARK) +
							//" acc_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_ID) +
							" time_stamp " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.TIME_STAMP) +
							"\nacc_x " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_X) +
							" acc_Y " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_Y) +
							" acc_Z " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_Z));
				}
				break;
		}
	}
}
