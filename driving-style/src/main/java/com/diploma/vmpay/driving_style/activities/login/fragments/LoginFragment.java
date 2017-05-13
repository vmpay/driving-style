package com.diploma.vmpay.driving_style.activities.login.fragments;


import android.content.Intent;
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
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.presenters.UserLoginPresenter;
import com.diploma.vmpay.driving_style.utils.BugTrackingUtils;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Andrew on 10.02.2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, TextWatcher
{

	private final String LOG_TAG = "AuthActivity";

	private UserLoginPresenter userLoginPresenter;

	private Button btnSignIn, btnSignUp;
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private Switch swRememberMe;
	private ArrayAdapter<CharSequence> adapterEmail;
	private LoginButton loginButton;

	@BindView(R.id.tvVersion) TextView tvVersion;

	private Unbinder unbinder;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "onCreateView() LoginFragment");
		userLoginPresenter = AppController.getInstance().getUserLoginPresenter();
		userLoginPresenter.setLoginFragment(this);

		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
		View v = inflater.inflate(R.layout.login_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);

		swRememberMe = (Switch) v.findViewById(R.id.switchRememberMe);
		btnSignIn = (Button) v.findViewById(R.id.btnSignIn);
		btnSignUp = (Button) v.findViewById(R.id.btnSignUp);

		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);

		adapterEmail = ArrayAdapter.createFromResource(getActivity(),
				R.array.email_autocomplete_full, android.R.layout.simple_dropdown_item_1line);

		// Set up the login form.
		mEmailView = (AutoCompleteTextView) v.findViewById(R.id.email);
		mEmailView.setAdapter(adapterEmail);
		mEmailView.addTextChangedListener(this);

		mPasswordView = (EditText) v.findViewById(R.id.password);

		String email = userLoginPresenter.getLastLogin();
		if(!email.isEmpty())
		{
			mEmailView.setText(email);
			swRememberMe.setChecked(true);
		}

		loginButton = (LoginButton) v.findViewById(R.id.login_button);
		userLoginPresenter.facebookLoginInit(loginButton);

		tvVersion.setText(BugTrackingUtils.getAppVersion());

		return v;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		userLoginPresenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		userLoginPresenter.setLoginFragment(null);
		unbinder.unbind();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnSignIn:
				userLoginPresenter.attemptLogin();
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

	public void attemptLogin()
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
			List<UserModel> userModelList = userLoginPresenter.authorize(email, password);
			if(userModelList.size() > 0)
			{
				if(userModelList.get(0).getPassword().equals(password))
				{
					userLoginPresenter.startMainActivity(userModelList.get(0), swRememberMe.isChecked());
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
		if(s.length() > 0)
		{
			string = s.toString().substring((s.length() - 1));
		}
		Log.d(LOG_TAG, "Last char = " + string);
		if(string.equals("@"))
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
