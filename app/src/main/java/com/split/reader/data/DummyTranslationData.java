package com.split.reader.data;

import android.content.Context;

import com.google.gson.Gson;
import com.split.reader.model.TranslationData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DummyTranslationData {

    private Context context;

    public DummyTranslationData(Context context) {
        this.context = context;
    }

    public List<TranslationData> fetchTranslationDummyData() {
        Gson gson = new Gson();
        return gson.fromJson(readDataFromFile(), TranslationData.class);
    }


    private String readDataFromFile() {
        String json;
        try {
            InputStream is = context.getAssets().open("translation_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
