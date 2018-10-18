package com.split.reader.ui.binding;

import android.databinding.BindingAdapter;
import android.databinding.adapters.Converters;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.split.reader.adapters.SurahDetailPagerAdapter;
import com.split.reader.adapters.SurahsRecycleViewAdapter;
import com.split.reader.model.Surahs;
import com.split.reader.ui.MainActivity;
import com.split.reader.ui.SurahDetailActivity;
import com.split.reader.ui.SurahPageFragment;

public class BindingUtils extends Converters {


    @BindingAdapter({"app:setSurahViewPagerAdapter","app:surahPosition"})
    public static void inertiaViewPagerAdapter(ViewPager viewPager, SurahDetailActivity activity, int position) {
        SurahDetailPagerAdapter adapter = new SurahDetailPagerAdapter(activity.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position-1);
        viewPager.addOnPageChangeListener(activity);
        activity.onPageSelected(position-1);
    }



    @BindingAdapter({"app:setSurahImg","app:setActivity"})
    public static void inertiaViewPagerAdapter(ImageView imageView, Surahs surahs,SurahsRecycleViewAdapter.OnSurahClickListener listener) {
        MainActivity activity = (MainActivity) listener;
        String packageName = activity.getPackageName();
        int resId = activity.getResources().getIdentifier(packageName+":drawable/"+"sura_"+surahs.getSura() ,
                null,null);
       imageView.setImageResource(resId);
    }

/*    @BindingAdapter({"app:navigationOnClick"})
    public static void toolbarOnBackPressed(Toolbar toolbar, final SurahPageFragment fragment) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getActivity().onBackPressed();
            }
        });
    }*/
}
