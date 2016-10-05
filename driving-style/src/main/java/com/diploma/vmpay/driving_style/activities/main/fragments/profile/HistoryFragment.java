package com.diploma.vmpay.driving_style.activities.main.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
				break;
			case R.id.btnThird:
				Log.v(LOG_TAG, "btnThird");
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
		tvMark.setText(Double.toString(historyAdapter.getAverageMark()));

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
