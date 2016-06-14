package com.diploma.vmpay.driving_style.activities.login.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.activities.test.TestActivity;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrew on 10.02.2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, TextWatcher
{

	private final String LOG_TAG = "AuthActivity";

	private Button btnSignIn, btnSignUp;
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private Switch swRememberMe;
	private ArrayAdapter<CharSequence> adapterEmail;
	private DatabaseAccess databaseAccess;
	private LoginButton loginButton;
	private CallbackManager callbackManager;
	private ProfileTracker profileTracker;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() LoginFragment");
		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
		View v = inflater.inflate(R.layout.login_fragment, container, false);

		swRememberMe = (Switch) v.findViewById(R.id.switchRememberMe);
		btnSignIn = (Button) v.findViewById(R.id.btnSignIn);
		btnSignUp = (Button) v.findViewById(R.id.btnSignUp);

		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);

		adapterEmail = ArrayAdapter.createFromResource(getActivity(),
				R.array.email_autocomplete_full, android.R.layout.simple_dropdown_item_1line);

//        setupActionBar();
		// Set up the login form.
		mEmailView = (AutoCompleteTextView) v.findViewById(R.id.email);
		mEmailView.setAdapter(adapterEmail);
		mEmailView.addTextChangedListener(this);
		//populateAutoComplete();

		mPasswordView = (EditText) v.findViewById(R.id.password);

		databaseAccess = new DatabaseAccess(getActivity());

		SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		String email = sharedPreferences.getString(AppConstants.SharedPreferencesNames.EMAIL, "");
		String password = sharedPreferences.getString(AppConstants.SharedPreferencesNames.PASSWORD, "");

		if (!email.isEmpty() && !password.isEmpty())
		{
			mEmailView.setText(email);
			mPasswordView.setText(password);
			swRememberMe.setChecked(true);
		}

		loginButton = (LoginButton) v.findViewById(R.id.login_button);
		loginButton.setReadPermissions("user_friends");
		// If using in a fragment
		loginButton.setFragment(this);
		// Other app specific specialization

		// Callback registration

		callbackManager = CallbackManager.Factory.create();
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				// App code
				Log.d(LOG_TAG, "onSuccess Token " + loginResult.getAccessToken()
						+ " Permissions" + loginResult.getRecentlyGrantedPermissions());
//				LoginManager.getInstance().logInWithPublishPermissions(
//						getActivity(),  Arrays.asList("public_profile", "user_friends"));
			}

			@Override
			public void onCancel() {
				// App code
				Log.d(LOG_TAG, "onCancel");
			}

			@Override
			public void onError(FacebookException exception) {
				// App code
				Log.d(LOG_TAG, "onError");
			}
		});

		profileTracker = new ProfileTracker() {
			@Override
			protected void onCurrentProfileChanged(
					Profile oldProfile,
					Profile currentProfile) {
				// App code
				if (currentProfile != null)
				{
					Log.d(LOG_TAG, "onCurrentProfileChanged " + currentProfile.getName());
				}
			}
		};

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		profileTracker.stopTracking();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnSignIn:
				Log.d(LOG_TAG, "Toggle Button is on = " + swRememberMe.isChecked());
				attemptLogin();
//				if(swRememberMe.isChecked())
////				if(true)
//				{
//					//Intent intent = new Intent(getActivity(), StartActivity.class);
//					Intent intent = new Intent(getActivity(), TestActivity.class);
//					Log.d(LOG_TAG, "Intent is starting");
//					startActivity(intent);
//					//mEmailView.showDropDown();
//				}
//				else
//				{
//					attemptLogin();
//				}
				break;
			case R.id.btnSignUp:
				RegistrationFragment registrationFragment = new RegistrationFragment();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.fragmentLoginActivity, registrationFragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				break;
		}
	}

	private void attemptLogin()
	{
	    /*if (mAuthTask != null) {
            return;
        }*/

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if(TextUtils.isEmpty(password) || !isPasswordValid(password))
		{
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if(TextUtils.isEmpty(email))
		{
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		else if(!isEmailValid(email))
		{
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if(cancel)
		{
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else
		{
			UserModel userModel = new UserModel(email, password, -1);
			userModel.setWhereClause(UserModel.UserNames.LOGIN + "='" + userModel.getLogin() + "'");
			List<UserModel> userModelList = UserModel.buildFromContentValuesList(
					databaseAccess.select(userModel));
			if (userModelList.size() > 0)
			{
				if (userModelList.get(0).getPassword().equals(userModel.getPassword()))
				{
					SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor ed = sharedPreferences.edit();
					if (swRememberMe.isChecked())
					{
						ed.putString(AppConstants.SharedPreferencesNames.EMAIL, userModel.getLogin());
						ed.putString(AppConstants.SharedPreferencesNames.PASSWORD, userModel.getPassword());
					}
					else
					{
						ed.putString(AppConstants.SharedPreferencesNames.EMAIL, "");
						ed.putString(AppConstants.SharedPreferencesNames.PASSWORD, "");
					}
					ed.commit();
					Intent intent = new Intent(getActivity(), TestActivity.class);
					Log.d(LOG_TAG, "Intent is starting");
					startActivity(intent);
				}
				else
				{
					mPasswordView.setError(getString(R.string.wrong_password));
					focusView = mPasswordView;
					focusView.requestFocus();
				}
			}
			else
			{
				mEmailView.setError(getString(R.string.wrong_email));
				focusView = mEmailView;
				focusView.requestFocus();
			}
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
            /*showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);*/

		}
	}

	private boolean isEmailValid(String email)
	{
		//TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password)
	{
		//TODO: Replace this with your own logic
		return password.length() > 4;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		//Log.d(LOG_TAG, "LoginFragment: beforeTextChange s = " + s);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		//Log.d(LOG_TAG, "LoginFragment: onTextChange s = " + s);
		String string = "";
		if (s.length()>0)
			string = s.toString().substring((s.length()-1));
		Log.d(LOG_TAG, "Last char = " + string);
		if (string.equals("@"))
		{
			Log.d(LOG_TAG, "inside if" + string);
			mEmailView.showDropDown();
		}
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		//Log.d(LOG_TAG, "LoginFragment: afterTextChange s = " + s);
	}
}
