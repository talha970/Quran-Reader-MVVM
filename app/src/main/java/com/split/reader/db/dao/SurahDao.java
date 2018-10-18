package com.split.reader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.split.reader.model.Surahs;

import java.util.List;

@Dao
public interface SurahDao {
    @Query("SELECT * FROM chapters")
    LiveData<List<Surahs>> getAllSurahs();
}
