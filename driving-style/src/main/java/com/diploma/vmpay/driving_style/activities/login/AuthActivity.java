package com.diploma.vmpay.driving_style.activities.login;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.login.fragments.LoginFragment;

public class AuthActivity extends AppCompatActivity
{

	private final String LOG_TAG = "AuthActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

		Log.d(LOG_TAG, "AuthActivity: onCreate()");
		//Fragment loginFragment = new LoginFragment();
		LoginFragment loginFragment = new LoginFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragmentLoginActivity, loginFragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d(LOG_TAG, "AuthActivity: onResume()");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d(LOG_TAG, "AuthActivity: onPause()");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Log.d(LOG_TAG, "AuthActivity: onStop()");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d(LOG_TAG, "AuthActivity: onDestroy()");
	}

	@Override
	public void onBackPressed()
	{
		Log.d(LOG_TAG, "onCreate() AuthActivity: onBackPressed BackStackCount = " + getFragmentManager().getBackStackEntryCount());
		if(getFragmentManager().getBackStackEntryCount() >= 1)
		{
			Log.d(LOG_TAG, "onCreate() MainActivity: onBackPressed count > 1");
			//getFragmentManager().popBackStackImmediate();
			getFragmentManager().popBackStack();
			getFragmentManager().beginTransaction().commit();
		}
		else
		{
			Log.d(LOG_TAG, "onCreate() MainActivity: onBackPressed count <= 1");
			//handle finish
			//finish(); // Closes app
			super.onBackPressed();
		}
	}
}
