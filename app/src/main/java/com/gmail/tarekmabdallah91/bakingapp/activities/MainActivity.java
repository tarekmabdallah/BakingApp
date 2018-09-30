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
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.fragments.DetailsFragment;
import com.gmail.tarekmabdallah91.bakingapp.fragments.MainFragment;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;

// TODO 4 to adjust UI and change widget example & add selector
// TODO 5 to add essprsso
// TODO 6 apply mvp model

public class MainActivity extends AppCompatActivity implements OnRecipeClickedOnFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean twoPane = false;
    private static Bundle lastInsertedData;
    private RoomPresenter roomPresenter;
    private RecipeEntry recipeEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);
        roomPresenter = RoomPresenter.getInstance(this);

        if (findViewById(R.id.fragment_master_sw600) != null
                || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            twoPane = true;
            // not recommended to make it global to let the fragments replaced easily
            FragmentManager fragmentManager = getSupportFragmentManager();

            // initiate and commitNow the mainFragment
            MainFragment mainFragment = new MainFragment();
            fragmentManager.beginTransaction().replace(R.id.container_content_main, mainFragment).commitNow();

            // initiate and commitNow detailsFragment after setting it by recipeEntry as it's state
            DetailsFragment detailsFragment = new DetailsFragment();
            if (savedInstanceState != null)
                recipeEntry = savedInstanceState.getParcelable(RECIPE_KEYWORD);
            detailsFragment.setRecipeEntry(recipeEntry);
            fragmentManager.beginTransaction().add(R.id.container_content_details, detailsFragment).commitNow();
        }

        // to get data from notification message while app is running in background
        getDataFromNotificationMessage();
    }

    /**
     * to get data from notification message while app is running in background
     */
    private void getDataFromNotificationMessage(){
        Intent coming = getIntent();
        if (null != coming){
            Bundle data = coming.getExtras();
            if (null == lastInsertedData || !lastInsertedData.equals(data)) {
                // to avoid reloading and inserting data each time the device rotated
                lastInsertedData = data;
                roomPresenter.getRecipeDataFromMessageStoreItInRoom(this, data, false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
    }

    @Override
    public void onRecipeClickedOnFragment(RecipeEntry recipeEntry) {
        // if the tablet mode do this code ..
        // TODO - to fix the recyclerView as it hides the 1st item when it is clicked !
        if (twoPane) {
            this.recipeEntry = recipeEntry;

            FragmentManager fragmentManager = getSupportFragmentManager();

            MainFragment mainFragment = new MainFragment(); // to reload the recycler view because 1st item disappears when clicked
            fragmentManager.beginTransaction().replace(R.id.container_content_main, mainFragment).commitNow();

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setRecipeEntry(recipeEntry);
            fragmentManager.beginTransaction().replace(R.id.container_content_details, detailsFragment).commitNow();
        }
    }
}
