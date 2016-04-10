package com.diploma.vmpay.driving_style.activities.main.fragments;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.diploma.vmpay.driving_style.activities.main.fragments.StartFragment;

/**
 * Created by Andrew on 11.02.2016.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 7;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return StartFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
