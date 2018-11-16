package com.split.reader.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "quran_simple_translation")
data class SurahDetail (
        @PrimaryKey
        @ColumnInfo(name = "id") var id: Int? = 0,
        @ColumnInfo(name = "sura") var suraNo: Int? = 0,
        @ColumnInfo(name = "aya") var ayaNo: Int? = 0,
        @ColumnInfo(name = "text") var text: String?="",
        @ColumnInfo(name = "translation") var translation: String?="",
        @ColumnInfo(name = "md5") var md5: String? = "",
        @Ignore
        @ColumnInfo(name = "trans") var trans: String?=""

)
