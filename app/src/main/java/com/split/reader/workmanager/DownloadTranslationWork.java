package com.split.reader.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.split.reader.MainApplication;
import com.split.reader.data.DataManager;
import com.split.reader.downloader.TranslationDownloader;
import com.split.reader.model.TranslationData;

import java.util.List;

import javax.inject.Inject;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadTranslationWork extends Worker {

    private static final String TAG = DownloadTranslationWork.class.getName();
    @Inject
    DataManager dataManager;
    @Inject
    TranslationDownloader translationDownloader;

    public DownloadTranslationWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        MainApplication.getAppComponent().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<TranslationData> data = dataManager.fetchTranslationSync();
        if(data!=null){
            Log.d(TAG,data.get(0).getFileUrl());
            String url = data.get(0).getFileUrl();
            String title = data.get(0).getDisplayName();
            translationDownloader.download(title,url);

        }
        else {
            Log.d(TAG,"failed to read translation data. Aborting download");
        }
        return Result.SUCCESS;
    }
}
