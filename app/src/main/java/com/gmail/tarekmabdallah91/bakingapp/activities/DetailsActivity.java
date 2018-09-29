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
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.ImagesRecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.OnImagesRecipesClickListener;
import com.gmail.tarekmabdallah91.bakingapp.exoplayer.RecipePlayer;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.DrawerUtil;
import com.gmail.tarekmabdallah91.bakingapp.utils.ThemesUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.COMMA;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SDK_MARSHMALLOW;

public class DetailsActivity extends AppCompatActivity
        implements /*NavigationView.OnNavigationItemSelectedListener,*/ OnImagesRecipesClickListener {

    @BindView(R.id.layout_activity_details)
    View layout_activity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ingredients)
    TextView ingredients;
    @BindView(R.id.instructions)
    TextView instructions;
    @BindView(R.id.no_videos_tv)
    TextView noVideosTV;
    @BindView(R.id.no_images_tv)
    TextView noImagesTV;
    @BindView(R.id.exo_player)
    PlayerView playerView;
    @BindView(R.id.images_rv)
    RecyclerView imagesRecyclerView;

    @BindString(R.string.no_videos_msg)
    String noVideosMsg;
    @BindString(R.string.no_images_msg)
    String noImagesMsg;

    private String[] imagesUrls;
    private String[] videosUrls;
    private RecipeEntry recipeEntry;
    private RecipePlayer recipePlayer;
    private ExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemesUtils.getThemeByKey(this)); // must be before setContentView() to set theme
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);

        getComingIntents();
        setUI();
    }

    private void setUI (){
        layout_activity.setVisibility(VISIBLE);
//        setNavBar();
        if (null != recipeEntry) {
            title.setText(recipeEntry.getTitle());
            instructions.setText(recipeEntry.getInstructions());
            ingredients.setText(recipeEntry.getIngredients());
            setImagesUrls();
            setVideosUrls();
            setImagesRecyclerView();
            recipePlayer = new RecipePlayer(this);
        }
    }

    private void setNavBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar);
//        navigationView.setNavigationItemSelectedListener(this);
    }
    private void setImagesRecyclerView(){
        ImagesRecipesAdapter imagesAdapter = new ImagesRecipesAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        imagesRecyclerView.setLayoutManager(linearLayoutManager);
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setAdapter(imagesAdapter);
        imagesAdapter.swapList(imagesUrls);
    }

    private void getComingIntents () {
        Intent comingIntent = getIntent();
        if (null != comingIntent) {
            recipeEntry = comingIntent.getParcelableExtra(RECIPE_KEYWORD);

        }
    }

    /**
     * set images urls from coming intents
     */
    private void setImagesUrls (){
        String images = recipeEntry.getImages();

        if (images.isEmpty()){
            Timber.v(getString(R.string.images_empty_msg));
            // "https://joyofandroid.com/wp-content/uploads/2013/03/android-female-version.jpg"
            imagesRecyclerView.setVisibility(View.GONE);
            noImagesTV.setText(noImagesMsg);
            noImagesTV.setVisibility(View.VISIBLE);

        } else if (images.contains(COMMA)) {
            imagesUrls = images.split(COMMA);
            Timber.v(getString(R.string.images_contain_msg), imagesUrls.length);
        }else {
            Timber.v(getString(R.string.image_msg));
            imagesUrls = new String[]{images};
        }

    }

    /**
     * set videos urls from coming intents
     */
    private void setVideosUrls (){
        String videos = recipeEntry.getVideos();
        if (null == videos || videos.isEmpty()){
            // tell user that there is no videos and show fun video
            videosUrls = new String[]{getString(R.string.media_url_dash)};
            noVideosTV.setText(noVideosMsg);
            noVideosTV.setVisibility(View.VISIBLE);
        } else if (videos.contains(COMMA)) {
            // means that there are list of videos then split them to an String[] and display all of them
            videosUrls = videos.split(COMMA);
            Timber.v(getString(R.string.videos_msg), videosUrls.length);
        }else {
            // ready to display dash format
            Timber.v(getString(R.string.video_msg));
            videosUrls = new String[]{videos};
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > SDK_MARSHMALLOW) {
            recipePlayer.initializePlayerForDash(playerView , videosUrls);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= SDK_MARSHMALLOW) {
            recipePlayer.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > SDK_MARSHMALLOW) {
            recipePlayer.releasePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ThemesUtils.isThemeChanged()) recreate(); // to reset the theme
        recipePlayer.hideSystemUi();
        if ((Util.SDK_INT <= SDK_MARSHMALLOW) || exoPlayer == null) {
            exoPlayer = recipePlayer.initializePlayerForDash(playerView, videosUrls);
        }
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        ThemesUtils.setNavSelections(this, id);
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
