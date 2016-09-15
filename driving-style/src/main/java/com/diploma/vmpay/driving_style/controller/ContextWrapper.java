package com.diploma.vmpay.driving_style.controller;

import android.content.Context;

import com.diploma.vmpay.driving_style.interfaces.IOnContextChangedListener;
import com.diploma.vmpay.driving_style.utils.ListenerList;

/**
 * Created by Andrew on 07/09/2016.
 */
public class ContextWrapper
{
	private Context context;
	private ListenerList<IOnContextChangedListener> onContextChangedListenerList = new ListenerList<>();

	public ContextWrapper(Context context)
	{
		this.context = context;
	}

	public void setContext(Context context)
	{
		if(this.context != context)
		{
			this.context = context;
			for(IOnContextChangedListener contextChangedListener : onContextChangedListenerList.getListCopy())
			{
				contextChangedListener.onContextChanged(context);
			}
		}
	}

	public Context getContext()
	{
		return context;
	}

	public void addOnContextChangeListener(IOnContextChangedListener listener)
	{
		onContextChangedListenerList.add(listener);
	}
}
