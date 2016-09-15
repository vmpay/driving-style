package com.diploma.vmpay.driving_style.activities.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.MainFragmentPagerAdapter;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.controller.ContextOwner;
import com.diploma.vmpay.driving_style.sensors.AccelerometerSensor;
import com.diploma.vmpay.driving_style.sensors.LocationSensor;


/**
 * Created by Andrew on 11.02.2016.
 */
public class StartActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
{
	private final String LOG_TAG = "StartActivity";

	ViewPager viewPager;
	PagerAdapter pagerAdapter;
	TextView tvStepNumber;
	String[] stepnumbers;

	private AppController appController = AppController.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() StartActivity");
		super.onCreate(savedInstanceState);
		appController.setUp(getApplicationContext(), ContextOwner.MAIN_ACTIVITY, hashCode());
		setContentView(R.layout.activity_start);

		tvStepNumber = (TextView) findViewById(R.id.tvStepNumber);
		viewPager = (ViewPager) findViewById(R.id.vpMain);

		pagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);

		viewPager.addOnPageChangeListener(this);

		stepnumbers = getResources().getStringArray(R.array.step_number);
		tvStepNumber.setText(stepnumbers[0]);
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	@Override
	public void onPageSelected(int position)
	{
		Log.d(LOG_TAG, "onPageSelected, position = " + position);
		tvStepNumber.setText(stepnumbers[position]);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}
}
