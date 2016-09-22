package com.diploma.vmpay.driving_style.activities.main.fragments.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 15/09/2016.
 */
public class MainMenuFragment extends Fragment
{
	private static final String LOG_TAG = "MainMenuFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Log.v(LOG_TAG, "MainMenuFragment onCreateView()");
		View v = inflater.inflate(R.layout.main_menu_fragment, container, false);

		return v;
	}

//				MainMenuFragment mainMenuFragment = new MainMenuFragment();
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				fragmentTransaction.replace(R.id.fragmentStartActivity, mainMenuFragment);
//				//fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
}
