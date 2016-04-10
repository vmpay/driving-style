package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 10.04.2016.
 */
public class ExportDialog extends DialogFragment
{
	private final String LOG_TAG = "TestActivity";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Log.d(LOG_TAG, "ExportDialog onCreateView()");
		View v = inflater.inflate(R.layout.exporting_fragment, container, false);

		return v;
	}
}
