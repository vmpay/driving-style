package com.diploma.vmpay.drivingstyle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class SensorData extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private final String LOG_TAG = "SensorDataLog";
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView tvWelcome, tvAlpha;
    private GraphView graph;
    private Button btnStart, btnFinish, btnAnalyze, btnDB;
    private String list = "";
    private float gravity[] = {0,0,0}, linear_acceleration[] = {0,0,0};
    private float alpha = (float)0.8;
    private easyDB testDB;
    private int iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        Intent intent = getIntent();
        alpha = intent.getFloatExtra("alpha", (float)0.8);

        graph = (GraphView) findViewById(R.id.gvGraph);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvAlpha = (TextView) findViewById(R.id.tvAlpha);
        btnAnalyze = (Button) findViewById(R.id.btnAnalyze);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnDB = (Button) findViewById(R.id.btnDB);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        btnStart.setOnClickListener(this);
        btnAnalyze.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnDB.setOnClickListener(this);

        tvAlpha.setText("Alpha = " + alpha + "\n");
        // Use the accelerometer.
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else{
            // Sorry, there are no accelerometers on your device.
            // You can't play this game.
        }
        iterator = 0;
        testDB = new easyDB(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


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
        tvWelcome.setText(list);
        Log.d(LOG_TAG, "Befor insert iterator = " + iterator);
        long id = testDB.InsertRow(1,iterator,linear_acceleration);
        Log.d(LOG_TAG, "after insert id = " + id);
        iterator++;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View v) {
        int PKid, tripID, number, accelerationx, accelerationy, accelerationz, count, i=0;
        switch (v.getId())
        {
            case R.id.btnAnalyze:
                Cursor inputDB = testDB.ReadDB();
                if (inputDB.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                     PKid = inputDB.getColumnIndex("_id");
                     tripID = inputDB.getColumnIndex("tripid");
                     number = inputDB.getColumnIndex("number");
                     accelerationx = inputDB.getColumnIndex("accelerationx");
                     accelerationy = inputDB.getColumnIndex("accelerationy");
                     accelerationz = inputDB.getColumnIndex("accelerationz");
                     count = inputDB.getColumnIndex("_count");
                    DataPoint[] DataPointX = new DataPoint[inputDB.getCount()];
                    DataPoint[] DataPointY = new DataPoint[inputDB.getCount()];
                    DataPoint[] DataPointZ = new DataPoint[inputDB.getCount()];
                    Log.d(LOG_TAG, "before do: row count = " +  inputDB.getCount());
                    do {
                        //Log.d(LOG_TAG, "inside do: _ID =" +  inputDB.getInt(PKid));
                        DataPointX[i] = new DataPoint(inputDB.getInt(number)+i, inputDB.getFloat(accelerationx));
                        DataPointY[i] = new DataPoint(inputDB.getInt(number)+i, inputDB.getFloat(accelerationy));
                        DataPointZ[i] = new DataPoint(inputDB.getInt(number)+i, inputDB.getFloat(accelerationz));
                        //Log.d(LOG_TAG, "DataPoint[i].toString()=" + DataPointX[i].toString());
                        i++;
                    } while (inputDB.moveToNext());
                    Log.d(LOG_TAG, "After do");
                    LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<DataPoint>(DataPointX);
                    LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<DataPoint>(DataPointY);
                    LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<DataPoint>(DataPointZ);
                    Log.d(LOG_TAG, "After series init");
                    seriesX.setTitle("x");
                    graph.addSeries(seriesX);
                    seriesY.setTitle("y");
                    seriesY.setColor(Color.GREEN);
                    graph.addSeries(seriesY);
                    seriesZ.setTitle("z");
                    seriesZ.setColor(Color.RED);
                    graph.addSeries(seriesZ);
                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                    Log.d(LOG_TAG, "After addSeries");
                } else
                    Log.d(LOG_TAG, "0 rows");
                inputDB.close();
                break;
            case R.id.btnDB:
                this.deleteDatabase("DrivingStyle.db");
                /*
                //Log.d (LOG_TAG, "DB init successful");
                float linear_acceleration_test[] = {1,2,3};
                long id = testDB.InsertRow(1,1,linear_acceleration_test);
                //Log.d (LOG_TAG, "Row insert successful id = " + id);
                Cursor c = testDB.ReadDB();
                //Log.d (LOG_TAG, "Read to cursor successful");
                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                     PKid = c.getColumnIndex("_id");
                     tripID = c.getColumnIndex("tripid");
                     number = c.getColumnIndex("number");
                     accelerationx = c.getColumnIndex("accelerationx");
                     accelerationy = c.getColumnIndex("accelerationy");
                     accelerationz = c.getColumnIndex("accelerationz");

                    //Log.d (LOG_TAG, "GetColumnIndex was successful");
                    do {
                        //Log.d (LOG_TAG, "DO entered");
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(PKid) +
                                        ", TripID = " + c.getInt(tripID) +
                                        ", " + c.getInt(number) +
                                        ". X: " + c.getFloat(accelerationx) +
                                        ". Y: " + c.getFloat(accelerationy) +
                                        ". Z: " + c.getFloat(accelerationz));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                        //Log.d (LOG_TAG, "DO exited");
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();*/
                break;
            case R.id.btnFinish:
                mSensorManager.unregisterListener(this);
                //this.deleteDatabase("DrivingStyle.db");
                break;
            case R.id.btnStart:
                mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            default:
                break;
        }
    }
}
