package com.diploma.vmpay.driving_style;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.diploma.vmpay.driving_style.login.LoginFragment;

public class AuthActivity extends AppCompatActivity
{

	private final String LOG_TAG = "AuthActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

		Log.d(LOG_TAG, "onCreate() MainActivity");
		//Fragment loginFragment = new LoginFragment();
		LoginFragment loginFragment = new LoginFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragmentLoginActivity, loginFragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
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
