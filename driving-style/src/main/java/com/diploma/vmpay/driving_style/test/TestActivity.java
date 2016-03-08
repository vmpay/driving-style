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
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;
import com.diploma.vmpay.driving_style.sensors.AccelerometerFragment;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
	private final String LOG_TAG = "TestActivity";

	private Button btnStart, btnStop;
	private List<ContentValues> tripDataView;
	private DatabaseManager databaseManager;
	private AccelerometerFragment accelerometerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() TestActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		//TODO: check sensors availability

		btnStart = (Button) findViewById(R.id.btnStartRecording);
		btnStop = (Button) findViewById(R.id.btnStopRecording);

		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		databaseManager = new DatabaseManager(this);

		accelerometerFragment = new AccelerometerFragment();
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
				Log.d(LOG_TAG, "TA: Start recording");
				accelerometerFragment.StartRecording();
				tripDataView = databaseManager.getAllTrips();
				for (int i = 0; i < tripDataView.size(); i++)
				{
					Log.d(LOG_TAG, " trip_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.ID) +
							" user_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.USER_ID) +
							" start_time " + tripDataView.get(i).getAsString(TripModel.TripNames.START_TIME) +
							" finish_time " + tripDataView.get(i).getAsString(TripModel.TripNames.FINISH_TIME) +
							" mark " + tripDataView.get(i).getAsLong(TripModel.TripNames.MARK));
				}
				/*tripDataView = databaseManager.getTripData();
				for (int i = 0; i < tripDataView.size(); i++)
				{
					Log.d(LOG_TAG, "TA: i = " + i +
							//" trip_id " +tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ID) +
							" user_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.USER_ID) +
							" start_time " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.START_TIME) +
							" finish_time " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.FINISH_TIME) +
							" mark " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.MARK) +
							//" acc_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_ID) +
							" time_stamp " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.TIME_STAMP) +
							" acc_x " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_X) +
							" acc_Y " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_Y) +
							" acc_Z " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_Z));
				}*/
				break;
			case R.id.btnStopRecording:
				Log.d(LOG_TAG, "TA: Finish recording");
				accelerometerFragment.StopRecording();
//				tripDataView = databaseManager.getAccData();
//				for (int i = 0; i < tripDataView.size(); i++)
//				{
//					Log.d(LOG_TAG, " acc_id " + tripDataView.get(i).getAsLong(AccDataModel.AccDataNames.ID) +
//							" trip_id " + tripDataView.get(i).getAsLong(AccDataModel.AccDataNames.TRIP_ID) +
//							" time_stamp " + tripDataView.get(i).getAsString(AccDataModel.AccDataNames.TIME_STAMP) +
//							" acc_x " + tripDataView.get(i).getAsDouble(AccDataModel.AccDataNames.ACC_X) +
//							" acc_Y " + tripDataView.get(i).getAsDouble(AccDataModel.AccDataNames.ACC_Y) +
//							" acc_Z " + tripDataView.get(i).getAsDouble(AccDataModel.AccDataNames.ACC_Z));
//				}
				/*tripDataView = databaseManager.getTripData();
				for (int i = 0; i < tripDataView.size(); i++)
				{
					Log.d(LOG_TAG, "TA: i = " + i +
							" trip_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ID) +
							" user_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.USER_ID) +
							" start_time " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.START_TIME) +
							" finish_time " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.FINISH_TIME) +
							" mark " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.MARK) +
							//" acc_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_ID) +
							" time_stamp " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.TIME_STAMP) +
							" acc_x " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_X) +
							" acc_Y " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_Y) +
							" acc_Z " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_Z));
				}*/
				break;
		}
	}
}
