package com.split.reader.ui;


import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;

import androidx.core.app.NavUtils;
import androidx.viewpager.widget.ViewPager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;


import com.split.reader.model.Surahs;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.ActivitySurahDetailBinding;
import com.split.reader.viewmodels.SurahDetailViewModel;

import java.util.List;

import static com.split.reader.utils.Constants.SURAH_COUNT;


public class SurahDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ActivitySurahDetailBinding binding;
    SurahDetailViewModel viewModel;
    int surah;
    boolean isLastRead;
    int bookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_surah_detail);
        viewModel = ViewModelProviders.of(this).get(SurahDetailViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(this);
        binding.setViewModel(viewModel);


        final Bundle b = getIntent().getExtras();
        surah = b.getInt(MainActivity.SURAH);
        bookmark = b.getInt(MainActivity.BOOKMARK_POSITION,0);
        isLastRead = b.getBoolean(MainActivity.LAST_READ,false);
        List<Surahs> surahs = b.getParcelableArrayList(MainActivity.SURAHS_LIST);
        viewModel.setSurahs(surahs);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.setPosition(surah);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onPreviousClick() {
        int prevPosition = binding.pager.getCurrentItem() - 1;
        setCurrentItem(prevPosition);
    }

    public void onNextClick() {
        int nextPosition = binding.pager.getCurrentItem() + 1;
        setCurrentItem(nextPosition);
    }

    private void setCurrentItem(int nextPosition) {
        if(nextPosition >=0 && nextPosition<SURAH_COUNT){
            binding.pager.setCurrentItem(nextPosition,true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    public void onToolbarClick(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(viewModel.actionBarTitle.getValue());
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        arrayAdapter.addAll(viewModel.getVerseDialogItems());
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int verse) {
               viewModel.rvScrollPosition.setValue(verse);
               Log.d("mine","dialog on click");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

  /*  takes in position of the surah
    in the quran*/
    @Override
    public void onPageSelected(int position) {
        viewModel.updateBottomBar(position, binding.pager.getAdapter().getCount() - 1);
        if(viewModel.getSurahs().size()>SURAH_COUNT){
            viewModel.updateActionbar(position+1);

        }
        else{ //last bookmark location
            viewModel.updateActionbar(position);
        }
        if(isLastRead){
           int lastReadVerse = viewModel.getLastReadVerse();
            viewModel.rvScrollPosition.setValue(lastReadVerse);
            Log.e("mine","if "+lastReadVerse);

        }
        else {
            //if bookmark not selected it will move to position 0 since thats the default bundle value
            viewModel.rvScrollPosition.setValue(bookmark -1);
            Log.e("mine","else "+isLastRead);

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
