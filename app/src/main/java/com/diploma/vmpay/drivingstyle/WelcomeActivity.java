package com.diploma.vmpay.drivingstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    TextView tvAlphaVal;
    float leftValue = (float) 0.8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnAcc = (Button) findViewById(R.id.btnAccelerometer);
        SeekBar sbAlpha = (SeekBar) findViewById(R.id.sbAlpha);
        tvAlphaVal = (TextView) findViewById(R.id.AlphaValue);

        btnAcc.setOnClickListener(this);
        sbAlpha.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnAccelerometer:
                Intent intent = new Intent(this, SensorData.class);
                intent.putExtra("alpha", leftValue);
                startActivity(intent);
                break;
            default:
                break;
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        leftValue = (float)progress / 100;
        //int rightValue = seekBar.getMax() - progress;
        tvAlphaVal.setText("" + leftValue);
        /*
        // настраиваем вес
        lParams1.weight = leftValue;
        lParams2.weight = rightValue;
        // в текст кнопок пишем значения переменных
        btn1.setText(String.valueOf(leftValue));
        btn2.setText(String.valueOf(rightValue));
        */
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
