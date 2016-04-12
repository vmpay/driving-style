package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 28.03.2016.
 */
public class ScenarioFragment extends Fragment
{
	private final String LOG_TAG = "TestActivity";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Log.d(LOG_TAG, "ScenarioFragment onCreateView()");
		View v = inflater.inflate(R.layout.scenario_fragment, container, false);

		return v;
	}
}
