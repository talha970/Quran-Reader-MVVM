package com.split.reader.data;

import android.arch.lifecycle.LiveData;

import com.split.reader.db.AppDatabase;
import com.split.reader.db.TranslationDatabase;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.model.Verses;
import com.split.reader.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;

public class DataManager {

    private AppDatabase appDatabase;
    private TranslationDatabase translationDatabase;
    private PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(AppDatabase appDatabase, PreferencesHelper preferencesHelper,TranslationDatabase translationDatabase) {
        this.appDatabase = appDatabase;
        this.preferencesHelper = preferencesHelper;
        this.translationDatabase = translationDatabase;
    }

    public LiveData<List<Surahs>> getAllSurahs(){
        return appDatabase.SurahDao().getAllSurahs();
    }
    public LiveData<List<Verses>> getAllVerses(){
        return translationDatabase.VersesDao().getAllVerses();
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
}
