/*
 Copyright 2018 tarekmabdallah91@gmail.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.gmail.tarekmabdallah91.bakingapp.data.sharedPreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.gmail.tarekmabdallah91.bakingapp.R;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.EMPTY_TEXT;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.shared_preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int preferencesScreenCount = preferenceScreen.getPreferenceCount();

        for (int i = ZERO; i < preferencesScreenCount; i++) {
            Preference preference = preferenceScreen.getPreference(i); // 1st is category for themes
            if (preference instanceof PreferenceCategory) {
                PreferenceCategory category = (PreferenceCategory) preference;
                int categoryCount = category.getPreferenceCount();
                for (int x = ZERO; x < categoryCount; x++) {
                    Preference child = category.getPreference(i); // 1st is list for themes
                    if (child instanceof ListPreference) {
                        String key = child.getKey();
                        String value = sharedPreferences.getString(key, EMPTY_TEXT);
                        setSummaryPreference(child, value);
                    }
                }
            }
        }

    }

    /**
     * to set summary of preferences when the fragment is created
     *
     * @param preference to change it's summary
     * @param value      to set the summary
     */
    private void setSummaryPreference(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            int index;
            ListPreference listPreference = (ListPreference) preference;
            if (null == value) { // set the summary when the item selected
                String selectedColor = ((ListPreference) preference).getValue();
                index = listPreference.findIndexOfValue(selectedColor);
            } else
                index = listPreference.findIndexOfValue(value);// set summary when the fragment is created
            if (index >= ZERO) {
                listPreference.setSummary(listPreference.getEntries()[index]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        setSummaryPreference(preference, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // registerOnSharedPreferenceChangeListener
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregisterOnSharedPreferenceChangeListener
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
