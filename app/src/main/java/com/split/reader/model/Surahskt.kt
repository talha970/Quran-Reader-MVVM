package com.split.reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "chapters")
 data class Surahskt (
        @NonNull
        @PrimaryKey @ColumnInfo(name = "sura") var sura: Int? = 0,
        @ColumnInfo(name = "ayas_count") var ayasCount: Int? = 0,
        @ColumnInfo(name = "first_aya_id") var firstAyaId: Int? = 0,
        @ColumnInfo(name = "name_arabic") var nameArabic: String? = "",
        @ColumnInfo(name = "name_transliteration") var nameTransliteration: String? = "",
        @ColumnInfo(name = "type") var type: String? = "",
        @ColumnInfo(name = "revelation_order") var revelationOrder: Int? = 0,
        @ColumnInfo(name = "rukus") var rukus: Int? = 0

)

