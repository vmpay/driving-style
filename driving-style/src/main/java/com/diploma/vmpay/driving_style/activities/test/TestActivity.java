package com.diploma.vmpay.driving_style.activities.test;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.ExportDialog;
import com.diploma.vmpay.driving_style.database.dbentities.AsyncExportEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;
import com.diploma.vmpay.driving_style.activities.main.fragments.ScenarioFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.AccelerometerFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.GpsFragment;
import com.diploma.vmpay.driving_style.interfaces.DatabaseInterface;

import java.util.Date;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, DatabaseInterface
{
	private final String LOG_TAG = "TestActivity";

	private Button btnStart, btnStop, btnExport;
	private DatabaseAccess databaseAccess;
	private TripModel tripModel;
	private AccelerometerFragment accelerometerFragment;
	private GpsFragment gpsFragment;
	private ScenarioFragment scenarioFragment;
	private FragmentTransaction fragmentTransaction;
	private Date startDate, finishDate;
	private long trip_id = -1;
	private ProgressBar pbTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "TestActivity onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		//TODO: check sensors availability

		btnStart = (Button) findViewById(R.id.btnStartRecording);
		btnStop = (Button) findViewById(R.id.btnStopRecording);
		btnExport = (Button) findViewById(R.id.btnExport);
		pbTransaction = (ProgressBar) findViewById(R.id.pbTransaction);

		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnExport.setOnClickListener(this);
		btnStop.setEnabled(false);

		databaseAccess = new DatabaseAccess(this);
		databaseAccess.setCallback(this);

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
				pbTransaction.setVisibility(View.VISIBLE);
				btnExport.setEnabled(false);
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				startDate = new Date();
				//TODO: update user id
				long userId = 0;
				tripModel = new TripModel(userId, startDate.getTime(), 1, -1);
				trip_id = databaseAccess.insert(tripModel);
				tripModel.setId(trip_id);
				accelerometerFragment.startRecording(trip_id);
				gpsFragment.startRecording(trip_id);
				break;
			case R.id.btnStopRecording:
				Log.d(LOG_TAG, "TA: Finish recording");
				finishDate = new Date();
				//TODO: update user id & mark
				tripModel.setFinishTime(finishDate.getTime());
				tripModel.setWhereClause(TripModel.TripNames.ID + "=" + tripModel.getId());
				databaseAccess.asyncInsert(tripModel);
				accelerometerFragment.stopRecording();
				gpsFragment.stopRecording();
				break;
			case R.id.btnExport:
				fragmentTransaction = getSupportFragmentManager().beginTransaction();
				ExportDialog exportDialog = new ExportDialog();
				exportDialog.show(fragmentTransaction, "ExportingDialog");
				break;
		}
	}

	@Override
	public void onAsyncInsertFinished()
	{
		Log.d(LOG_TAG, "TestActivity interface received");
		pbTransaction.setVisibility(View.GONE);
		btnExport.setEnabled(true);
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}

	@Override
	public void onAsyncExportFinished(AsyncExportEntity asyncExportEntity)
	{
	}

	@Override
	public void onAsyncExportStarted()
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		databaseAccess.removeCallback();
	}
}
