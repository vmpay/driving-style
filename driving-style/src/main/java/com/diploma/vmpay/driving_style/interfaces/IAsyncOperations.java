package com.diploma.vmpay.driving_style.interfaces;

import com.diploma.vmpay.driving_style.database.dbentities.AsyncExportEntity;

/**
 * Created by Andrew on 05/09/2016.
 */
public interface IAsyncOperations
{
    void onAsyncInsertFinished();

    void onAsyncExportFinished(AsyncExportEntity AsyncExportEntity);

    void onAsyncExportStarted();
}
