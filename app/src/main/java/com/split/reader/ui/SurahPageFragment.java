package com.split.reader.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.split.reader.adapters.SurahDetailRecycleViewAdapter;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.FragmentSurahPageBinding;
import com.split.reader.viewmodels.SurahDetailViewModel;

import java.util.List;


public class SurahPageFragment extends Fragment implements SurahDetailRecycleViewAdapter.OnSurahDetailClickListener {

    private static final String PAGER_FRAG_POSITION = "PAGER_FRAG_POSITION";
    private FragmentSurahPageBinding binding;
    SurahDetailViewModel viewModel;
    SurahDetailRecycleViewAdapter adapter;
    private  List<Surahs> surahs;

    public void setSurahs(List<Surahs> surahs) {
        this.surahs = surahs;
    }

    public static Fragment getNewInstance(int position) {
        SurahPageFragment fragment = new SurahPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGER_FRAG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }



    private void setupToolbar(){
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
       /* appCompatActivity.setSupportActionBar(binding.toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SurahDetailViewModel.class);
        int pos = getArguments().getInt(PAGER_FRAG_POSITION);
        Log.d("yellow", "pagerfragpos "+String.valueOf(viewModel.currentPos.getValue())+ "bundle vale ="+pos);
        viewModel.getSurahDetailLiveData(pos+1).observe(this, new Observer<List<SurahDetail>>() {
            @Override
            public void onChanged(@Nullable List<SurahDetail> surahDetails) {

                    adapter.setSurahs(surahDetails);
                    adapter.setListener(SurahPageFragment.this);
                    if(getUserVisibleHint()) {
                        int scroll = viewModel.rvScrollPosition.getValue();
                        binding.rvSurahDetails.getLayoutManager().scrollToPosition(scroll);

                    }

            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint()){
            LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvSurahDetails.getLayoutManager();
            Log.d("mine", "Surah "+viewModel.currentPos.getValue()+" verse "+layoutManager.findLastVisibleItemPosition());
            viewModel.setLastReadLocation(viewModel.currentPos.getValue()+","+layoutManager.findLastVisibleItemPosition());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_surah_page, container,false);
        binding.setLifecycleOwner(this);
        adapter = new SurahDetailRecycleViewAdapter();
        binding.setFragment(SurahPageFragment.this);
        binding.setViewModel(viewModel);
        binding.rvSurahDetails.setAdapter(adapter);
       // setupToolbar();
        return binding.getRoot();
        }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.rvScrollPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.rvSurahDetails.scrollToPosition(integer);
            }
        });

    }

    @Override
    public void onSurahDetailClick(SurahDetail surah) {
        //TODO: verse share
    }
}
