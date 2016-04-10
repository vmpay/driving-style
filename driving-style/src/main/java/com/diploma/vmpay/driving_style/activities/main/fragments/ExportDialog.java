package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 10.04.2016.
 */
public class ExportDialog extends DialogFragment implements View.OnClickListener, TextView.OnEditorActionListener
{
	private final String LOG_TAG = "TestActivity";

	private Button btnBack, btnExport;
	private CheckBox cbUsers, cbTrips, cbAcc, cbGps;
	private EditText etFileName;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Log.d(LOG_TAG, "ExportDialog onCreateView()");
		View v = inflater.inflate(R.layout.exporting_fragment, container, false);

		btnBack = (Button) v.findViewById(R.id.btnBack);
		btnExport = (Button) v.findViewById(R.id.btnExport);

		btnBack.setOnClickListener(this);
		btnExport.setOnClickListener(this);

		cbUsers = (CheckBox) v.findViewById(R.id.cbUsers);
		cbTrips = (CheckBox) v.findViewById(R.id.cbTrips);
		cbAcc = (CheckBox) v.findViewById(R.id.cbAccData);
		cbGps = (CheckBox) v.findViewById(R.id.cbGpsData);

		etFileName = (EditText) v.findViewById(R.id.etFileName);

		etFileName.setOnEditorActionListener(this);

		return v;
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnExport:
				exportData();
				break;
			case R.id.btnBack:
				dismiss();
				break;
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	{
		boolean handled = false;
//		Log.d(LOG_TAG, "onEditorAction actionId " + actionId);
//		if (actionId == EditorInfo.IME_ACTION_SEND) {
		if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
			exportData();
			handled = true;
		}
		return handled;
	}

	public void exportData()
	{
		//TODO: validate input data
		etFileName.setError(null);
		View focusView = null;
		focusView = etFileName;
		etFileName.setError("Test error");
		focusView.requestFocus();
		//TODO: export data
		return;
	}
}
