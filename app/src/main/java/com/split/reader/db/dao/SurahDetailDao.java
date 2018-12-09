package com.split.reader.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.split.reader.model.SurahDetail;


import java.util.List;

@Dao
public interface SurahDetailDao {
    @Query("SELECT * FROM quran_simple_translation where  sura = :suraNo order by aya ASC")
    LiveData<List<SurahDetail>> getSurahDetail(int suraNo);
    @Query("update quran_simple_translation set bookmark = :bookmark where id = :id" )
    void setBookmark(int bookmark, int id);

    @Query("select * from quran_simple_translation where bookmark = 1" )
    LiveData<List<SurahDetail>> getFavorites();
}
