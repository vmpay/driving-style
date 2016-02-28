package com.diploma.vmpay.driving_style.test;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.diploma.vmpay.driving_style.R;

public class TestActivity extends AppCompatActivity
{
	private final String LOG_TAG = "TestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() TestActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		AccelerometerFragment accelerometerFragment = new AccelerometerFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.llAccelerometerFragment, accelerometerFragment, "SettingsMainFragment");
		fragmentTransaction.commit();
	}
}
