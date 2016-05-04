package com.diploma.vmpay.driving_style.activities.login.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;

/**
 * Created by Andrew on 10.02.2016.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener
{

	private final String LOG_TAG = "AuthActivity";

	private EditText etLogin, etPsw1, etPsw2;
	private Button btnSingUp;
	private DatabaseAccess databaseAccess;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() RegistrationFragment");
		View v = inflater.inflate(R.layout.registration_fragment, container, false);

		etLogin = (EditText) v.findViewById(R.id.email);
		etPsw1 = (EditText) v.findViewById(R.id.password);
		etPsw2 = (EditText) v.findViewById(R.id.repeat_password);

		btnSingUp = (Button) v.findViewById(R.id.btnSignUp);
		btnSingUp.setOnClickListener(this);

		databaseAccess = new DatabaseAccess(getActivity());

		return v;
	}

	@Override
	public void onClick(View v)
	{
		attemptSignUp();
	}

	private void attemptSignUp()
	{
	    /*if (mAuthTask != null) {
            return;
        }*/

		// Reset errors.
		etLogin.setError(null);
		etPsw1.setError(null);
		etPsw2.setError(null);

		// Store values at the time of the login attempt.
		String email = etLogin.getText().toString();
		String password1 = etPsw1.getText().toString();
		String password2 = etPsw2.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if(TextUtils.isEmpty(password1) || !isPasswordValid(password1) || !password1.equals(password2))
		{
			etPsw1.setError(getString(R.string.error_invalid_password));
			focusView = etPsw1;
			cancel = true;
		}

		// Check for a valid email address.
		if(TextUtils.isEmpty(email))
		{
			etLogin.setError(getString(R.string.error_field_required));
			focusView = etLogin;
			cancel = true;
		}
		else if(!isEmailValid(email))
		{
			etLogin.setError(getString(R.string.error_invalid_email));
			focusView = etLogin;
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

			UserModel userModel = new UserModel(email, password1, -1);
			if (databaseAccess.insert(userModel)<0)
			{
				etLogin.setError(getString(R.string.error_invalid_same_email));
				focusView = etLogin;
				focusView.requestFocus();
			}
			else
			{
				Toast.makeText(getActivity(), "Sign up successfully", Toast.LENGTH_SHORT).show();
				getFragmentManager().popBackStack();
				getFragmentManager().beginTransaction().commit();
			}
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
