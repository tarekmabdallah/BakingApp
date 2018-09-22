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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.ImagesRecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.OnImagesRecipesClickListener;
import com.gmail.tarekmabdallah91.bakingapp.exoplayer.RecipePlayer;
import com.gmail.tarekmabdallah91.bakingapp.room.recipe.RecipeEntry;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;

public class DetailsActivity extends AppCompatActivity implements OnImagesRecipesClickListener {

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
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getComingIntents();
        setUI();
    }

    private void setUI (){
        if (null != recipeEntry){
            title.setText(recipeEntry.getTitle());
            instructions.setText(recipeEntry.getInstructions());
            ingredients.setText(recipeEntry.getIngredients());
            setImagesUrls();
            setVideosUrls();
            setImagesRecyclerView();
            recipePlayer = new RecipePlayer(this);
        }
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
        Timber.v("images are : %s " , images);

        if (images.isEmpty()){
            Timber.v("images are : empty ");
            // "https://joyofandroid.com/wp-content/uploads/2013/03/android-female-version.jpg"
            imagesRecyclerView.setVisibility(View.GONE);
            noImagesTV.setText(noImagesMsg);
            noImagesTV.setVisibility(View.VISIBLE);

        }else if (images.contains(",")){
            imagesUrls = images.split(",");
            Timber.v("images contain : , %d" , imagesUrls.length);
        }else {
            Timber.v("images contains one image");
            imagesUrls = new String[]{images};
        }

    }

    /**
     * set videos urls from coming intents
     */
    private void setVideosUrls (){
        String videos = recipeEntry.getVideos();

        videos = null;
        if (null == videos || videos.isEmpty()){
            // tell user that there is no videos and show fun video
            videosUrls = new String[]{getString(R.string.media_url_dash)};
            noVideosTV.setText(noVideosMsg);
            noVideosTV.setVisibility(View.VISIBLE);
        }else if (videos.contains(",")){
            // means that there are list of videos then split them to an String[] and display all of them
            videosUrls = videos.split(",");
            Timber.v("videos contains : %d " , videosUrls.length);
        }else {
            // ready to display dash format
            Timber.v("videos contain one video ");
            videosUrls = new String[]{videos};
        }
    }

    @Override
    public void onImagesRecipesClicked(RecipeEntry recipeEntry) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            recipePlayer.initializePlayerForDash(playerView , videosUrls);
        }
    }

    @Override
    public void onResume() {
        recipePlayer.hideSystemUi();
        super.onResume();
        if ((Util.SDK_INT <= 23) || exoPlayer == null) {
            exoPlayer = recipePlayer.initializePlayerForDash(playerView , videosUrls);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 ) {
            recipePlayer.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            recipePlayer.releasePlayer();
        }
    }


}
