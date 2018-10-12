/*
 * Copyright 2018 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.gmail.tarekmabdallah91.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter.OnStepClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.fragments.DetailsFragment;
import com.gmail.tarekmabdallah91.bakingapp.fragments.RecipeStepFragment;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INVALID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.POSITION_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;

public class DetailsActivity extends AppCompatActivity implements OnStepClickedOnFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView nextBtn, backBtn;

    private boolean twoPane = false;
    private RecipeEntry recipeEntry;
    private int positionStep = INVALID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_master_sw600) != null) {
            twoPane = true;
            // not recommended to make it global to let the fragments replaced easily
            FragmentManager fragmentManager = getSupportFragmentManager();

            // initiate and commitNow the mainFragment
            DetailsFragment detailsFragment = new DetailsFragment();
            fragmentManager.beginTransaction().replace(R.id.container_content_details, detailsFragment).commitNow();

            // initiate and commitNow detailsFragment after setting it by recipeEntry as it's state
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            nextBtn = findViewById(R.id.next_instruction_tv);
            backBtn = findViewById(R.id.back_instruction_tv);
            nextBtn.setVisibility(GONE);
            backBtn.setVisibility(GONE);
            if (savedInstanceState != null) {
                recipeEntry = savedInstanceState.getParcelable(RECIPE_KEYWORD);
                positionStep = savedInstanceState.getInt(POSITION_KEYWORD, INVALID);
            }
            recipeStepFragment.setFragmentData(recipeEntry, positionStep);
            fragmentManager.beginTransaction().replace(R.id.container_content_recipes_steps, recipeStepFragment).commitNow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DrawerUtil drawerUtil = new DrawerUtil(this, toolbar);
        drawerUtil.buildDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != recipeEntry) outState.putParcelable(RECIPE_KEYWORD, recipeEntry);
        if (positionStep > INVALID) outState.putInt(POSITION_KEYWORD, positionStep);
    }

    @Override
    public void onStepClickedOnFragment(RecipeEntry recipe, int positionStep) {
        // if the tablet mode do this code ..
        if (twoPane) {
            this.recipeEntry = recipe; // to be saved if the device rotated
            this.positionStep = positionStep;
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setFragmentData(recipeEntry, positionStep);
            fragmentManager.beginTransaction().replace(R.id.container_content_recipes_steps, recipeStepFragment).commitNow();
        }
    }
}
