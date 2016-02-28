package com.diploma.vmpay.driving_style.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.support.v4.app.Fragment;
import android.util.Log;

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
	private AccelerometerFragment accelerometerFragment;
	private Context context;

	AccelerometerSensor(double alpha, Context context)
	{
		this.alpha = alpha;
		this.context = context;
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
		accelerometerFragment = new AccelerometerFragment();
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

		list = "Accelerometer\nx=";
		if (linear_acceleration[0]<0)
			list += "-\t";
		else
			list += "\t";
		list += abs(linear_acceleration[0]);
		list += "\ny=";
		if (linear_acceleration[1]<0)
			list += "-\t";
		else
			list += "\t";
		list += abs(linear_acceleration[1]);
		list += "\nz=";
		if (linear_acceleration[2]<0)
			list += "-\t";
		else
			list += "\t";
		list += abs(linear_acceleration[2]);
		Log.d(LOG_TAG, list);
		accelerometerFragment.updateTextView(list);
		//updateTextView(list);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}


	public void Start()
	{
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
	}

	public void Stop()
	{
		mSensorManager.unregisterListener(this);
	}
}
