package com.diploma.vmpay.driving_style.presenters;

import android.support.annotation.Nullable;

import com.diploma.vmpay.driving_style.activities.main.fragments.profile.HistoryFragment;
import com.diploma.vmpay.driving_style.activities.main.fragments.profile.ProfileFragment;
import com.diploma.vmpay.driving_style.adapters.HistoryAdapter;
import com.diploma.vmpay.driving_style.controller.ActualUserWrapper;
import com.diploma.vmpay.driving_style.controller.ContextWrapper;
import com.diploma.vmpay.driving_style.database.dbmodels.TripModel;
import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.diploma.vmpay.driving_style.interfaces.IOnActualUserChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 19/09/2016.
 */
public class HistoryPresenter implements IOnActualUserChangedListener
{
	private static final String LOG_TAG = "HistoryPresenter";

	private ActualUserWrapper actualUserWrapper;
	private IDatabaseClient databaseClient;
	private ContextWrapper contextWrapper;

	private ProfileFragment profileFragment;
	private HistoryFragment historyFragment;
	private HistoryAdapter historyAdapter;

	private List<TripModel> tripModelList = new ArrayList<>();

	public HistoryPresenter(ActualUserWrapper actualUserWrapper, IDatabaseClient databaseClient, ContextWrapper contextWrapper)
	{
		this.actualUserWrapper = actualUserWrapper;
		this.databaseClient = databaseClient;
		this.contextWrapper = contextWrapper;
	}

	@Override
	public void onActualUserChanged(@Nullable UserModel userModel)
	{
		getTripModelList();
		historyAdapter = new HistoryAdapter(tripModelList, contextWrapper.getContext());
	}

	public void updateTripModelList()
	{
		getTripModelList();
		historyAdapter.notifyDataSetChanged();
	}

	private void getTripModelList()
	{
//		TripModel tripModel = new TripModel(actualUserWrapper.getActualUser().getId());
//		tripModel.setWhereClause(TripModel.TripNames.USER_ID + "=" + tripModel.getUserId());
//		tripModelList = TripModel.buildFromContentValuesList(databaseClient.select(tripModel));
	}

	public void setProfileFragment(ProfileFragment profileFragment)
	{
		this.profileFragment = profileFragment;
	}

	public void setHistoryFragment(HistoryFragment historyFragment)
	{
		this.historyFragment = historyFragment;
	}

	public String getUserName()
	{
		return actualUserWrapper.getActualUser().getName();
	}

	public HistoryAdapter getHistoryAdapter()
	{
		return historyAdapter;
	}

	public boolean isDataValid()
	{
		return tripModelList.size() > 0;
	}
}
