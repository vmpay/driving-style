package com.diploma.vmpay.driving_style.interfaces;

import android.support.annotation.Nullable;

import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;

/**
 * Created by Andrew on 05/09/2016.
 */
public interface IOnActualUserChangedListener
{
	void onActualUserChanged(@Nullable UserModel userModel);
}
