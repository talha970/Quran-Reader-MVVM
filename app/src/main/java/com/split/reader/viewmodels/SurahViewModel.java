package com.split.reader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;
import android.util.Log;

import com.split.reader.MainApplication;
import com.split.reader.data.DataManager;
import com.split.reader.model.Surahs;
import com.split.reader.model.Verses;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

public class SurahViewModel extends ViewModel {

    private static final String TAG = SurahViewModel.class.getCanonicalName();
    @Inject
    DataManager dataManager;
    private LiveData<List<Surahs>> surahLiveData;
    private LiveData<List<Verses>> versesLiveData;

    public SurahViewModel() {
        MainApplication.getAppComponent().inject(this);
        surahLiveData = dataManager.getAllSurahs();
        versesLiveData = dataManager.getAllVerses();
    }

    public LiveData<List<Verses>> getVersesLiveData() {
        return versesLiveData;
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
}
