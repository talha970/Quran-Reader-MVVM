package com.split.reader.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.split.reader.db.dao.SurahDao
import com.split.reader.db.dao.SurahDetailDao
import com.split.reader.model.SurahDetail
import com.split.reader.model.Surahs

@Database(entities = arrayOf(
        Surahs::class,SurahDetail::class), version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun SurahDao(): SurahDao
    abstract fun SurahDetailDao(): SurahDetailDao

}