package com.split.reader.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.split.reader.db.dao.TranslationDataDao;
import com.split.reader.model.TranslationData;

import java.util.List;

import javax.inject.Inject;

public class TranslationRepo {
    private DummyTranslationData dummyTranslationData;
    private TranslationDataDao translationDataDao;

    @Inject
    public TranslationRepo(DummyTranslationData dummyTranslationData, TranslationDataDao translationDataDao) {
        this.dummyTranslationData = dummyTranslationData;
        this.translationDataDao = translationDataDao;
    }

    public LiveData<List<TranslationData>> fetchTranslationDataSynchronous(){
        return translationDataDao.getTranslationData();

    }
    public void fetchTranslationDataWithCallback(TranslationDatabaseListener translationDatabaseListener){
        new DatabaseTask(dummyTranslationData.fetchTranslationDummyData(),translationDataDao,translationDatabaseListener).execute();
    }

    /**
     * Async task that saves our network object to Room database and calls onFinished() once
     * done saving to persistent storage.
     */
    private static class DatabaseTask extends AsyncTask<Void, Void, Void> {

        private List<TranslationData> responseObject;
        private TranslationDataDao translationDataDao;
        private TranslationDatabaseListener databaseListener;

        DatabaseTask(List<TranslationData> responseObject, TranslationDataDao translationDataDao,
                     TranslationDatabaseListener databaseListener) {
            this.responseObject = responseObject;
            this.translationDataDao = translationDataDao;
            this.databaseListener = databaseListener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                translationDataDao.saveAppData(responseObject);
                databaseListener.onFinished(responseObject);
            } catch (Exception e) {
                databaseListener.onFailed(e);
            }
            return null;
        }
    }
}
