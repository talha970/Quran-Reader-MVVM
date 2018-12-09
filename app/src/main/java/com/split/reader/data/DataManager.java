package com.split.reader.data;

import androidx.lifecycle.LiveData;
import android.content.Context;

import com.split.reader.db.AppDatabase;
import com.split.reader.db.dao.BookmarkDao;
import com.split.reader.db.sqlite.DatabaseAccess;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.model.TranslationData;
import com.split.reader.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;

public class DataManager {

    private AppDatabase appDatabase;
    private PreferencesHelper preferencesHelper;
    private Context context;
    private TranslationRepo translationRepo;
    private BookmarkDao bookmarkDao;

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

    public void setBookmark(SurahDetail surah, int value) {
        appDatabase.SurahDetailDao().setBookmark(value,surah.getId());
    }

    public LiveData<List<SurahDetail>> getFavorites(){
       return appDatabase.SurahDetailDao().getFavorites();
    }

    public Surahs getSurah(Integer suraNo) {
        return appDatabase.SurahDao().getSurah(suraNo);
    }

   /* public void setBookmark(final SurahDetail surah) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bookmark bookmark = new Bookmark(surah.getSuraNo(),surah.getAyaNo());
                appDatabase.BookmarkDao().insertBookmark(bookmark);

            }
        }) .start();
        }

    public LiveData<List<Bookmark>> getBookmarks() {

                return appDatabase.BookmarkDao().getBookmarks();

    }*/

    }

