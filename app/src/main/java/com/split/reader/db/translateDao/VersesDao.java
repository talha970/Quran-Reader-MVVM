package com.split.reader.db.translateDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.split.reader.model.Verses;

import java.util.List;


@Dao
public interface VersesDao {
    @Query("SELECT * FROM verses_content")
    LiveData<List<Verses>> getAllVerses();
}
