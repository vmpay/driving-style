package com.diploma.vmpay.driving_style.activities.main.fragments.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.SensorPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Andrew on 15/09/2016.
 */
public class MainMenuFragment extends Fragment
{
	private static final String LOG_TAG = "MainMenuFragment";

	private Unbinder unbinder;

	private SensorPresenter sensorPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.main_menu_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);

		sensorPresenter = AppController.getInstance().getSensorPresenter();
		sensorPresenter.setMainMenuFragment(this);

		return v;
	}

	@OnClick({R.id.btnStartPractice, R.id.btnStartExam, R.id.btnStartScenario})
	public void buttonClick(Button view)
	{
		switch(view.getId())
		{
			case R.id.btnStartPractice:
				Log.v(LOG_TAG, "btnStartPractice");
				startDialog(AppConstants.TripType.PRACTICE);
				break;
			case R.id.btnStartExam:
				Log.v(LOG_TAG, "btnStartExam");
				startDialog(AppConstants.TripType.EXAM);
				break;
			case R.id.btnStartScenario:
				Log.v(LOG_TAG, "btnStartScenario");
				startDialog(AppConstants.TripType.SCENARIO);
				break;
		}
	}

	private void startDialog(AppConstants.TripType type)
	{
		FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
		switch(type)
		{
			case PRACTICE:
				break;
			case EXAM:
				break;
			case SCENARIO:
				ScenarioDialog dialog = new ScenarioDialog();
				dialog.show(fragmentTransaction, "ScenarioDialog");
				break;
		}
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		sensorPresenter.setMainMenuFragment(null);
		unbinder.unbind();
	}

	//				MainMenuFragment mainMenuFragment = new MainMenuFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				fragmentTransaction.replace(R.id.fragmentStartActivity, mainMenuFragment);
//				//fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
}
