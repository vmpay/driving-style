package com.diploma.vmpay.driving_style.activities.main.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;
import com.diploma.vmpay.driving_style.presenters.HistoryPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Andrew on 19/09/2016.
 */
public class ProfileFragment extends Fragment
{
	private static final String LOG_TAG = "ProfileFragment";

	private HistoryPresenter historyPresenter;

	private Unbinder unbinder;
	@BindView(R.id.tvHelloUser) TextView tvGreetings;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.profile_fragment, container, false);
		unbinder = ButterKnife.bind(this, v);

		historyPresenter = AppController.getInstance().getHistoryPresenter();
		historyPresenter.setProfileFragment(this);

		String string = getActivity().getResources().getText(R.string.hello) + " " + historyPresenter.getUserName() + "!";
//		tvGreetings = (TextView) v.findViewById(R.id.tvHelloUser);
		tvGreetings.setText(string);

		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		if(!historyPresenter.isDataValid())
		{
			HistoryFragment historyFragment = new HistoryFragment();
			fragmentTransaction.add(R.id.llHistoryFragment, historyFragment, "HistoryFragment").commit();
		}
		else
		{
			EmptyHistoryFragment emptyHistoryFragment = new EmptyHistoryFragment();
			fragmentTransaction.add(R.id.llHistoryFragment, emptyHistoryFragment, "EmptyHistoryFragment").commit();
		}

		return v;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		historyPresenter.setProfileFragment(null);
		unbinder.unbind();
	}
}
