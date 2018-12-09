package com.split.reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "quran_simple_translation")
data class SurahDetail (
        @PrimaryKey
        @ColumnInfo(name = "id") var id: Int? = 0,
        @ColumnInfo(name = "sura") var suraNo: Int? = 0,
        @ColumnInfo(name = "aya") var ayaNo: Int? = 0,
        @ColumnInfo(name = "text") var text: String?="",
        @ColumnInfo(name = "translation") var translation: String?="",
        @ColumnInfo(name = "md5") var md5: String? = "",
        @ColumnInfo(name = "bookmark") var bookmark: Int? = 0,
        @Ignore
        @ColumnInfo(name = "trans") var trans: String?=""

)
