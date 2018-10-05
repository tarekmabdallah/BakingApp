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


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickListener;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.RecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeViewModel;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;
import com.gmail.tarekmabdallah91.bakingapp.utils.ScreenSizeUtils;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;


// TODO 5 to add essprsso
// TODO 6 apply mvp model

public class MainActivity extends AppCompatActivity implements OnRecipeClickListener {

    @BindView(R.id.layout_activity_main)
    View layout_activity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.empty_tv)
    TextView emptyTV;
    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;

    private RecipesAdapter adapter;
    private RoomPresenter roomPresenter;
    private OnRecipeClickedOnFragment callbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.base_layout);

        setUI();
        setViewModel();
    }

    private void setUI() {
        initiateValues();
        layout_activity.setVisibility(VISIBLE); // set it visible after calling butter knife
        setRecyclerView();
    }

    private void setRecyclerView() {
        ScreenSizeUtils screenSizeUtils = new ScreenSizeUtils(this);
        int spanCount = screenSizeUtils.calculateSpanCount();
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, spanCount));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        roomPresenter.DeleteRecipeDataFromRoom(getBaseContext(), (int) viewHolder.itemView.getTag());
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setViewModel() {

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                // update UI
                if (null == recipeEntries || recipeEntries.isEmpty()) {
                    recyclerView.setVisibility(GONE);
                    emptyTV.setVisibility(VISIBLE);
                    Toast.makeText(getBaseContext(), R.string.no_data_msg, Toast.LENGTH_LONG).show();
                } else {
                    emptyTV.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    adapter.swapList(recipeEntries);
                }
            }
        });
    }

    private void initiateValues() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);
        adapter = new RecipesAdapter(this);
        roomPresenter = RoomPresenter.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
    }

    @Override
    public void onRecipeClicked(RecipeEntry recipeEntry) {
        Intent openDetailsActivity = new Intent(this, DetailsActivity.class);
        openDetailsActivity.putExtra(BakingConstants.RECIPE_KEYWORD, recipeEntry);
        startActivity(openDetailsActivity);
    }
}
