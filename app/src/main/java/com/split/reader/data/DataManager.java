package com.split.reader.data;

import android.arch.lifecycle.LiveData;

import com.split.reader.db.AppDatabase;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.model.TranslationData;
import com.split.reader.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;

public class DataManager {

    private AppDatabase appDatabase;
    private PreferencesHelper preferencesHelper;


    private TranslationRepo translationRepo;

    @Inject
    public DataManager(AppDatabase appDatabase, PreferencesHelper preferencesHelper, TranslationRepo translationRepo) {
        this.appDatabase = appDatabase;
        this.preferencesHelper = preferencesHelper;
        this.translationRepo = translationRepo;
    }

    public LiveData<List<Surahs>> getAllSurahs(){
        return appDatabase.SurahDao().getAllSurahs();
    }


    public LiveData<List<SurahDetail>> getSuraDetail(int suraNo){
        return appDatabase.SurahDetailDao().getSurahDetail(suraNo);
    }


    public void setLastReadLocation(String location){
        preferencesHelper.setLastRead(location);
    }

    public String getLastReadLocation(){
        return preferencesHelper.getLastRead();
    }

    public void fetchTranslationRepo(TranslationDatabaseListener databaseListener){
        translationRepo.fetchTranslationDataWithCallback(databaseListener);
    }
    public LiveData<List<TranslationData>> fetchTranslationSync(){
        return translationRepo.fetchTranslationDataSynchronous();
    }
}
