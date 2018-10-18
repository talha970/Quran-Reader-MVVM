package com.split.reader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.split.reader.model.SurahDetail;


import java.util.List;

@Dao
public interface SurahDetailDao {
    @Query("SELECT * FROM quran_simple_translation where  sura = :suraNo order by aya ASC")
    LiveData<List<SurahDetail>> getSurahDetail(int suraNo);
}
