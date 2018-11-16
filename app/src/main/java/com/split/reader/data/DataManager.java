package com.split.reader.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.split.reader.db.AppDatabase;
import com.split.reader.db.sqlite.DatabaseAccess;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.model.TranslationData;
import com.split.reader.prefs.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DataManager {

    private AppDatabase appDatabase;
    private PreferencesHelper preferencesHelper;
    private Context context;
    private TranslationRepo translationRepo;

    @Inject
    public DataManager(AppDatabase appDatabase, PreferencesHelper preferencesHelper
            , TranslationRepo translationRepo, Context context) {
        this.appDatabase = appDatabase;
        this.preferencesHelper = preferencesHelper;
        this.translationRepo = translationRepo;
        this.context = context;
    }

    public LiveData<List<Surahs>> getAllSurahs() {
        return appDatabase.SurahDao().getAllSurahs();
    }


    public LiveData<List<SurahDetail>> getSuraDetail(int suraNo) {
        return appDatabase.SurahDetailDao().getSurahDetail(suraNo);
    }

    public List<String> getTranslationVerses(int sura) {
        if (preferencesHelper.isTranslationSet()) {
            String name = preferencesHelper.getTranslationName();
            Integer version = preferencesHelper.getTranslationVersion();
            if (!name.isEmpty() && version != -1) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context, name, version);
                databaseAccess.open();
                List<String> verses = databaseAccess.getVerses(sura);
                databaseAccess.close();
                return verses;
            }
        }
        return null;
    }

    public void setLastReadLocation(String location) {
        preferencesHelper.setLastRead(location);
    }

    public String getLastReadLocation() {
        return preferencesHelper.getLastRead();
    }

    public void fetchTranslationRepo(TranslationDatabaseListener databaseListener) {
        translationRepo.fetchTranslationDataWithCallback(databaseListener);
    }

    public List<TranslationData> fetchTranslationSync() {
        return translationRepo.fetchTranslationDataSynchronous();
    }
}
