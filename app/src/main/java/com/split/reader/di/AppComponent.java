package com.split.reader.di;

import com.split.reader.receivers.DownloadCompleteReceiver;
import com.split.reader.ui.MainActivity;
import com.split.reader.MainApplication;
import com.split.reader.ui.SurahDetailActivity;
import com.split.reader.viewmodels.SurahDetailViewModel;
import com.split.reader.viewmodels.SurahViewModel;
import com.split.reader.workmanager.DownloadTranslationWork;
import com.split.reader.workmanager.TranslationRetrievalWork;

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

    void inject(TranslationRetrievalWork translationRetrievalWork);

    void inject(DownloadTranslationWork downloadTranslationWork);

    void inject(DownloadCompleteReceiver downloadCompleteReceiver);

}