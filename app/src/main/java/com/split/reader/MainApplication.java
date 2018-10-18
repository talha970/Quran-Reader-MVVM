package com.split.reader;

import android.app.Application;

import com.split.reader.di.AppComponent;
import com.split.reader.di.AppModule;
import com.split.reader.di.DaggerAppComponent;


public class MainApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))

                .build();

        getAppComponent().inject(this);

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
