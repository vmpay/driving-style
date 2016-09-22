package com.diploma.vmpay.driving_style.activities.main.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 19/09/2016.
 */
public class EmptyHistoryFragment extends Fragment
{
	private static final String LOG_TAG = "EmptyHistoryFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.empty_history_fragment, container, false);

		return v;
	}
}
