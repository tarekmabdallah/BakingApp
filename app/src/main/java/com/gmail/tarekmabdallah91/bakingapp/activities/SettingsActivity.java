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
package com.gmail.tarekmabdallah91.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.layout_activity_settings)
    View layout_activity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.base_layout);
        ButterKnife.bind(this);
        layout_activity.setVisibility(VISIBLE);
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
    }
}
