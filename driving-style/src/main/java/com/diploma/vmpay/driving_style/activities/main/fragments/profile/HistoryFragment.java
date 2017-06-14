package com.diploma.vmpay.driving_style.activities.main.fragments.profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.adapters.HistoryAdapter;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.HistoryPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Andrew on 19/09/2016.
 */
public class HistoryFragment extends Fragment
{
	private static final String LOG_TAG = "HistoryFragment";

	private HistoryPresenter historyPresenter;
	private HistoryAdapter historyAdapter;

	private Unbinder unbinder;
	@BindView(R.id.tvMark) TextView tvMark;
	@BindView(R.id.lvHistory) ListView lvHistory;

	@OnClick({R.id.btnShare, R.id.btnExport, R.id.btnThird})
	public void buttonClick(Button view)
	{
		switch(view.getId())
		{
			case R.id.btnShare:
				Log.v(LOG_TAG, "btnShare");
				break;
			case R.id.btnExport:
				Log.v(LOG_TAG, "btnExport");
				Toast.makeText(getActivity(), "Exporting data: Downloads/exported_data.csv", Toast.LENGTH_LONG).show();
				historyPresenter.exportData(historyAdapter.getCheckedItemsID(), "exported_data");
				break;
			case R.id.btnThird:
				Log.v(LOG_TAG, "btnDropDb");
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
				alertDialog.setTitle(R.string.alert_dialog_title);
				alertDialog.setMessage(R.string.alert_dialog_message);
				alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						historyPresenter.clearDatabase();
					}
				});
				alertDialog.show();
//				historyPresenter.clearDatabase();
				break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.history_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);

		historyPresenter = AppController.getInstance().getHistoryPresenter();
		historyPresenter.setHistoryFragment(this);

		historyAdapter = historyPresenter.getHistoryAdapter();
		lvHistory.setAdapter(historyAdapter);
		tvMark.setText(String.format(Double.toString(historyAdapter.getAverageMark())));

		return v;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		historyPresenter.setHistoryFragment(null);
		unbinder.unbind();
	}
}
