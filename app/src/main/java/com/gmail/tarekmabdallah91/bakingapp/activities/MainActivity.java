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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickListener;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.RecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.PresenterRoom;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeViewModel;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnRecipeClickListener {

    @BindView(R.id.layout_activity_main)
    View layout_activity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;


    private RecipesAdapter adapter;
    private static Bundle lastInsertedData;
    private PresenterRoom presenterRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.activity_for_all);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        setUI();
        setViewModel();

        // to get data from notification message while app is running in background
        getDataFromNotificationMessage();
    }


    private void initiateValues(){
        adapter = new RecipesAdapter(this);
        presenterRoom = PresenterRoom.getInstance(this);
        // TODO 2 to add fragments
        // TODO 4 to adjust UI and change widget example
        // TODO 5 to add essprsso
        // TODO 6 apply mvp model

    }

    private void setUI() {
        layout_activity.setVisibility(VISIBLE);
        initiateValues();
        setNavBar();
        setRecyclerView();
    }

    private void setRecyclerView (){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenterRoom.DeleteRecipeDataFromRoom(getApplicationContext(),(int) viewHolder.itemView.getTag());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void setNavBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setViewModel (){

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                // update UI
                if (null == recipeEntries || recipeEntries.isEmpty() ){
                    Toast.makeText(getBaseContext(),"no data" , Toast.LENGTH_LONG).show();
                }else{
                    adapter.swapList(recipeEntries);
                }
            }
        });
    }

    @Override
    public void onRecipeClicked(RecipeEntry recipeEntry) {
        Intent openDetailsActivity = new Intent(this , DetailsActivity.class);
        openDetailsActivity.putExtra(BakingConstants.RECIPE_KEYWORD,recipeEntry);
        startActivity(openDetailsActivity);
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
                presenterRoom.getRecipeDataFromMessageStoreItInRoom(this, data, false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        ThemesUtils.setNavSelections(this, id);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
