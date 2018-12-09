package com.split.reader.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.split.reader.db.dao.BookmarkDao
import com.split.reader.db.dao.SurahDao
import com.split.reader.db.dao.SurahDetailDao
import com.split.reader.db.dao.TranslationDataDao
import com.split.reader.model.Bookmark
import com.split.reader.model.SurahDetail
import com.split.reader.model.Surahs
import com.split.reader.model.TranslationData

@Database(entities = arrayOf(
        Surahs::class,SurahDetail::class, TranslationData::class), version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun SurahDao(): SurahDao
    abstract fun SurahDetailDao(): SurahDetailDao
    abstract fun TranslationDataDao(): TranslationDataDao
   // abstract fun BookmarkDao(): BookmarkDao
}