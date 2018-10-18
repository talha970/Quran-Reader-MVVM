package com.split.reader.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.split.reader.db.translateDao.VersesDao
import com.split.reader.model.Verses

@Database(entities = arrayOf(Verses::class), version = 3)
abstract class TranslationDatabase : RoomDatabase() {

    abstract fun VersesDao(): VersesDao

}