package com.split.reader.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.util.Pair;
import android.util.Log;

import com.split.reader.MainApplication;
import com.split.reader.data.DataManager;
import com.split.reader.model.Bookmark;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SurahViewModel extends ViewModel {

    private static final String TAG = SurahViewModel.class.getCanonicalName();
    @Inject
    DataManager dataManager;

    private LiveData<List<Surahs>> surahLiveData;

    public SurahViewModel() {
        MainApplication.getAppComponent().inject(this);
        surahLiveData = dataManager.getAllSurahs();


    }


    public LiveData<List<Surahs>> getSurahLiveData() {
        return surahLiveData;
    }

    public Pair<Surahs,Integer> getLastReadSurah(){

        String lastLocation;
        lastLocation = dataManager.getLastReadLocation();
        if(!lastLocation.isEmpty()){
            try {
                int sura = Integer.parseInt(lastLocation.split(",")[0]);
                int verse = Integer.parseInt(lastLocation.split(",")[1]);
                 return new Pair<>(surahLiveData.getValue().get(sura),verse);

            }
            catch (NumberFormatException e){
                Log.e(TAG,e.toString());
            }

        }
        return null;
    }

    public LiveData<List<SurahDetail>> getFavoritesFromDb(){
        return dataManager.getFavorites();
    }
    public List<Bookmark> getBookmarks(List<SurahDetail> favorites){
        List<Bookmark> bookmarks = new ArrayList<>();
        for(SurahDetail surahDetail: favorites){
            Surahs surah = dataManager.getSurah(surahDetail.getSuraNo());
            bookmarks.add(new Bookmark(surah,surahDetail.getAyaNo()));
        }
        return bookmarks;
    }

}
