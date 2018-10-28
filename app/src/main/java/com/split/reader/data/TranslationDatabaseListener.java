package com.split.reader.data;

import com.split.reader.model.TranslationData;

import java.util.List;

public interface TranslationDatabaseListener {
    void onFinished( List<TranslationData> responseObject);
    void onFailed(Throwable t);
}
