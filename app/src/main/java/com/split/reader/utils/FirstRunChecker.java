package com.split.reader.utils;

import com.split.reader.prefs.PreferencesHelper;
import com.split.reader.workmanager.DownloadTranslationWork;
import com.split.reader.workmanager.TranslationRetrievalWork;

import javax.inject.Inject;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class FirstRunChecker {

    PreferencesHelper preferencesHelper;
    OneTimeWorkRequest transWork;
    OneTimeWorkRequest downloadTrans;


    @Inject
    public FirstRunChecker(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    public void doFirstRunWork() {
         if (!preferencesHelper.getFirstRun()) {
        createFirstTimeRequests();
        WorkManager.getInstance()
                .beginWith(transWork)
                .then(downloadTrans)
                .enqueue();
          preferencesHelper.setFirstRun(true);
         }
    }

    private void createFirstTimeRequests() {
        // Create a Constraints object that defines when the task should run
        Constraints myConstraints = new Constraints.Builder()
               .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        transWork = new OneTimeWorkRequest.Builder(TranslationRetrievalWork.class)
                        .build();
        downloadTrans = new OneTimeWorkRequest.Builder(DownloadTranslationWork.class)
                .setConstraints(myConstraints)
                .build();
    }
}
