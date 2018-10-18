package com.split.reader.di;

import com.split.reader.ui.MainActivity;
import com.split.reader.MainApplication;
import com.split.reader.ui.SurahDetailActivity;
import com.split.reader.viewmodels.SurahDetailViewModel;
import com.split.reader.viewmodels.SurahViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(MainApplication mainApplication);

    void inject(SurahViewModel surahViewModel);

    void inject(SurahDetailActivity surahDetailActivity);

    void inject(SurahDetailViewModel surahDetailViewModel);

}