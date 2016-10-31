package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;
import com.diploma.vmpay.driving_style.controller.AppController;

/**
 * Created by Andrew on 15/09/2016.
 */
public class ViewPagerFragment extends Fragment implements ViewPager.OnPageChangeListener
{
	private static final String LOG_TAG = "ViewPagerFragment";
	private TextView tvStepNumber;
	private ViewPager viewPager;
	private MainFragmentPagerAdapter pagerAdapter;
	private String[] stepNumbers;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "ViewPagerFragment onCreateView()");
		View v = inflater.inflate(R.layout.tutorial_fragment, container, false);

		tvStepNumber = (TextView) v.findViewById(R.id.tvStepNumber);
		viewPager = (ViewPager) v.findViewById(R.id.vpMain);

		pagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(pagerAdapter);

		viewPager.addOnPageChangeListener(this);

		stepNumbers = getResources().getStringArray(R.array.dot_bar);
		tvStepNumber.setText(stepNumbers[0]);

		return v;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	@Override
	public void onPageSelected(int position)
	{
		Log.v(LOG_TAG, "onPageSelected, position = " + position);

		tvStepNumber.setText(stepNumbers[position]);
		//TODO: refactor page number somehow
		if (position == 1)
		{
//			update history fragment
			AppController.getInstance().getHistoryPresenter().updateTripModelList();
		}
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}
}
