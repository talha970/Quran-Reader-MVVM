package com.split.reader.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.split.reader.MainApplication;
import com.split.reader.adapters.SurahsRecycleViewAdapter;
import com.split.reader.data.TranslationDatabaseListener;
import com.split.reader.model.Surahs;
import com.split.reader.model.TranslationData;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.MainActivityBinding;
import com.split.reader.viewmodels.SurahViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahsRecycleViewAdapter.OnSurahClickListener, TranslationDatabaseListener {
    public static final String SURAH = "SURAH";
    public static final String SURAHS_LIST = "SURAHS_LIST";
    public static final String LAST_READ = "last_read";

    private SurahViewModel surahViewModel;
    private SurahsRecycleViewAdapter adapter;
    private MainActivityBinding binding;
    private int lastReadVerse = -1;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainApplication.getAppComponent().inject(this);
        surahViewModel = ViewModelProviders.of(MainActivity.this).get(SurahViewModel.class);
        adapter = new SurahsRecycleViewAdapter();
        binding.setLifecycleOwner(this);
        binding.rvGroup.setAdapter(adapter);


    }

    private ArrayList<Surahs> makeCopy(List<Surahs> surahs) {
        ArrayList<Surahs> copy = new ArrayList<Surahs>(surahs.size());
        Iterator<Surahs> iterator = surahs.iterator();
        while (iterator.hasNext()) {
            copy.add(iterator.next().clone());
        }
        return copy;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //surahViewModel.fetchTranslationRepo(this);
        surahViewModel.getSurahLiveData().observe(this, new Observer<List<Surahs>>() {
            @Override
            public void onChanged(@Nullable List<Surahs> surahs) {

                if (surahViewModel.getLastReadSurah() != null) {
                    Surahs sura = surahViewModel.getLastReadSurah().first;
                    lastReadVerse = surahViewModel.getLastReadSurah().second;
                    ArrayList<Surahs> list = makeCopy(surahs);
                    list.add(0, sura);
                    adapter.setSurahs(list);
                    adapter.setListener(MainActivity.this);

                    Log.d("mine", "verse" + lastReadVerse);

                } else {
                    adapter.setSurahs(surahs);
                    adapter.setListener(MainActivity.this);
                }


            }
        });
      /*  surahViewModel.getVersesLiveData().observe(this, new Observer<List<Verses>>() {
            @Override
            public void onChanged(@Nullable List<Verses> verses) {
                verses.size();
            }
        });*/
    }

    @Override
    public void onSurahClick(Surahs surah, boolean lastRead) {

        Intent detailActivityIntent = new Intent(this, SurahDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SURAH, surah.getSura());
        bundle.putBoolean(LAST_READ, lastRead);
        ArrayList<Surahs> surahs = new ArrayList<>(surahViewModel.getSurahLiveData().getValue());
        bundle.putParcelableArrayList(SURAHS_LIST, surahs);
        detailActivityIntent.putExtras(bundle);
        startActivity(detailActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinished(List<TranslationData> responseObject) {
        Log.d("yellow", responseObject.get(0).getDisplayName());
    }

    @Override
    public void onFailed(Throwable t) {
        Log.d("yellow", "error " + t);
    }
}
