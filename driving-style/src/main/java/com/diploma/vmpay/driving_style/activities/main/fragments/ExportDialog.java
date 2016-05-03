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

import com.diploma.vmpay.driving_style.AppConstants;
import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.database.dbmodels.AccDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.GpsDataModel;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.database.dbutils.DatabaseAccess;


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
		String fileName = "";
		if(etFileName.getText().toString().contains("[^a-zA-Z0-9.-]")
				|| etFileName.getText().toString().isEmpty())
		{
			focusView = etFileName;
			etFileName.setError(getResources().getString(R.string.filename_error));
			focusView.requestFocus();
		}
		else
		{
			fileName = etFileName.getText().toString();
		}

		if(!cbUsers.isChecked() && !cbTrips.isChecked() && !cbAcc.isChecked() && !cbGps.isChecked())
		{
			tvExportResult.setText(getResources().getString(R.string.cb_error));
		}
		else
		{
			DatabaseAccess databaseAccess = new DatabaseAccess(getActivity());
			boolean cancel = false;
			String error = "Exporting failure\n";
			if(cbUsers.isChecked())
			{
				if(!databaseAccess.exportToCSV(databaseAccess.selectCursor(
						new UserModel()), fileName + AppConstants.ExportFileNames.USER))
				{
					cancel = true;
					error += fileName + AppConstants.ExportFileNames.USER + "\n";
				}
			}

			if(cbTrips.isChecked())
			{
				if(!databaseAccess.exportToCSV(databaseAccess.selectCursor(
						new TripModel()), fileName + AppConstants.ExportFileNames.TRIP))
				{
					cancel = true;
					error += fileName + AppConstants.ExportFileNames.TRIP + "\n";
				}
			}

			if(cbAcc.isChecked())
			{
				if(!databaseAccess.exportToCSV(databaseAccess.selectCursor(
						new AccDataModel()), fileName + AppConstants.ExportFileNames.ACCELEROMETER))
				{
					cancel = true;
					error += fileName + AppConstants.ExportFileNames.ACCELEROMETER + "\n";
				}
			}

			if(cbGps.isChecked())
			{
				if(!databaseAccess.exportToCSV(databaseAccess.selectCursor(
						new GpsDataModel()), fileName + AppConstants.ExportFileNames.GPS))
				{
					cancel = true;
					error += fileName + AppConstants.ExportFileNames.GPS + "\n";
				}
			}

			if(cancel)
			{
				tvExportResult.setText(error);
			}
			else
			{
				tvExportResult.setText(getResources().getString(R.string.result_msg));
			}
		}
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
