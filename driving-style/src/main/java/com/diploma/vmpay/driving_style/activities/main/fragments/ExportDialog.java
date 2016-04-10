package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseManager;


/**
 * Created by Andrew on 10.04.2016.
 */
public class ExportDialog extends DialogFragment implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher
{
	private final String LOG_TAG = "TestActivity";

	private Button btnBack, btnExport;
	private CheckBox cbUsers, cbTrips, cbAcc, cbGps;
	private EditText etFileName;
	private TextView tvExportResult;

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

		tvExportResult = (TextView) v.findViewById(R.id.tvExportResult);

		cbUsers = (CheckBox) v.findViewById(R.id.cbUsers);
		cbTrips = (CheckBox) v.findViewById(R.id.cbTrips);
		cbAcc = (CheckBox) v.findViewById(R.id.cbAccData);
		cbGps = (CheckBox) v.findViewById(R.id.cbGpsData);

		etFileName = (EditText) v.findViewById(R.id.etFileName);

		etFileName.addTextChangedListener(this);
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
		Log.d(LOG_TAG, "onEditorAction actionId " + actionId);
//		if (actionId == EditorInfo.IME_ACTION_SEND) {
		if(actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
		{
			exportData();
			handled = true;
		}
		return handled;
	}

	public void exportData()
	{
		etFileName.setError(null);
		tvExportResult.setText("");
		View focusView = null;
		boolean cancel = false;
		String fileName = "";
		if(etFileName.getText().toString().contains("[^a-zA-Z0-9.-]")
				|| etFileName.getText().toString().isEmpty())
		{
			cancel = true;
			focusView = etFileName;
			etFileName.setError("Invalid filename");
		}
		else
		{
			fileName = etFileName.getText().toString();
		}

		if(!cbUsers.isChecked() && !cbTrips.isChecked() && !cbAcc.isChecked() && !cbGps.isChecked())
		{
			cancel = true;
//			focusView = cbUsers;
//			cbUsers.setError("Check at least one option");
			tvExportResult.setText("Check at least one option");
		}

		if(cancel)
		{
//			focusView.requestFocus();
		}
		else
		{
			DatabaseManager databaseManager = new DatabaseManager(getActivity());
			//TODO: export data
			cancel = false;
			String error = "Exporting failure\n";
			if(cbUsers.isChecked())
			{
				//TODO: add users table
			}

			if(cbTrips.isChecked())
			{
				if(!databaseManager.exportTripData(fileName + "_trips"))
				{
					cancel = true;
					error += fileName + "_trips.csv\n";
//					focusView = cbTrips;
//					cbTrips.setError("Export failed");
				}
			}

			if(cbAcc.isChecked())
			{
				if(!databaseManager.exportTripData(fileName + "_acc"))
				{
					cancel = true;
					error += fileName + "_acc.csv\n";
//					focusView = cbAcc;
//					cbAcc.setError("Export failed");
				}
			}

			if(cbGps.isChecked())
			{
				if(!databaseManager.exportTripData(fileName + "_gps"))
				{
					cancel = true;
					error += fileName + "_gps.csv\n";
//					focusView = cbGps;
//					cbGps.setError("Export failed");
				}
			}

			if(cancel)
			{
				tvExportResult.setText(error);
				focusView.requestFocus();
			}
			else
			{
				tvExportResult.setText("Exported successfully");
			}
		}
		return;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		etFileName.setError(null);
	}

	@Override
	public void afterTextChanged(Editable s)
	{
	}
}
