package com.diploma.vmpay.driving_style.activities.login.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.UserLoginPresenter;
import com.diploma.vmpay.driving_style.utils.BugTrackingUtils;
import com.facebook.FacebookSdk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Andrew on 10.02.2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener
{

	private final String LOG_TAG = "AuthActivity";
	@BindView(R.id.tvVersion) TextView tvVersion;
	private UserLoginPresenter userLoginPresenter;
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

		userLoginPresenter.facebookLoginInit();

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

	@OnClick({ R.id.ivFbLogin })
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.ivFbLogin:
				userLoginPresenter.attemptFacebookLogin(this);
				break;
		}
	}
}
