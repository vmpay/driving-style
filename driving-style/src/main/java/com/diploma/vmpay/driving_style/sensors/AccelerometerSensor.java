package com.diploma.vmpay.driving_style.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.database.dbentities.AccDataEntity;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.abs;

/**
 * Created by Andrew on 28.02.2016.
 */
public class AccelerometerSensor implements SensorEventListener
{
	private final String LOG_TAG = "SensorDataLog";

	private SensorManager mSensorManager;
	private Sensor mSensor;
	private String list = "";
	private double gravity[] = {0,0,0}, linear_acceleration[] = {0,0,0};
	private double alpha = (double)0.8;
	private Context context;
	private TextView textView;
	private DatabaseManager databaseManager;
	private AccDataEntity accDataEntity;
	private long trip_id = -1;
	private boolean recordingFlag = false, sensorListenerFlag = false;

	AccelerometerSensor(double alpha, Context context, TextView textView)
	{
		this.alpha = alpha;
		this.context = context;
		this.textView = textView;
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		// Use the accelerometer.
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		else{
			// Sorry, there are no accelerometers on your device.
			// You can't play this game.
			//TODO: delete this stuff after checking
			Log.d(LOG_TAG, "Sorry, there are no accelerometers on your device.");
		}
		databaseManager = new DatabaseManager(context);
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		// Isolate the force of gravity with the low-pass filter.
		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

		// Remove the gravity contribution with the high-pass filter.
		linear_acceleration[0] = event.values[0] - gravity[0];
		linear_acceleration[1] = event.values[1] - gravity[1];
		linear_acceleration[2] = event.values[2] - gravity[2];

		list = "";
		if (linear_acceleration[0]<0)
			list += "-\t";
		else
			list += "\t";
		list += abs(linear_acceleration[0]);
		if (linear_acceleration[1]<0)
			list += "\n-\t";
		else
			list += "\n\t";
		list += abs(linear_acceleration[1]);
		if (linear_acceleration[2]<0)
			list += "\n-\t";
		else
			list += "\n\t";
		list += abs(linear_acceleration[2]);
		//Log.d(LOG_TAG, list);
		textView.setText(list);
		if (recordingFlag)
		{
			accDataEntity = new AccDataEntity(trip_id, new SimpleDateFormat("HH:mm:ss:SSS_dd-MM-yyyy").format(new Date()),
					linear_acceleration[0], linear_acceleration[1], linear_acceleration[2]);
			databaseManager.addAccData(accDataEntity);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}


	public void Start(long trip_id)
	{
		this.trip_id = trip_id;
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
		sensorListenerFlag = true;
	}

	public void Stop()
	{
		if (recordingFlag)
		{
			recordingFlag = false;
			Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show();
		}
		mSensorManager.unregisterListener(this);
		sensorListenerFlag = false;
		Log.d(LOG_TAG, "AccSensor: unregisterListener");

	}

	public boolean StartRecording()
	{
		if (sensorListenerFlag)
		{
			recordingFlag = true;
			return true;
		} else
		{
			Toast.makeText(context, "Turn on acc sensor first", Toast.LENGTH_SHORT).show();
			//Snackbar.make(null, "Snackbar test", Snackbar.LENGTH_SHORT).show();
			return false;
		}
	}

	public void StopRecording()
	{
		recordingFlag = false;
	}
}
