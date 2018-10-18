/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.split.reader.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_LAST_READ = "quran_last_read";



    private final SharedPreferences mPrefs;
    private final String prefFileName = "QURAN_PREFS";

    @Inject
    public AppPreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getLastRead() {
        return  mPrefs.getString(PREF_KEY_LAST_READ, "");
    }

    @Override
    public void setLastRead(String location) {
        mPrefs.edit().putString(PREF_KEY_LAST_READ, location).apply();
    }


}
