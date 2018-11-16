package com.split.reader.downloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

public class TranslationDownloader {

    DownloadManager downloadManager;
    Context context;

    @Inject
    public TranslationDownloader(DownloadManager downloadManager, Context context) {
        this.downloadManager = downloadManager;
        this.context = context;
    }


    public void download(String title, String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection ucon = (HttpURLConnection) url1.openConnection();
            ucon.setInstanceFollowRedirects(false);
            URL secondURL = new URL(ucon.getHeaderField("Location"));
            String httpsURL = secondURL.toString().replaceAll("http","https");
            Uri uri = Uri.parse(httpsURL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(String.format("Downloading %s", title));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            // Set path
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS+ File.separator+ "databases",
                    uri.getLastPathSegment());
            downloadManager.enqueue(request);
        }
        catch (Exception e){
            Log.d("yellow",e.toString());
        }

    }
}
