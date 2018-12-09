package com.split.reader.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Bookmark {
   Surahs surah;
   int position;

    public Bookmark(Surahs surah, int position) {
        this.surah = surah;
        this.position = position;
    }

    public Surahs getSurah() {
        return surah;
    }

    public void setSurah(Surahs surah) {
        this.surah = surah;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
