package com.diploma.vmpay.driving_style.activities.login.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diploma.vmpay.driving_style.R;

/**
 * Created by Andrew on 10.02.2016.
 */
public class RegistrationFragment extends Fragment {

    private final String LOG_TAG = "AuthActivity";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() RegistrationFragment");
        View v = inflater.inflate(R.layout.registration_fragment, container, false);


        return  v;
    }
}
