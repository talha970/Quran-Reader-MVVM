package com.split.reader.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.split.reader.model.TranslationData;

import java.lang.reflect.Type;
import java.util.List;

public class ResponseConverters {

    @TypeConverter
    public List<TranslationData> fromTranslationDataString(String value) {
        Type listType = new TypeToken<List<TranslationData>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromTranslationDataList(List<TranslationData> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }
}
