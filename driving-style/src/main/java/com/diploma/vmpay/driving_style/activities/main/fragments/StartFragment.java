package com.diploma.vmpay.driving_style.activities.main.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diploma.vmpay.driving_style.R;

import java.util.Random;

/**
 * Created by Andrew on 10.02.2016.
 */
public class StartFragment extends Fragment {

    private final String LOG_TAG = "StartActivity";
    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int pageNumber, backColor;
    private TextView tvPageNumber;

    //TODO: not forget to remove this designers' stuff
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate() StartFragment");

        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() StartFragment");
        View v = inflater.inflate(R.layout.step_fragment, container, false);

        tvPageNumber = (TextView) v.findViewById(R.id.tvPageNumber);
        tvPageNumber.setText("Page " + pageNumber);
        tvPageNumber.setBackgroundColor(backColor);

        return v;
    }

    public static StartFragment newInstance(int page) {
        StartFragment startFragment = new StartFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        startFragment.setArguments(arguments);
        return startFragment;
    }
}
