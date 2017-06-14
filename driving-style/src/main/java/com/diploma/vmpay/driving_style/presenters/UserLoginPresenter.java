package com.diploma.vmpay.driving_style.presenters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.activities.login.fragments.LoginFragment;
import com.diploma.vmpay.driving_style.activities.main.StartActivity;
import com.diploma.vmpay.driving_style.activities.main.fragments.settings.SettingsFragment;
import com.diploma.vmpay.driving_style.controller.ActualUserWrapper;
import com.diploma.vmpay.driving_style.controller.ContextWrapper;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrew on 08/09/2016.
 */
public class UserLoginPresenter
{
	private static final String LOG_TAG = "UserLoginPresenter";

	private IDatabaseClient databaseClient;
	private ActualUserWrapper actualUserWrapper;
	private ContextWrapper contextWrapper;

	private LoginFragment loginFragment;
	private static SettingsFragment settingsFragment;

	private CallbackManager callbackManager;

	public UserLoginPresenter(IDatabaseClient databaseClient, ActualUserWrapper actualUserWrapper, ContextWrapper contextWrapper)
	{
		this.databaseClient = databaseClient;
		this.actualUserWrapper = actualUserWrapper;
		this.contextWrapper = contextWrapper;
	}

	public void setLoginFragment(LoginFragment loginFragment)
	{
		this.loginFragment = loginFragment;
	}

	public void setSettingsFragment(SettingsFragment settingsFragment)
	{
		this.settingsFragment = settingsFragment;
	}

	public String getLastLogin()
	{
		return PreferenceManager.getDefaultSharedPreferences(contextWrapper.getContext()).getString(AppConstants.SharedPreferencesNames.EMAIL, "");
	}

	public void facebookLoginInit(LoginButton loginButton)
	{
		loginButton.setReadPermissions(Arrays.asList(AppConstants.FacebookNames.USER_EMAIL_PERMISSION, AppConstants.FacebookNames.USER_EMAIL_PERMISSION));
		loginButton.setFragment(loginFragment);
		callbackManager = CallbackManager.Factory.create();
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
		{
			@Override
			public void onSuccess(LoginResult loginResult)
			{
				Log.v(LOG_TAG, "onSuccess Token " + loginResult.getAccessToken()
						+ " Permissions" + loginResult.getRecentlyGrantedPermissions());
				logInAsync(loginResult.getAccessToken());
			}

			@Override
			public void onCancel()
			{
				Log.v(LOG_TAG, "onCancel");
			}

			@Override
			public void onError(FacebookException error)
			{
				Log.v(LOG_TAG, "onError");
			}
		});
	}

	private void logInAsync(AccessToken accessToken)
	{
		GraphRequest request = GraphRequest.newMeRequest(
				accessToken,
				new GraphRequest.GraphJSONObjectCallback()
				{
					@Override
					public void onCompleted(JSONObject object, GraphResponse response)
					{
						Log.v(LOG_TAG, "onCompleted " + response.toString());

						// Application code
						try
						{
							String email = object.getString("email");
							String name = object.getString("name");
							facebookLogin(email, name);
						} catch(JSONException e)
						{
							e.printStackTrace();
						}
					}
				});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "name,email");
		request.setParameters(parameters);
		request.executeAsync();
//				LoginManager.getInstance().logInWithPublishPermissions(
//						getActivity(),  Arrays.asList("public_profile", "user_friends"));
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(callbackManager != null)
		{
			callbackManager.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void facebookLogin(String email, String name)
	{
		UserModel userModel = new UserModel(email, "facebook", -1);
		userModel.setName(name);
		userModel.setWhereClause(UserModel.UserNames.LOGIN + "='" + userModel.getLogin() + "'");
		List<UserModel> userModelList = UserModel.buildFromContentValuesList(
				databaseClient.select(userModel));
		if(userModelList.size() > 0)
		{
			startMainActivity(userModelList.get(0), false);
		}
		else
		{
			databaseClient.insert(userModel);
			facebookLogin(email, name);
		}
	}

	private void putSharedPreferences(String email)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextWrapper.getContext().getApplicationContext());
		SharedPreferences.Editor ed = sharedPreferences.edit();
		ed.putString(AppConstants.SharedPreferencesNames.EMAIL, email);
		ed.commit();
	}

	public boolean isLoggedIn()
	{
		return AccessToken.getCurrentAccessToken() != null;
	}

	public void attemptLogin()
	{
		if(isLoggedIn())
		{
			//TODO: uncomment after logout button'll be added
			logInAsync(AccessToken.getCurrentAccessToken());
			return;
		}
		loginFragment.attemptLogin();
	}

	public List<UserModel> authorize(String email, String password)
	{
		UserModel userModel = new UserModel(email, password, -1);
		userModel.setWhereClause(UserModel.UserNames.LOGIN + "='" + userModel.getLogin() + "'");
		return UserModel.buildFromContentValuesList(databaseClient.select(userModel));
	}

	public void startMainActivity(UserModel userModel, boolean checked)
	{
		putSharedPreferences(checked ? userModel.getLogin() : "");
		actualUserWrapper.setActualUser(userModel);
//		Intent intent = new Intent(loginFragment.getActivity(), TestActivity.class);
		Intent intent = new Intent(loginFragment.getActivity(), StartActivity.class);
		Log.v(LOG_TAG, "MainActivity is starting");
		loginFragment.startActivity(intent);
	}

	public static void logoutFb()
	{
		LoginManager.getInstance().logOut();
		if (settingsFragment != null)
		{
			settingsFragment.getActivity().finish();
		}
	}
}
