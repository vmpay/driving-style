package com.diploma.vmpay.driving_style.activities.test;

import android.content.ContentValues;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.ExportDialog;
import com.diploma.vmpay.driving_style.database.dbentities.TripEntity;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;
import com.diploma.vmpay.driving_style.activities.main.fragments.ScenarioFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.AccelerometerFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.GpsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
	private final String LOG_TAG = "TestActivity";

//	private TextView tvDbRecording;
	private Button btnStart, btnStop, btnExport;
	private List<ContentValues> tripDataView;
	private DatabaseManager databaseManager;
	private AccelerometerFragment accelerometerFragment;
	private GpsFragment gpsFragment;
	private ScenarioFragment scenarioFragment;
	private FragmentTransaction fragmentTransaction;
	private SimpleDateFormat simpleDateFormat;
	private Date startDate, finishDate;
	private TripEntity tripEntity;
	private long trip_id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "TestActivity onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		//TODO: check sensors availability

//		tvDbRecording = (TextView) findViewById(R.id.tvDatabaseRecording);
		btnStart = (Button) findViewById(R.id.btnStartRecording);
		btnStop = (Button) findViewById(R.id.btnStopRecording);
		btnExport = (Button) findViewById(R.id.btnExport);

//		tvDbRecording.setClickable(true);

//		tvDbRecording.setOnClickListener(this);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnExport.setOnClickListener(this);
		databaseManager = new DatabaseManager(this);
		simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS_dd-MM-yyyy");

		scenarioFragment = new ScenarioFragment();
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
				btnExport.setEnabled(false);
				startDate = new Date();
				tripEntity = new TripEntity(0, simpleDateFormat.format(startDate), simpleDateFormat.format(startDate), -1);
				trip_id = databaseManager.addTrip(tripEntity);
				accelerometerFragment.startRecording(trip_id);
				gpsFragment.startRecording(trip_id);
//				fragmentTransaction = getSupportFragmentManager().beginTransaction();
//				fragmentTransaction.replace(R.id.llScenario, scenarioFragment, "ScenarioFragment");
//				fragmentTransaction.commit();
				break;
			case R.id.btnStopRecording:
				Log.d(LOG_TAG, "TA: Finish recording");
				btnExport.setEnabled(true);
				finishDate = new Date();
				tripEntity = new TripEntity(0, simpleDateFormat.format(startDate), simpleDateFormat.format(finishDate), -1);
				databaseManager.updateTrip(trip_id, tripEntity);
				accelerometerFragment.stopRecording();
				gpsFragment.stopRecording();
//				fragmentTransaction = getSupportFragmentManager().beginTransaction();
//				fragmentTransaction.remove(scenarioFragment);
//				fragmentTransaction.commit();
				break;
//			case R.id.tvDatabaseRecording:
//				tripDataView = databaseManager.getAllTrips();
//				for (int i = 0; i < tripDataView.size(); i++)
//				{
//					Log.d(LOG_TAG, " trip_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.ID) +
//							" user_id " + tripDataView.get(i).getAsLong(TripModel.TripNames.USER_ID) +
//							" start_time " + tripDataView.get(i).getAsString(TripModel.TripNames.START_TIME) +
//							" finish_time " + tripDataView.get(i).getAsString(TripModel.TripNames.FINISH_TIME) +
//							" mark " + tripDataView.get(i).getAsLong(TripModel.TripNames.MARK));
//				}
//				tripDataView = databaseManager.getTripData();
//				for (int i = 0; i < tripDataView.size(); i++)
//				{
//					Log.d(LOG_TAG, "TA: i = " + i +
//							" trip_id " +tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ID) +
//							" user_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.USER_ID) +
//							" start_time " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.START_TIME) +
//							" finish_time " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.FINISH_TIME) +
//							" mark " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.MARK) +
//							//" acc_id " + tripDataView.get(i).getAsLong(TripDataView.TripDataNames.ACC_ID) +
//							" time_stamp " + tripDataView.get(i).getAsString(TripDataView.TripDataNames.TIME_STAMP) +
//							" acc_x " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_X) +
//							" acc_Y " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_Y) +
//							" acc_Z " + tripDataView.get(i).getAsDouble(TripDataView.TripDataNames.ACC_Z));
//				}
//				Log.d(LOG_TAG, "exporting success " + databaseManager.exportTripData());
//				tripDataView = databaseManager.getGpsData();
//				for (int i = 0; i < tripDataView.size(); i++)
//				{
//					Log.d(LOG_TAG, " trip_id " + tripDataView.get(i).getAsLong(GpsDataModel.GpsDataNames.ID) +
//							" time_stamp " + tripDataView.get(i).getAsString(GpsDataModel.GpsDataNames.TIME_STAMP) +
//							" latitude " + tripDataView.get(i).getAsDouble(GpsDataModel.GpsDataNames.LATITUDE) +
//							" longitude " + tripDataView.get(i).getAsDouble(GpsDataModel.GpsDataNames.LONGITUDE) +
//							" altitude " + tripDataView.get(i).getAsDouble(GpsDataModel.GpsDataNames.ALTITUDE) +
//							" speed " + tripDataView.get(i).getAsFloat(GpsDataModel.GpsDataNames.SPEED));
//				}
//				break;
			case R.id.btnExport:
//				Log.d(LOG_TAG, "exporting success " + databaseManager.exportTripData());
				fragmentTransaction = getSupportFragmentManager().beginTransaction();
				ExportDialog exportDialog = new ExportDialog();
				exportDialog.show(fragmentTransaction, "ExportingDialog");

				break;
		}
	}
}
