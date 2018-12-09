package com.split.reader.ui;

import android.app.SearchManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.split.reader.MainApplication;
import com.split.reader.adapters.ExpandableFavoritesAdapter;
import com.split.reader.adapters.SurahsRecycleViewAdapter;
import com.split.reader.data.TranslationDatabaseListener;
import com.split.reader.model.Bookmark;
import com.split.reader.model.SurahDetail;
import com.split.reader.model.Surahs;
import com.split.reader.model.TranslationData;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.MainActivityBinding;
import com.split.reader.viewmodels.SurahViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements
        SurahsRecycleViewAdapter.OnSurahClickListener,
        TranslationDatabaseListener,ExpandableFavoritesAdapter.onBookmarkClickListener {
    public static final String SURAH = "SURAH";
    public static final String SURAHS_LIST = "SURAHS_LIST";
    public static final String LAST_READ = "last_read";
    public static final String BOOKMARK_POSITION = "bookmark";
    private SurahViewModel surahViewModel;
    private SurahsRecycleViewAdapter adapter;
    private MainActivityBinding binding;
    private DrawerLayout mDrawerLayout;
    private int lastReadVerse = -1;
    private SearchView searchView;
    @Inject
    ExpandableFavoritesAdapter expandableFavoritesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainApplication.getAppComponent().inject(this);
        surahViewModel = ViewModelProviders.of(MainActivity.this).get(SurahViewModel.class);
        adapter = new SurahsRecycleViewAdapter();
        mDrawerLayout = binding.drawerLayout;
        binding.setLifecycleOwner(this);
        binding.rvGroup.setAdapter(adapter);
        binding.bookmarkLv.setAdapter(expandableFavoritesAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rvGroup.addItemDecoration(itemDecor);
        expandableFavoritesAdapter.setListener(this);

        setSupportActionBar(binding.toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        surahViewModel.getFavoritesFromDb().observe(this, new Observer<List<SurahDetail>>() {
            @Override
            public void onChanged(@Nullable List<SurahDetail> surahDetails) {
                expandableFavoritesAdapter.setBookmarkList(surahViewModel.getBookmarks(surahDetails));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    }

    @Override
    public void onSurahClick(Surahs surah, boolean lastRead) {
        Bundle bundle = new Bundle();
        bundle.putInt(SURAH, surah.getSura());
        //bundle.putInt(BOOKMARK_POSITION,surahViewModel.getLastReadVerse());
        bundle.putBoolean(LAST_READ, lastRead);
        //todo wtf?
        ArrayList<Surahs> surahs = new ArrayList<>(surahViewModel.getSurahLiveData().getValue());
        bundle.putParcelableArrayList(SURAHS_LIST, surahs);
        startSurahDetailActivity(bundle);
    }
    private void startSurahDetailActivity(Bundle bundle){
        Intent detailActivityIntent = new Intent(this, SurahDetailActivity.class);
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
    public void onFinished(List<TranslationData> responseObject) {
        Log.d("yellow", responseObject.get(0).getDisplayName());
    }

    @Override
    public void onFailed(Throwable t) {
        Log.d("yellow", "error " + t);
    }


    @Override
    public void onClick(Bookmark bookmark) {
        Bundle bundle = new Bundle();
        bundle.putInt(SURAH, bookmark.getSurah().getSura());
        bundle.putInt(BOOKMARK_POSITION,bookmark.getPosition());
        //todo wtf?
        ArrayList<Surahs> surahs = new ArrayList<>(surahViewModel.getSurahLiveData().getValue());
        bundle.putParcelableArrayList(SURAHS_LIST, surahs);
        startSurahDetailActivity(bundle);
    }
}
