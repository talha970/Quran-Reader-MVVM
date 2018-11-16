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



public interface PreferencesHelper {

    String getLastRead();

    void setLastRead(String location);

    String getTranslationName();

    void setTranslationName(String translationName);

    Integer getTranslationVersion();

    void setTranslationVersion(Integer version);

    Boolean isTranslationSet();

    void setTranslationSet(Boolean translationSet);

    Boolean getFirstRun();

    void setFirstRun(Boolean firstRun);

}
