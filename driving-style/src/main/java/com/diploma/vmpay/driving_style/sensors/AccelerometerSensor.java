package com.diploma.vmpay.driving_style.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.controller.ContextWrapper;
import com.diploma.vmpay.driving_style.interfaces.IAccelerometerListener;
import com.diploma.vmpay.driving_style.utils.ListenerList;

import static java.lang.Math.abs;

/**
 * Created by Andrew on 28.02.2016.
 */
public class AccelerometerSensor implements SensorEventListener
{
	private final String LOG_TAG = "AccelerometerSensor";

	private SensorManager mSensorManager;
	private Sensor mSensor;
	private String list = "";
	private double gravity[] = { 0, 0, 0 }, linear_acceleration[] = { 0, 0, 0 };
	private double alpha = (double) 0.8;
	private Context context;
	private TextView textView;
	private long trip_id = -1;
	private boolean recordingFlag = false, sensorListenerFlag = false;

	private ContextWrapper contextWrapper;
	private ListenerList<IAccelerometerListener> accelerometerSensorListenerList;

	public AccelerometerSensor(ContextWrapper contextWrapper, ListenerList<IAccelerometerListener> accelerometerSensorListenerList)
	{
		this.contextWrapper = contextWrapper;
		this.accelerometerSensorListenerList = accelerometerSensorListenerList;

		mSensorManager = (SensorManager) contextWrapper.getContext().getSystemService(Context.SENSOR_SERVICE);

		if(isAvailable())
		{
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
	}

	@Deprecated
	public AccelerometerSensor(double alpha, Context context, TextView textView)
	{
		this.alpha = alpha;
		this.context = context;
		this.textView = textView;
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		// Use the accelerometer.
		if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
		{
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		else
		{
			// Sorry, there are no accelerometers on your device.
			// You can't play this game.
			//TODO: delete this stuff after checking
			Log.d(LOG_TAG, "Sorry, there are no accelerometers on your device.");
		}
//		databaseManager = new DatabaseManager(context);
//		databaseAccess = new DatabaseAccess(context);
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

		for(IAccelerometerListener listener : accelerometerSensorListenerList.getListCopy())
		{
			listener.onAccData(linear_acceleration[0], linear_acceleration[1], linear_acceleration[2]);
		}

//		list = "";
//		if(linear_acceleration[0] < 0)
//		{
//			list += "-\t";
//		}
//		else
//		{
//			list += "\t";
//		}
//		list += String.format(
//				"%1$.4f", abs(linear_acceleration[0]));
//		if(linear_acceleration[1] < 0)
//		{
//			list += "\n-\t";
//		}
//		else
//		{
//			list += "\n\t";
//		}
//		list += String.format(
//				"%1$.4f", abs(linear_acceleration[1]));
//		if(linear_acceleration[2] < 0)
//		{
//			list += "\n-\t";
//		}
//		else
//		{
//			list += "\n\t";
//		}
//		list += String.format(
//				"%1$.4f", abs(linear_acceleration[2]));
//		textView.setText(list);
//		if(recordingFlag)
//		{
//			AccDataModel accDataModel = new AccDataModel(trip_id, new Date().getTime(),
//					linear_acceleration[0], linear_acceleration[1], linear_acceleration[2]);
//			databaseAccess.asyncInsert(accDataModel);
//			AsyncDatabaseAccess asyncDatabaseAccess = new AsyncDatabaseAccess();
//			asyncDatabaseAccess.execute(accDataModel);
//		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}

	@Deprecated
	public void start()
	{
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
		sensorListenerFlag = true;
	}

	public void start(int sensorDelay, double alpha)
	{
		this.alpha = alpha;
		switch(sensorDelay)
		{
			case SensorManager.SENSOR_DELAY_FASTEST:
				mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
				break;
			case SensorManager.SENSOR_DELAY_GAME:
				mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
				break;
			case SensorManager.SENSOR_DELAY_UI:
				mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
				break;
			case SensorManager.SENSOR_DELAY_NORMAL:
				mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
				break;
			default:
				mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
				break;
		}
	}

	public void finish()
	{
		mSensorManager.unregisterListener(this);
	}

	@Deprecated
	public void stop()
	{
		if(recordingFlag)
		{
			recordingFlag = false;
			Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show();
		}
		mSensorManager.unregisterListener(this);
		sensorListenerFlag = false;
		Log.d(LOG_TAG, "AccSensor: unregisterListener");

	}

	public boolean startRecording(long trip_id)
	{
		this.trip_id = trip_id;
		recordingFlag = true;
		return true;
	}

	public void stopRecording()
	{
		recordingFlag = false;
	}

//	public class AsyncDatabaseAccess extends AsyncTask<AccDataModel, Void, Void>
//	{
//
//		@Override
//		protected Void doInBackground(AccDataModel... params)
//		{
//			databaseAccess.insert(params[0]);
//			return null;
//		}
//	}

	public boolean isAvailable()
	{
		return mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
	}
}
