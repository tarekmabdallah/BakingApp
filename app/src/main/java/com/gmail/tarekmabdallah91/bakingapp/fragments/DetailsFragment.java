package com.gmail.tarekmabdallah91.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.ImagesRecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.OnImagesRecipesClickListener;
import com.gmail.tarekmabdallah91.bakingapp.exoplayer.RecipeExoPlayer;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SDK_MARSHMALLOW;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;


public class DetailsFragment extends Fragment implements OnImagesRecipesClickListener {

    @BindView(R.id.empty_tv)
    TextView emptyTV;
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

    private Context context;
    private String[] imagesUrls;
    private String[] videosUrls;
    private RecipeEntry recipe;
    private RecipeExoPlayer recipeExoPlayer;
    private ExoPlayer exoPlayer;

    public DetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComingIntents();
        setUI();
    }


    private void setUI() {
        context = getContext();
        if (null != recipe) {
            makeUIVisible(true);
            title.setText(recipe.getTitle());
            instructions.setText(recipe.getInstructions());
            ingredients.setText(recipe.getIngredients());
            setImagesUrls();
            setVideosUrls();
            setImagesRecyclerView();
            recipeExoPlayer = new RecipeExoPlayer(context);
        } else {
            makeUIVisible(false); // show empty msg only
        }
    }

    private void makeUIVisible(boolean value) {
        if (value) {
            title.setVisibility(VISIBLE);
            instructions.setVisibility(VISIBLE);
            ingredients.setVisibility(VISIBLE);
            imagesRecyclerView.setVisibility(VISIBLE);
            playerView.setVisibility(VISIBLE);
            emptyTV.setVisibility(GONE);
        } else {
            title.setVisibility(GONE);
            instructions.setVisibility(GONE);
            ingredients.setVisibility(GONE);
            imagesRecyclerView.setVisibility(GONE);
            playerView.setVisibility(GONE);
            emptyTV.setVisibility(VISIBLE);
        }
    }

    private void setImagesRecyclerView() {
        ImagesRecipesAdapter imagesAdapter = new ImagesRecipesAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        imagesRecyclerView.setLayoutManager(linearLayoutManager);
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setAdapter(imagesAdapter);
        imagesAdapter.swapList(imagesUrls);
    }

    /**
     * to set recipe with coming intent if it wasn't = null
     */
    private void getComingIntents() {
        Activity activity = getActivity();
        if (null != activity) {
            Intent comingIntent = activity.getIntent();
            if (null != comingIntent) {
                recipe = comingIntent.getParcelableExtra(RECIPE_KEYWORD);
            }
        }
    }

    /**
     * set images urls from coming intents
     */
    private void setImagesUrls() {
        imagesUrls = recipe.getImagesUrls();
        if (null == imagesUrls) {
            Timber.v(getString(R.string.images_empty_msg));
            // Example "https://joyofandroid.com/wp-content/uploads/2013/03/android-female-version.jpg"
            imagesRecyclerView.setVisibility(GONE);
            noImagesTV.setText(noImagesMsg);
            noImagesTV.setVisibility(VISIBLE);
        }

    }

    /**
     * set videos urls from coming intents
     */
    private void setVideosUrls() {
        videosUrls = recipe.getVideosUrls();
        if (null == videosUrls || videosUrls.length <= ZERO) {
            // tell user that there is no videos and show fun video
            videosUrls = new String[]{getString(R.string.media_url_dash)};
            noVideosTV.setText(noVideosMsg);
            noVideosTV.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > SDK_MARSHMALLOW) {
            recipeExoPlayer.initializePlayerForDash(playerView, videosUrls);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= SDK_MARSHMALLOW) {
            recipeExoPlayer.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > SDK_MARSHMALLOW) {
            recipeExoPlayer.releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeExoPlayer.hideSystemUi();
        if ((Util.SDK_INT <= SDK_MARSHMALLOW) || exoPlayer == null) {
            exoPlayer = recipeExoPlayer.initializePlayerForDash(playerView, videosUrls);
        }
    }

}


