package com.split.reader.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.split.reader.ui.SurahPageFragment;


public class SurahDetailPagerAdapter extends FragmentStatePagerAdapter {

    private int pageCount = 114;

    public SurahDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SurahPageFragment.getNewInstance(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
