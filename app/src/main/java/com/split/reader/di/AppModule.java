package com.split.reader.di;


import android.content.Context;


import com.huma.room_for_asset.RoomAsset;
import com.split.reader.db.AppDatabase;
import com.split.reader.db.TranslationDatabase;
import com.split.reader.db.dao.TranslationDataDao;
import com.split.reader.prefs.AppPreferencesHelper;
import com.split.reader.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    TranslationDatabase provideTranslationDatabase(Context context) {
        return RoomAsset.databaseBuilder(context,
                TranslationDatabase.class, "quran.en.yusufali.db")
                .build();
    }
    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return RoomAsset.databaseBuilder(context,
                AppDatabase.class, "quran.sqlite")
                .build();
    }


    @Provides
    @Singleton
    TranslationDataDao provideTranslationDataDao(AppDatabase appDatabase) {
        return appDatabase.TranslationDataDao();
    }
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new AppPreferencesHelper(context);
    }
}
