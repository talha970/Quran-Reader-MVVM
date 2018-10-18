package com.split.reader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
