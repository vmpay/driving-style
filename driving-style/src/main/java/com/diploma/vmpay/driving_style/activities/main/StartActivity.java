package com.diploma.vmpay.driving_style.activities.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.MainMenuFragment;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.controller.ContextOwner;


/**
 * Created by Andrew on 11.02.2016.
 */
public class StartActivity extends AppCompatActivity //implements ViewPager.OnPageChangeListener
{
	private final String LOG_TAG = "StartActivity";

//	private ViewPager viewPager;
//	private PagerAdapter pagerAdapter;
//	private TextView tvStepNumber;
//	private String[] stepnumbers;

	private AppController appController = AppController.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() StartActivity");
		super.onCreate(savedInstanceState);
		appController.setUp(getApplicationContext(), ContextOwner.MAIN_ACTIVITY, hashCode());
		setContentView(R.layout.activity_start);

		MainMenuFragment mainMenuFragment = new MainMenuFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragmentStartActivity, mainMenuFragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
//		tvStepNumber = (TextView) findViewById(R.id.tvStepNumber);
//		viewPager = (ViewPager) findViewById(R.id.vpMain);
//
//		pagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
//		viewPager.setAdapter(pagerAdapter);
//
//		viewPager.addOnPageChangeListener(this);
//
//		stepnumbers = getResources().getStringArray(R.array.step_number);
//		tvStepNumber.setText(stepnumbers[0]);
	}


//	@Override
//	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
//	{
//
//	}
//
//	@Override
//	public void onPageSelected(int position)
//	{
//		Log.d(LOG_TAG, "onPageSelected, position = " + position);
//		tvStepNumber.setText(stepnumbers[position]);
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int state)
//	{
//
//	}
}
