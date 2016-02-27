package com.diploma.vmpay.driving_style.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.test.TestActivity;

/**
 * Created by Andrew on 10.02.2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener
{

	private final String LOG_TAG = "AuthActivity";

	private Button btnSignIn, btnSignUp;
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private Switch swRememberMe;
	private ArrayAdapter<CharSequence> adapterEmail;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() LoginFragment");
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
		//populateAutoComplete();

		mPasswordView = (EditText) v.findViewById(R.id.password);

		return v;
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnSignIn:
				Log.d(LOG_TAG, "Toggle Button is on = " + swRememberMe.isChecked());
				if(swRememberMe.isChecked())
				{
					//Intent intent = new Intent(getActivity(), StartActivity.class);
					Intent intent = new Intent(getActivity(), TestActivity.class);
					Log.d(LOG_TAG, "Intent is starting");
					startActivity(intent);
					//mEmailView.showDropDown();
				}
				else
				{
					attemptLogin();
				}
				break;
			case R.id.btnSignUp:
				Fragment registrationFragment = new RegistrationFragment();
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
}
