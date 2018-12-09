package com.split.reader.ui.binding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.adapters.Converters;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.widget.CompoundButton;
import android.widget.ImageView;

import com.split.reader.adapters.SurahDetailPagerAdapter;
import com.split.reader.adapters.SurahDetailRecycleViewAdapter;
import com.split.reader.adapters.SurahsRecycleViewAdapter;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.ui.MainActivity;
import com.split.reader.ui.SurahDetailActivity;

public class BindingUtils extends Converters {


    @BindingAdapter({"app:setSurahViewPagerAdapter","app:surahPosition"})
    public static void inertiaViewPagerAdapter(ViewPager viewPager, SurahDetailActivity activity, int position) {
        SurahDetailPagerAdapter adapter = new SurahDetailPagerAdapter(activity.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position-1);
        viewPager.addOnPageChangeListener(activity);
        // have to do it here since adapter would be null in onCreate()
        activity.onPageSelected(position-1);
    }



    @BindingAdapter(value = {"app:onCheckedChanged", "app:setSuraDetails"})
    public static void suraDetailsChecked(AppCompatCheckBox appCompatCheckBox,
                                         final SurahDetailRecycleViewAdapter.OnSurahDetailClickListener listener,
                                         final SurahDetail surahDetail) {

        appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed())
                {
                    listener.onSurahDetailClick(surahDetail,isChecked);
                }
            }
        });
    }

    @BindingAdapter({"app:setSurahImg","app:setActivity"})
    public static void inertiaViewPagerAdapter(ImageView imageView, Surahs surahs,SurahsRecycleViewAdapter.OnSurahClickListener listener) {
        MainActivity activity = (MainActivity) listener;
        String packageName = activity.getPackageName();
        int resId = activity.getResources().getIdentifier(packageName+":drawable/"+"sura_"+surahs.getSura() ,
                null,null);
       imageView.setImageResource(resId);
    }


}
