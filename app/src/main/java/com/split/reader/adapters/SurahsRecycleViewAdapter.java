package com.split.reader.adapters;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.split.reader.model.Surahs;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.SurahRvItemsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.split.reader.utils.Constants.SURAH_COUNT;

public class SurahsRecycleViewAdapter extends RecyclerView.Adapter<SurahsRecycleViewAdapter.ViewHolder>{

    private List<Surahs> surahs;
    private OnSurahClickListener listener;

    @Inject
    public SurahsRecycleViewAdapter() {
        surahs = new ArrayList<>();
    }

    @Override
    public SurahsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SurahRvItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.surah_rv_items,
                parent, false);
        return new SurahsRecycleViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SurahsRecycleViewAdapter.ViewHolder holder, int position) {
        Surahs surah = surahs.get(position);
        boolean lastRead = false;

        if(position==0) {
            lastRead = surahs.size() > SURAH_COUNT ? true : false;
        }
        holder.bind(surah,listener,lastRead);
    }

    public void setSurahs(@NonNull List<Surahs> surahs) {
        this.surahs.clear();
        this.surahs.addAll(surahs);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d("mine", "size "+String.valueOf(surahs.size()));
        return surahs.size();

    }
    public void setListener(OnSurahClickListener listener) {
        this.listener = listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final SurahRvItemsBinding mBinding;

        ViewHolder(SurahRvItemsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(@NonNull final Surahs surah, final OnSurahClickListener listener, boolean lastRead) {
            mBinding.setSurah(surah);
            mBinding.setListener(listener);
            mBinding.setLastRead(lastRead);

            mBinding.executePendingBindings();
        }
    }
    public interface OnSurahClickListener {
        void onSurahClick(Surahs surahs,boolean lastRead);
    }
    }

