package com.diploma.vmpay.driving_style.activities.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.login.fragments.LoginFragment;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.controller.ContextOwner;

public class AuthActivity extends AppCompatActivity
{

	private final String LOG_TAG = "AuthActivity";
	private final String[] permissions = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		checkPermissions();
		AppController.getInstance().setUp(getApplicationContext(), ContextOwner.AUTHORISING_ACTIVITY, hashCode());
		setContentView(R.layout.activity_auth);

		Log.d(LOG_TAG, "AuthActivity: onCreate()");
		//Fragment loginFragment = new LoginFragment();
		LoginFragment loginFragment = new LoginFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragmentLoginActivity, loginFragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

		// Add code to print out the key hash
//		try
//		{
//			PackageInfo info = getPackageManager().getPackageInfo(
//					"com.diploma.vmpay.driving_style",
//					PackageManager.GET_SIGNATURES);
//			for(Signature signature : info.signatures)
//			{
//				MessageDigest md = MessageDigest.getInstance("SHA");
//				md.update(signature.toByteArray());
//				Log.d(LOG_TAG, "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//			}
//		} catch(PackageManager.NameNotFoundException e)
//		{
//
//		} catch(NoSuchAlgorithmException e)
//		{
//		}
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

	private void checkPermissions()
	{
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this,
				permissions[0]) != PackageManager.PERMISSION_GRANTED
				|| ContextCompat.checkSelfPermission(this,
				permissions[1]) != PackageManager.PERMISSION_GRANTED)
		{

			// Should we show an explanation?
//			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//					permission)) {

			// Show an expanation to the user *asynchronously* -- don't block
			// this thread waiting for the user's response! After the user
			// sees the explanation, try again to request the permission.

//			} else {

			// No explanation needed, we can request the permission.

			ActivityCompat.requestPermissions(this,
					permissions,
					0);
		}

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
//			}
	}
}
