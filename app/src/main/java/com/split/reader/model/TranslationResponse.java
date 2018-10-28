package com.split.reader.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.split.reader.data.ResponseConverters;

import java.util.List;


public class TranslationResponse {
    @TypeConverters(ResponseConverters.class)
    @SerializedName("data")
    private List<TranslationData> data;

    public List<TranslationData> getTranslationData() {
        return data;
    }

    public void setTranslationData(List<TranslationData> translationData) {
        this.data = translationData;
    }
}
