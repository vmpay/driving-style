package com.diploma.vmpay.driving_style.utils;

import android.support.annotation.Nullable;

import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;

import java.util.List;

/**
 * Created by Andrew on 05/09/2016.
 */
public class ModelUtils
{
	@Nullable
	public static UserModel getLastLoggedUser(IDatabaseClient databaseClient)
	{
		List<UserModel> UserModelList = UserModel.buildFromContentValuesList(databaseClient.select(new UserModel()));

		if(UserModelList.isEmpty()) return null;

		UserModel actualModel = ModelUtils.getLastConnectedUser(UserModelList);
		if(actualModel != null) return actualModel;

		return UserModelList.get(0);
	}

	@Nullable
	public static UserModel getLastConnectedUser(List<UserModel> UserModelList)
	{
		if(UserModelList == null || UserModelList.isEmpty()) return null;

		long maxTimestamp = -1;
		long timestamp;
		UserModel newestModel = null;

		for(UserModel userModel : UserModelList)
		{
			timestamp = userModel.getLastConnectionTimestamp();
			if(timestamp > maxTimestamp)
			{
				maxTimestamp = timestamp;
				newestModel = userModel;
			}
		}

		return newestModel;
	}
}
