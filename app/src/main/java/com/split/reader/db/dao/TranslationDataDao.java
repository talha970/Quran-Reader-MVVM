package com.split.reader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.split.reader.model.TranslationData;

import java.util.List;

@Dao
public interface TranslationDataDao {
    @Query("select * from translations")
    List<TranslationData> getTranslationData();

    @Query("select * from translations where fileName = :name")
    TranslationData getTranslationObject(String name);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAppData(List<TranslationData> responseObject);
}
