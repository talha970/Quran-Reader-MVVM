package com.split.reader.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "translations")
public class TranslationData {

/*
    id: 54,
    displayName: "Albanian Translation",
    translator: "Sherif Ahmeti",
    languageCode: "sq",
    fileUrl: "http://android.quran.com/data/getTranslation.php?id=54",
    fileName: "quran.al.ahmeti.db",
    saveTo: "databases",
    downloadType: "translation",
    minimumVersion: 1,
    currentVersion: 2
*/

    @PrimaryKey
    private int id;

    String displayName;
     String translator;
     String languageCode;
     String fileName;
     String fileUrl;
     String saveTo;
     String downloadType;
     String minimumVersion;
     String currentVersion;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTranslator() {
        return translator;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getSaveTo() {
        return saveTo;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setSaveTo(String saveTo) {
        this.saveTo = saveTo;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setId(int id) {
        this.id = id;
    }
}
