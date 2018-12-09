package com.split.reader.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.split.reader.model.Surahs;

import java.util.List;

@Dao
public interface SurahDao {
    @Query("SELECT * FROM chapters")
    LiveData<List<Surahs>> getAllSurahs();
    @Query("SELECT * FROM chapters where sura = :suraNo")
    Surahs getSurah(Integer suraNo);
}
