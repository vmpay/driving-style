package com.diploma.vmpay.driving_style.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 05/09/2016.
 */
public class ListenerList<T>
{
	private final Object mutex = new Object();
	private final List<T> list = new ArrayList<>();

	public void add(T object)
	{
		if(object == null) return;

		synchronized(mutex)
		{
			if(!list.contains(object))
			{
				list.add(object);
			}
		}
	}

	public void remove(T object)
	{
		synchronized(mutex)
		{
			list.remove(object);
		}
	}

	public void clear()
	{
		synchronized(mutex)
		{
			list.clear();
		}
	}

	public List<T> getListCopy()
	{
		List<T> localList;
		synchronized(mutex)
		{
			localList = new ArrayList<>(list);
		}
		return localList;
	}
}
