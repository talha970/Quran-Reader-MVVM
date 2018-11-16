package com.split.reader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.view.View;
import com.split.reader.MainApplication;
import com.split.reader.data.DataManager;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.split.reader.utils.Constants.SURAH_COUNT;

public class SurahDetailViewModel extends ViewModel {

   public MutableLiveData<Integer> previousButtonVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> nextButtonVisibility = new MutableLiveData<>();
    public MutableLiveData<String> actionBarTitle = new MutableLiveData<>();
    public MutableLiveData<Integer> currentPos = new MutableLiveData<>();
    public MutableLiveData<Integer> rvScrollPosition = new MutableLiveData<>();
    private List<Surahs> surahs;

    public void setSurahs(List<Surahs> surahs) {
        this.surahs = surahs;
    }

    public List<Surahs> getSurahs() {
        return surahs;
    }

    @Inject
    DataManager dataManager;
    public SurahDetailViewModel() {
        MainApplication.getAppComponent().inject(this);

    }

    public LiveData<List<SurahDetail>> getSurahDetailLiveData(final int pos) {
        final MediatorLiveData<List<SurahDetail>> mediator = new MediatorLiveData<>();
        mediator.addSource(dataManager.getSuraDetail(pos), new Observer<List<SurahDetail>>() {
            @Override
            public void onChanged(@Nullable List<SurahDetail> surahDetails) {
                List<String> transList = dataManager.getTranslationVerses(pos);
                if(transList != null){
                    for(int i =0; i<surahDetails.size();i++){
                        surahDetails.get(i).setTrans(transList.get(i));
                    }
                }
                mediator.setValue(surahDetails);
            }
        });
        return mediator;
    }

    public void updateBottomBar(int position, int totalCount) {
        currentPos.setValue(position);
        if(position==0)
        {
            previousButtonVisibility.setValue(View.GONE);
            nextButtonVisibility.setValue(View.VISIBLE);
        }
        else {
            previousButtonVisibility.setValue(View.VISIBLE);
            nextButtonVisibility.setValue(View.VISIBLE);
        }

        if(position == totalCount){
            previousButtonVisibility.setValue(View.VISIBLE);
            nextButtonVisibility.setValue(View.GONE);
        }
        else{
            nextButtonVisibility.setValue(View.VISIBLE);
        }
    }

    public void updateActionbar(int position) {
        Surahs surah = surahs.get(position);
        if(surah!=null)
        {
            actionBarTitle.setValue(surah.getNameTransliteration());
        }

    }

    public List<String> getVerseDialogItems() {
        int pos = currentPos.getValue();
        int totalVerses = 0;
        if(surahs.size()>SURAH_COUNT){ //last read item
            totalVerses = surahs.get(pos+1).getAyasCount();
        }
        else{
            totalVerses = surahs.get(pos).getAyasCount();
        }
        List<String> verses = new ArrayList<>();
        for(int i=1;i<=totalVerses;i++){
            verses.add("Verse "+i);
        }
        return verses;

    }

    public void setLastReadLocation(String location) {
        dataManager.setLastReadLocation(location);
    }

    public int getLastReadVerse(){
        try {
            String lastRead = dataManager.getLastReadLocation();
            return Integer.parseInt(lastRead.split(",")[1]);
        }
        catch (NumberFormatException e){
            return 0;
        }
    }

}
