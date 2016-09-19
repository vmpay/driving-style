package com.diploma.vmpay.driving_style.activities.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.main.fragments.ViewPagerFragment;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.controller.ContextOwner;


/**
 * Created by Andrew on 11.02.2016.
 */
public class StartActivity extends AppCompatActivity
{
	private final String LOG_TAG = "StartActivity";

	private AppController appController = AppController.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreate() StartActivity");
		super.onCreate(savedInstanceState);
		appController.setUp(getApplicationContext(), ContextOwner.MAIN_ACTIVITY, hashCode());
		setContentView(R.layout.activity_start);

		setupTabs();
	}

	private void setupTabs()
	{
		ViewPagerFragment tutorialFragment = new ViewPagerFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragmentStartActivity, tutorialFragment);
//		fragmentTransaction.addToBackStack("MainMenu");
		fragmentTransaction.commit();
	}
}
