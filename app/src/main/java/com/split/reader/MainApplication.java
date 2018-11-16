package com.split.reader;

import android.app.Application;

import com.split.reader.di.AppComponent;
import com.split.reader.di.AppModule;
import com.split.reader.di.DaggerAppComponent;
import com.split.reader.prefs.AppPreferencesHelper;
import com.split.reader.utils.FirstRunChecker;
import com.split.reader.workmanager.TranslationRetrievalWork;

import javax.inject.Inject;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class MainApplication extends Application {
    private static AppComponent appComponent;
    @Inject
    FirstRunChecker firstRunChecker;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))

                .build();

        getAppComponent().inject(this);
        firstRunChecker.doFirstRunWork();

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
