package com.split.reader.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.split.reader.MainApplication;
import com.split.reader.db.dao.TranslationDataDao;
import com.split.reader.model.TranslationData;
import com.split.reader.prefs.PreferencesHelper;

import java.io.File;


import javax.inject.Inject;

public class DownloadCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = DownloadCompleteReceiver.class.getCanonicalName();
    @Inject
    DownloadManager downloadManager;
    @Inject
    PreferencesHelper preferencesHelper;
    @Inject
    TranslationDataDao translationDataDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        MainApplication.getAppComponent().inject(this);
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                long id = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
                if (id == -1) {
                    //fucked up
                } else {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor == null || !cursor.moveToFirst()) {
                        // non existent
                        Log.d(TAG, "non existent");
                    }
                    else{
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (DownloadManager.STATUS_PAUSED == status ||
                                DownloadManager.STATUS_PENDING == status ||
                                DownloadManager.STATUS_RUNNING == status) {
                            Log.d(TAG, "other status");
                        }
                        if (DownloadManager.STATUS_FAILED == status) {
                            int reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                            Log.d(TAG, "download failed reason: "+reason);
                        }
                            String localUriString =
                                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                        //Parse its uri for android to find
                        Uri localUri = Uri.parse(localUriString);

                        //Check if file exists
                        final File file = new File(localUri.getPath());
                        if(file.exists()){
                            String filePath =
                                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            String fileName = filePath.substring( filePath.lastIndexOf('/')+1, filePath.length() );
                            //TODO move to intentservice
                            TranslationData translationData = translationDataDao.getTranslationObject(fileName);
                            preferencesHelper.setTranslationSet(true);
                            preferencesHelper.setTranslationName(translationData.getFileName());
                            preferencesHelper.setTranslationVersion(Integer.valueOf(translationData.getCurrentVersion()));


                        }
                    }
                }
            }
        }
    }
}
