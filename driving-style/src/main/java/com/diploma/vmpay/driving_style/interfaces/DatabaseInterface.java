package com.diploma.vmpay.driving_style.interfaces;

import com.diploma.vmpay.driving_style.database.dbentities.AsyncExportEntity;

/**
 * Created by Andrew on 03.05.2016.
 */
public interface DatabaseInterface
{
	void onAsyncInsertFinished();

	void onAsyncExportFinished(AsyncExportEntity AsyncExportEntity);

	void onAsyncExportStarted();
}
