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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.utils.JsonUtils;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class SplashActivity extends AppCompatActivity {

    private static Bundle lastInsertedData;
    private RoomPresenter roomPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        roomPresenter = RoomPresenter.getInstance(this);

        int roomDbSize = roomPresenter.getRecipesCount(this);
        if (roomDbSize <= ZERO) loadSampleRecipesFromAssetsSaveThemInRoom();

        // to get data from notification message while app is running in background
        getDataFromNotificationMessage();
    }

    private void loadSampleRecipesFromAssetsSaveThemInRoom() {
        JsonUtils.saveSampleRecipesInRoom(this);
    }

    /**
     * to get data from notification message while app is running in background
     */
    private void getDataFromNotificationMessage() {
        Intent coming = getIntent();
        if (null != coming) {
            Bundle data = coming.getExtras();
            if (null == lastInsertedData || !lastInsertedData.equals(data)) {
                // to avoid reloading and inserting data each time the device rotated
                lastInsertedData = data;
                roomPresenter.getRecipeDataFromMessageStoreItInRoom(this, data, false);
            }
        }
    }

    public void onClickLoginBtn(View view) {
        Intent openMainActivity = new Intent(this, MainActivity.class);
        startActivity(openMainActivity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
