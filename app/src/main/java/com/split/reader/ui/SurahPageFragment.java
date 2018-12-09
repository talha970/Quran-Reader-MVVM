package com.split.reader.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.split.reader.adapters.SurahDetailRecycleViewAdapter;
import com.split.reader.model.SurahDetail;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.FragmentSurahPageBinding;
import com.split.reader.viewmodels.SurahDetailViewModel;

import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;


public class SurahPageFragment extends Fragment implements SurahDetailRecycleViewAdapter.OnSurahDetailClickListener {

    private static final String PAGER_FRAG_POSITION = "PAGER_FRAG_POSITION";
    private FragmentSurahPageBinding binding;
    SurahDetailViewModel viewModel;
    SurahDetailRecycleViewAdapter adapter;


    public static Fragment getNewInstance(int position) {
        SurahPageFragment fragment = new SurahPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGER_FRAG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
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
        Log.d("mine", "pagerfragpos " + String.valueOf(viewModel.currentPos.getValue()) + "bundle value =" + pos);
        final LiveData<List<SurahDetail>> liveData = viewModel.getSurahDetailLiveData(pos + 1);
       liveData.observe(this, new Observer<List<SurahDetail>>() {
            @Override
            public void onChanged(@Nullable List<SurahDetail> surahDetails) {
                adapter.setSurahs(surahDetails);
                adapter.setListener(SurahPageFragment.this);
                if (getUserVisibleHint()) {
                    int scroll = viewModel.rvScrollPosition.getValue();
                    binding.rvSurahDetails.getLayoutManager().scrollToPosition(scroll);
                    liveData.removeObserver(this);
                }
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvSurahDetails.getLayoutManager();
            Log.d("mine", "Surah " + viewModel.currentPos.getValue() + " verse " + layoutManager.findLastVisibleItemPosition());
            viewModel.setLastReadLocation(viewModel.currentPos.getValue() + "," + layoutManager.findLastVisibleItemPosition());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_surah_page, container, false);
        binding.setLifecycleOwner(this);
        adapter = new SurahDetailRecycleViewAdapter();
        DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.rvSurahDetails.addItemDecoration(itemDecor);
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
    public void onSurahDetailClick(SurahDetail surah, boolean isChecked) {
        //TODO: verse share

        viewModel.setBookmark(surah, isChecked ? 1:0);

    }
}
