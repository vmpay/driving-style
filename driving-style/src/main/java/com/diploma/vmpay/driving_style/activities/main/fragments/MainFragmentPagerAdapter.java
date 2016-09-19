package com.diploma.vmpay.driving_style.activities.main.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Andrew on 11.02.2016.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter
{
	private static final int PAGE_COUNT = 3;

	public MainFragmentPagerAdapter(FragmentManager fragmentManager)
	{
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position)
	{
		Fragment fragment;
		switch(position)
		{
			case 0:
				fragment = new MainMenuFragment();
				break;
			case 1:
				fragment = new HistoryFragment();
				break;
			case 2:
				fragment = new SettingsFragment();
				break;
			default:
				fragment = StartFragment.newInstance(position);
				break;
		}
		return fragment;
	}

	@Override
	public int getCount()
	{
		return PAGE_COUNT;
	}
}
