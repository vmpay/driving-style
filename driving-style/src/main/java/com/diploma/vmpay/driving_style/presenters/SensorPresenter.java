package com.diploma.vmpay.driving_style.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.menu.MainMenuFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.menu.ScenarioDialog;
import com.diploma.vmpay.driving_style.activities.main.fragments.settings.SettingsFragment;
import com.diploma.vmpay.driving_style.controller.ActualUserWrapper;
import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;
import com.diploma.vmpay.driving_style.database.dbentities.GpsDataEntity;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.interfaces.IAccelerometerListener;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.diploma.vmpay.driving_style.interfaces.ILocationListener;
import com.diploma.vmpay.driving_style.sensors.AccelerometerSensor;
import com.diploma.vmpay.driving_style.sensors.LocationSensor;
import com.diploma.vmpay.driving_style.utils.ModelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 07/09/2016.
 */
public class SensorPresenter implements ILocationListener, IAccelerometerListener
{
	private final static String LOG_TAG = "SensorPresenter";
	private final static long SCENARIO_TIME_MILIS = 30_000;

	private ActualUserWrapper actualUserWrapper;
	private IDatabaseClient databaseClient;
	private AccelerometerSensor accelerometerSensor;
	private LocationSensor locationSensor;

	private MainMenuFragment mainMenuFragment;

	//------------DIALOGS------------
	private ScenarioDialog scenarioDialog;
	private SettingsFragment settingsFragment;


	private List<AccDataEntity> accDataEntityList = new ArrayList<>();
	private List<GpsDataEntity> gpsDataEntityList = new ArrayList<>();
	private AppConstants.ScenarioType type;
	private long startTime = 0;
	private double alpha = 0.8;

	public SensorPresenter(ActualUserWrapper actualUserWrapper, IDatabaseClient databaseClient, AccelerometerSensor accelerometerSensor, LocationSensor locationSensor)
	{
		this.actualUserWrapper = actualUserWrapper;
		this.databaseClient = databaseClient;
		this.accelerometerSensor = accelerometerSensor;
		this.locationSensor = locationSensor;
	}


	@Override
	public void onAccData(double x, double y, double z)
	{
		Log.v(LOG_TAG, "onAccData: x " + x + "y " + y + "z " + z);
		if (!type.equals(AppConstants.ScenarioType.UNKNOWN))
		{
			if (accDataEntityList.size() > 0 && System.currentTimeMillis() - accDataEntityList.get(0).timeStamp > SCENARIO_TIME_MILIS)
			{
				accDataEntityList.remove(accDataEntityList.get(0));
			}
			accDataEntityList.add(new AccDataEntity(System.currentTimeMillis(), x, y, z));
		}
	}

	@Override
	public void onLocationChanged(Location location)
	{
		Log.v(LOG_TAG, "onLocationChanged: location" + location.toString());
		if (!type.equals(AppConstants.ScenarioType.UNKNOWN))
		{
			if (gpsDataEntityList.size() > 0 && System.currentTimeMillis() - gpsDataEntityList.get(0).timeStamp > SCENARIO_TIME_MILIS)
			{
				gpsDataEntityList.remove(gpsDataEntityList.get(0));
			}
			gpsDataEntityList.add(new GpsDataEntity(System.currentTimeMillis(), location));
		}
	}

	@Override
	public void onLastKnownLocation(Location location)
	{
		Log.v(LOG_TAG, "onLastKnownLocation: location " + location.toString());
	}

	@Override
	public void onStatusChanged(LocationSensor.GpsStatus status)
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

	public void enableGpsDialog(final Activity activity)
	{
		if(!locationSensor.isAvailable())
		{
			Toast.makeText(activity, activity.getResources().getText(R.string.enable_gps), Toast.LENGTH_SHORT).show();
			Log.v(LOG_TAG, "alert dialog");
			final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(activity.getResources().getText(R.string.enable_gps_dialog))
					.setCancelable(false)
					.setPositiveButton(activity.getResources().getText(R.string.yes), new DialogInterface.OnClickListener()
					{
						public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id)
						{
							Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							activity.startActivity(intent);
							dialog.dismiss();
						}
					})
					.setNegativeButton(activity.getResources().getText(R.string.no), new DialogInterface.OnClickListener()
					{
						public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id)
						{
							dialog.dismiss();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
		}
	}

	public void setMainMenuFragment(@Nullable MainMenuFragment mainMenuFragment)
	{
		this.mainMenuFragment = mainMenuFragment;
	}

	public void setScenarioDialog(@Nullable ScenarioDialog scenarioDialog)
	{
		this.scenarioDialog = scenarioDialog;
	}

	public void setScenarioType(AppConstants.ScenarioType type)
	{
		//TODO: update model or ???
		this.type = type;

	}

	public boolean isGpsAvailable()
	{
		return locationSensor.isAvailable();
	}

	public void startSensors()
	{
		startTime = System.currentTimeMillis();
		locationSensor.start(1000, 1);
		accelerometerSensor.start(SensorManager.SENSOR_DELAY_UI, alpha);
	}

	public void stopSensors()
	{
		locationSensor.finish();
		accelerometerSensor.finish();
	}

	public void saveScenario()
	{
		TripModel tripModel = new TripModel(actualUserWrapper.getActualUser().getId());
		tripModel.setStartTime(startTime);
		tripModel.setFinishTime(System.currentTimeMillis());
		tripModel.setTripType(AppConstants.TripType.SCENARIO);
		tripModel.setScenarioType(type);
		tripModel.setMark(0.0);

		long tripId = databaseClient.insert(tripModel);

		ModelUtils.saveAccEntityList(databaseClient, tripId, accDataEntityList);
		ModelUtils.saveGpsEntityList(databaseClient, tripId, gpsDataEntityList);
	}

	public void clearLists()
	{
		accDataEntityList.clear();
		gpsDataEntityList.clear();
		type = AppConstants.ScenarioType.UNKNOWN;
	}

	public void setSettingsFragment(SettingsFragment settingsFragment)
	{
		this.settingsFragment = settingsFragment;
	}

	public void setAlpha(double alpha)
	{
		this.alpha = alpha;
	}
}
