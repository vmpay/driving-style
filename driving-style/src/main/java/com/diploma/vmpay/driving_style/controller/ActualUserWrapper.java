package com.diploma.vmpay.driving_style.controller;

import com.diploma.vmpay.driving_style.database.dbmodels.UserModel;
import com.diploma.vmpay.driving_style.interfaces.IDatabaseClient;
import com.diploma.vmpay.driving_style.interfaces.IOnActualUserChangedListener;
import com.diploma.vmpay.driving_style.utils.ListenerList;
import com.diploma.vmpay.driving_style.utils.ModelUtils;

/**
 * Created by Andrew on 05/09/2016.
 */
public class ActualUserWrapper
{

	private UserModel actualUser;

	private ListenerList<IOnActualUserChangedListener> actualSelectedDeviceChangedListenerList;
	private IDatabaseClient databaseClient;

	public ActualUserWrapper(ListenerList<IOnActualUserChangedListener> actualSelectedDeviceChangedListenerList, IDatabaseClient databaseClient)
	{
		this.actualSelectedDeviceChangedListenerList = actualSelectedDeviceChangedListenerList;
		this.databaseClient = databaseClient;

		actualUser = ModelUtils.getLastLoggedUser(databaseClient);
	}

	public UserModel getActualUser()
	{
		return actualUser;
	}

	public void setActualUser(UserModel newUser)
	{
		this.actualUser = newUser;
		if (newUser != null)
		{
			for(IOnActualUserChangedListener listener : actualSelectedDeviceChangedListenerList.getListCopy())
			{
				listener.onActualUserChanged(newUser);
			}
		}
	}
}
