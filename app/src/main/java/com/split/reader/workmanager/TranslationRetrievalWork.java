package com.split.reader.workmanager;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.split.reader.MainApplication;
import com.split.reader.data.DataManager;
import com.split.reader.data.TranslationDatabaseListener;
import com.split.reader.model.TranslationData;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TranslationRetrievalWork extends Worker {

    private static final String TAG = TranslationRetrievalWork.class.getName();
    @Inject
     DataManager dataManager;

    public TranslationRetrievalWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
       MainApplication.getAppComponent().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Result[] result = {Result.RETRY};
        dataManager.fetchTranslationRepo(new TranslationDatabaseListener() {
            @Override
            public void onFinished(List<TranslationData> responseObject) {
                result[0] = Result.SUCCESS;
                countDownLatch.countDown();
                Log.d(TAG,"retrieved and saved translations");
            }

            @Override
            public void onFailed(Throwable t) {
                result[0] = Result.RETRY;
                countDownLatch.countDown();
                Log.d(TAG,t.toString());
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    return result[0];
    }


}
