package com.diploma.vmpay.drivingstyle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static java.lang.Math.abs;

public class SensorData extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView tvWelcome;
    private String list = "";
    private float gravity[] = {0,0,0}, linear_acceleration[] = {0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //list += mSensorManager.getSensorList(Sensor.TYPE_ALL).toString();
        //tvWelcome.setText(list);

        // Use the accelerometer.
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else{
            // Sorry, there are no accelerometers on your device.
            // You can't play this game.
        }



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = (float) 0.8;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        list = "Accelerometer\nx=" + linear_acceleration[0] + "\ny=" + linear_acceleration[1] + "\nz=" + linear_acceleration[2];
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
        tvWelcome.setText(list);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
