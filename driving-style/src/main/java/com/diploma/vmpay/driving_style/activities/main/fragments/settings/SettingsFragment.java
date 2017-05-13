package com.diploma.vmpay.driving_style.activities.main.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.SensorPresenter;
import com.diploma.vmpay.driving_style.presenters.UserLoginPresenter;
import com.diploma.vmpay.driving_style.utils.BugTrackingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Andrew on 19/09/2016.
 */
public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener
{
	private static final String LOG_TAG = "SettingsFragment";

	private SensorPresenter sensorPresenter;
	private UserLoginPresenter userLoginPresenter;
	private Unbinder unbinder;
	@BindView(R.id.tvAlpha) TextView tvAlpha;
	@BindView(R.id.tvVersion) TextView tvVersion;
	@BindView(R.id.sbAlpha) SeekBar sbAlpha;
	@BindView(R.id.btnLogout) Button btnLogout;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.settings_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);

		sensorPresenter = AppController.getInstance().getSensorPresenter();
		userLoginPresenter = AppController.getInstance().getUserLoginPresenter();
		sensorPresenter.setSettingsFragment(this);
		tvAlpha.setText("0.8");
		sbAlpha.setOnSeekBarChangeListener(this);
		btnLogout.setOnClickListener(this);
		tvVersion.setText(BugTrackingUtils.getAppVersion());

		return v;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		sensorPresenter.setSettingsFragment(null);
		unbinder.unbind();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b)
	{
		Log.v(LOG_TAG, " progress " + sbAlpha.getProgress());
		tvAlpha.setText(String.format("%.2f", sbAlpha.getProgress()/(double)sbAlpha.getMax()));
		sensorPresenter.setAlpha(sbAlpha.getProgress()/(double)sbAlpha.getMax());
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
	}

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
			case R.id.btnLogout:
				UserLoginPresenter.logoutFb();
				break;
		}
	}
}
