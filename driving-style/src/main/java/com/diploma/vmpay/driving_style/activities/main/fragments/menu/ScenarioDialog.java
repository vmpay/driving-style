package com.diploma.vmpay.driving_style.activities.main.fragments.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.SensorPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Andrew on 25/09/2016.
 */

public class ScenarioDialog extends android.support.v4.app.DialogFragment implements RadioGroup.OnCheckedChangeListener
{
	private static final String LOG_TAG = "ScenarioDialog";

	private Unbinder unbinder;
	private SensorPresenter sensorPresenter;

	@BindView(R.id.llScenario) LinearLayout llScenario;
	@BindView(R.id.llPickScenario) LinearLayout llPickScenario;
	@BindView(R.id.btnProceed)
	Button btnProceed;
	@BindView(R.id.rbngScenarioType)
	RadioGroup radioGroup;
	@BindView(R.id.tvScenarioName)
	TextView tvScenarioName;
	@BindView(R.id.ivScenarioHint)
	ImageView ivScenario;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "onCreateView()");
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		View v = inflater.inflate(R.layout.scenario_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);
		((RadioGroup)v.findViewById(R.id.rbngScenarioType)).setOnCheckedChangeListener(this);

		sensorPresenter = AppController.getInstance().getSensorPresenter();
		sensorPresenter.setScenarioDialog(this);

		return v;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}

	//	@OnCheckedChanged(R.id.rbngScenarioType)
//	public void checkedChanged(RadioGroup view)
//	{
//		btnProceed.setVisibility(View.VISIBLE);
//	}

	@OnClick({R.id.btnProceed, R.id.ivNo, R.id.ivYes})
	public void buttonClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.btnProceed:
				if (!sensorPresenter.isGpsAvailable())
				{
					sensorPresenter.enableGpsDialog(getActivity());
					return;
				}
				int scenarioName = R.string.unknown;
				int drawableId = R.drawable.ic_no;
				switch(radioGroup.getCheckedRadioButtonId())
				{
					case R.id.rbnBraking:
						scenarioName = R.string.scenario_braking;
						drawableId = R.drawable.braking;
						sensorPresenter.setScenarioType(AppConstants.ScenarioType.BRAKING);
						break;
					case R.id.rbnTurn:
						scenarioName = R.string.scenario_turn;
						drawableId = R.drawable.sharp_turn;
						sensorPresenter.setScenarioType(AppConstants.ScenarioType.TURNING);
						break;
					case R.id.rbnLaneChange:
						scenarioName = R.string.scenario_lane_change;
						drawableId = R.drawable.lane_change;
						sensorPresenter.setScenarioType(AppConstants.ScenarioType.CHANGING_LANE);
						break;
				}
				llPickScenario.setVisibility(View.GONE);
				tvScenarioName.setText(getActivity().getResources().getString(scenarioName));
				ivScenario.setImageDrawable(getActivity().getResources().getDrawable(drawableId));
				llScenario.setVisibility(View.VISIBLE);
				sensorPresenter.startSensors();
				break;
			case R.id.ivNo:
				sensorPresenter.stopSensors();
				sensorPresenter.clearLists();
				dismiss();
				break;
			case R.id.ivYes:
				sensorPresenter.stopSensors();
				sensorPresenter.saveScenario();
				sensorPresenter.clearLists();
				dismiss();
				break;
		}
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		sensorPresenter.setScenarioDialog(null);
		unbinder.unbind();
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int i)
	{
		btnProceed.setVisibility(View.VISIBLE);
	}
}
