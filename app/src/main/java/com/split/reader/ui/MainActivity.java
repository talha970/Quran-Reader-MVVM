package com.split.reader.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.split.reader.MainApplication;
import com.split.reader.adapters.SurahsRecycleViewAdapter;
import com.split.reader.model.Surahs;
import com.split.reader.model.Verses;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.MainActivityBinding;
import com.split.reader.viewmodels.SurahViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahsRecycleViewAdapter.OnSurahClickListener {
    public static final String SURAH = "SURAH";
    public static final String SURAHS_LIST = "SURAHS_LIST";
    public static final String LAST_READ = "last_read";

    private SurahViewModel surahViewModel;
    private SurahsRecycleViewAdapter adapter;
    private MainActivityBinding binding;
    private int lastReadVerse = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainApplication.getAppComponent().inject(this);
        surahViewModel = ViewModelProviders.of(MainActivity.this).get(SurahViewModel.class);
        adapter = new SurahsRecycleViewAdapter();
        binding.setLifecycleOwner(this);
        binding.rvGroup.setAdapter(adapter);


    }
    private ArrayList<Surahs> makeCopy(List<Surahs> surahs){
        ArrayList<Surahs> copy = new ArrayList<Surahs>(surahs.size());
        Iterator<Surahs> iterator = surahs.iterator();
        while(iterator.hasNext()){
            copy.add(iterator.next().clone());
        }
        return copy;
    }
    @Override
    protected void onResume() {
        super.onResume();
        surahViewModel.getSurahLiveData().observe(this, new Observer<List<Surahs>>() {
            @Override
            public void onChanged(@Nullable List<Surahs> surahs) {



                if(surahViewModel.getLastReadSurah()!=null) {
                    Surahs sura = surahViewModel.getLastReadSurah().first;
                    lastReadVerse = surahViewModel.getLastReadSurah().second;
                    ArrayList<Surahs> list = makeCopy(surahs);
                    list.add(0,sura);
                    adapter.setSurahs(list);
                    adapter.setListener(MainActivity.this);

                    Log.d("mine","verse"+lastReadVerse);

                }
                else{
                    adapter.setSurahs(surahs);
                    adapter.setListener(MainActivity.this);
                }



            }
        });
        surahViewModel.getVersesLiveData().observe(this, new Observer<List<Verses>>() {
            @Override
            public void onChanged(@Nullable List<Verses> verses) {
                verses.size();
            }
        });
    }

    @Override
    public void onSurahClick(Surahs surah,boolean lastRead) {

        Intent detailActivityIntent = new Intent(this,SurahDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SURAH,surah.getSura());
        bundle.putBoolean(LAST_READ,lastRead);
        ArrayList<Surahs> surahs = new ArrayList<>(surahViewModel.getSurahLiveData().getValue());
        bundle.putParcelableArrayList(SURAHS_LIST,surahs);
        detailActivityIntent.putExtras(bundle);
        startActivity(detailActivityIntent);
    }
}
