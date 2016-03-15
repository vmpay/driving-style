package com.diploma.vmpay.driving_style.test;

import android.content.ContentValues;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.TripDataView;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;
import com.diploma.vmpay.driving_style.sensors.AccelerometerFragment;
import com.diploma.vmpay.driving_style.sensors.GpsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
	private final String LOG_TAG = "TestActivity";

	private TextView tvDbRecording;
	private Button btnStart, btnStop;
	private List<ContentValues> tripDataView;
	private DatabaseManager databaseManager;
	private AccelerometerFragment accelerometerFragment;
	private GpsFragment gpsFragment;
	private FragmentTransaction fragmentTransaction;
	private SimpleDateFormat simpleDateFormat;
	private Date startDate, finishDate;
	private TripEntity tripEntity;
	private long trip_id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() TestActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		//TODO: check sensors availability

		tvDbRecording = (TextView) findViewById(R.id.tvDatabaseRecording);
		btnStart = (Button) findViewById(R.id.btnStartRecording);
		btnStop = (Button) findViewById(R.id.btnStopRecording);

		tvDbRecording.setClickable(true);

		tvDbRecording.setOnClickListener(this);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		databaseManager = new DatabaseManager(this);
		simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS_dd-MM-yyyy");

		accelerometerFragment = new AccelerometerFragment();
		gpsFragment = new GpsFragment();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.llAccelerometerFragment, accelerometerFragment, "AccelerometerFragment");
		fragmentTransaction.replace(R.id.llGpsFragment, gpsFragment, "GpsFragment");
		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View v)
	{

		switch(v.getId())
		{
			case R.id.btnStartRecording:
				Log.d(LOG_TAG, "TA: Start recording");
				startDate = new Date();
				tripEntity = new TripEntity(0, simpleDateFormat.format(startDate), simpleDateFormat.format(startDate), -1);
				trip_id = databaseManager.addTrip(tripEntity);
				accelerometerFragment.StartRecording(trip_id);
				break;
			case R.id.btnStopRecording:
				Log.d(LOG_TAG, "TA: Finish recording");
				finishDate = new Date();
				tripEntity = new TripEntity(0, simpleDateFormat.format(startDate), simpleDateFormat.format(finishDate), -1);
				databaseManager.updateTrip(trip_id, tripEntity);
				accelerometerFragment.StopRecording();
				break;
			case R.id.tvDatabaseRecording:
				tripDataView = databaseManager.getAllTrips();
				for (int i = 0; i < tripDataView.size(); i++)
				{
					Log.d(LOG_TAG, " trip_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.ID) +
							" user_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.USER_ID) +
							" start_time " + tripDataView.get(i).getAsString(TripModel.TripNames.START_TIME) +
							" finish_time " + tripDataView.get(i).getAsString(TripModel.TripNames.FINISH_TIME) +
							" mark " + tripDataView.get(i).getAsLong(TripModel.TripNames.MARK));
				}
				break;
		}
	}
}
