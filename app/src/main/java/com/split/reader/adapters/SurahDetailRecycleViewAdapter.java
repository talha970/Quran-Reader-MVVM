package com.split.reader.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.split.reader.model.SurahDetail;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.SurahDetailItemBinding;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SurahDetailRecycleViewAdapter extends RecyclerView.Adapter<SurahDetailRecycleViewAdapter.ViewHolder>{

    private List<SurahDetail> surahs;
    private SurahDetailRecycleViewAdapter.OnSurahDetailClickListener listener;

    @Inject
    public SurahDetailRecycleViewAdapter() {
        surahs = new ArrayList<>();
    }

    @Override
    public SurahDetailRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SurahDetailItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.surah_detail_item,
                parent, false);
        return new SurahDetailRecycleViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SurahDetailRecycleViewAdapter.ViewHolder holder, int position) {
        SurahDetail surah = surahs.get(position);
        holder.bind(surah,listener);
    }

    public void setSurahs(@NonNull List<SurahDetail> surah) {
        this.surahs.clear();
        this.surahs.addAll(surah);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return surahs.size();

    }
    public void setListener(SurahDetailRecycleViewAdapter.OnSurahDetailClickListener listener) {
        this.listener = listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final SurahDetailItemBinding mBinding;

        ViewHolder(SurahDetailItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(@NonNull final SurahDetail surah,final SurahDetailRecycleViewAdapter.OnSurahDetailClickListener listener) {
            mBinding.setSurahDetail(surah);
            mBinding.setListener(listener);
            mBinding.executePendingBindings();
        }
    }
    public interface OnSurahDetailClickListener {
        void onSurahDetailClick(SurahDetail surah, boolean isChecked);
    }
}



