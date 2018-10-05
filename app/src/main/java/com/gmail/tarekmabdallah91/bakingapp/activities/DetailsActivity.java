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

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter.OnStepClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.fragments.DetailsFragment;
import com.gmail.tarekmabdallah91.bakingapp.fragments.RecipeStepFragment;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;

public class DetailsActivity extends AppCompatActivity implements OnStepClickedOnFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean twoPane = false;
    private RecipeEntry recipeEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.content_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);


        if (findViewById(R.id.fragment_master_sw600) != null
                || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            twoPane = true;
            // not recommended to make it global to let the fragments replaced easily
            FragmentManager fragmentManager = getSupportFragmentManager();

            // initiate and commitNow the mainFragment
            DetailsFragment detailsFragment = new DetailsFragment();
            fragmentManager.beginTransaction().replace(R.id.container_content_details, detailsFragment).commitNow();

            // initiate and commitNow detailsFragment after setting it by recipeEntry as it's state
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            if (savedInstanceState != null)
                recipeEntry = savedInstanceState.getParcelable(RECIPE_KEYWORD);
            fragmentManager.beginTransaction().replace(R.id.container_content_recipes_steps, recipeStepFragment).commitNow();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
    }

    @Override
    public void onStepClickedOnFragment(RecipeEntry recipe, int position) {
        // if the tablet mode do this code ..
        if (twoPane) {
            this.recipeEntry = recipe; // to be saved if the device rotated
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setFragmentData(recipeEntry, position);
            fragmentManager.beginTransaction().replace(R.id.container_content_recipes_steps, recipeStepFragment).commitNow();
        }
    }
}
