package com.split.reader.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "verses_content")
public class Verses {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "docid")
    Integer docid = 0;

    @ColumnInfo(name = "c0sura")
    String sura = "";

    @ColumnInfo(name = "c1ayah")
    String ayah = "";
    @ColumnInfo(name = "c2text")
    String text = "";

    @NonNull
    public String getSura() {
        return sura;
    }

    @NonNull
    public Integer getDocid() {
        return docid;
    }

    public void setDocid(@NonNull Integer docid) {
        this.docid = docid;
    }

    public void setSura(@NonNull String sura) {
        this.sura = sura;
    }

    @NonNull
    public String getAyah() {
        return ayah;
    }

    public void setAyah(@NonNull String ayah) {
        this.ayah = ayah;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
