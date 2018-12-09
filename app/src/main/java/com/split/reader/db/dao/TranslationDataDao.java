package com.split.reader.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
