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
package com.gmail.tarekmabdallah91.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.RecipeStepActivity;
import com.gmail.tarekmabdallah91.bakingapp.exoplayer.RecipeExoPlayer;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.models.StepModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INVALID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ONE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.POSITION_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SDK_MARSHMALLOW;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class RecipeStepFragment extends Fragment {

    @BindView(R.id.empty_tv)
    TextView emptyTV;
    @BindView(R.id.no_videos_tv)
    TextView noVideosTV;
    @BindView(R.id.instructions_tv)
    TextView instructionTV;
    @BindView(R.id.next_instruction_tv)
    TextView nextTV;
    @BindView(R.id.back_instruction_tv)
    TextView backTV;
    @BindView(R.id.exo_player)
    PlayerView playerView;

    private Activity activity;
    private RecipeExoPlayer recipeExoPlayer;
    private ExoPlayer exoPlayer;
    private RecipeEntry recipe;
    private String videosUrl;
    private int positionStep;
    private int stepsCount;
    private long currentPositionExoPlayer;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        if (null != savedInstanceState) {
            currentPositionExoPlayer = savedInstanceState.getLong(POSITION_KEYWORD, ZERO);
            recipe = savedInstanceState.getParcelable(RECIPE_KEYWORD);
        }
        if (null == recipe && positionStep == INVALID) { // for first time open the two pane layout .. show msg to select step then after that run normally
            makeUIVisible(false);
        } else {
            getComingIntents();
        }
        setUI();
    }

    private void setUI() {
        Context context = getContext();
        recipeExoPlayer = new RecipeExoPlayer(context);
        ActionBar actionBar = null;
        if (null != activity) actionBar = ((AppCompatActivity) activity).getSupportActionBar();

        if (null != recipe) {
            if (null != actionBar) actionBar.setTitle(recipe.getName());
            List<StepModel> steps = recipe.getStepsList();
            stepsCount = steps.size();
            StepModel step = steps.get(positionStep);
            videosUrl = step.getVideoUrl();
            if (videosUrl == null || videosUrl.isEmpty())
                videosUrl = step.getThumbnailUrl();


            instructionTV.setText(step.getDescription());
            makeUIVisible(true);
        } else {
            makeUIVisible(false);
        }
    }

    private void makeUIVisible(boolean value) {
        if (value) {
            instructionTV.setVisibility(VISIBLE);
            backTV.setVisibility(VISIBLE);
            nextTV.setVisibility(VISIBLE);
            playerView.setVisibility(VISIBLE);
            emptyTV.setVisibility(GONE);
        } else {
            instructionTV.setVisibility(GONE);
            backTV.setVisibility(GONE);
            nextTV.setVisibility(GONE);
            playerView.setVisibility(GONE);
            emptyTV.setVisibility(VISIBLE);
        }
    }

    /**
     * to set steps and title with coming intent if it wasn't = null
     */
    private void getComingIntents() { // when the app run on a mobile screen by screen
        if (null != activity) {
            Intent comingIntent = activity.getIntent();
            if (null != comingIntent) {
                recipe = comingIntent.getParcelableExtra(RECIPE_KEYWORD);
                positionStep = comingIntent.getIntExtra(POSITION_KEYWORD, ZERO);
            } else {
                makeUIVisible(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != exoPlayer) {
            currentPositionExoPlayer = exoPlayer.getCurrentPosition();
            outState.putLong(POSITION_KEYWORD, currentPositionExoPlayer);
            outState.putParcelable(RECIPE_KEYWORD, recipe);
        }

    }

    @OnClick(R.id.next_instruction_tv)
    void onClickOnNextBtn() {
        // TODO - to search if there is method better than it to replace the fragment
        if (positionStep < stepsCount - ONE) openNewStep(++positionStep);
    }

    @OnClick(R.id.back_instruction_tv)
    void onClickOnBackBtn() {
        if (positionStep > ZERO) openNewStep(--positionStep);
    }

    private void openNewStep(int position) {
        Intent openNextStep = new Intent(getContext(), RecipeStepActivity.class);
        openNextStep.putExtra(RECIPE_KEYWORD, recipe);
        openNextStep.putExtra(POSITION_KEYWORD, position);
        startActivity(openNextStep);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > SDK_MARSHMALLOW) {
            if (null != videosUrl && !videosUrl.isEmpty()) {
                exoPlayer = recipeExoPlayer.initializePlayerForMp4(playerView, videosUrl);
                if (currentPositionExoPlayer > ZERO) exoPlayer.seekTo(currentPositionExoPlayer);
            } else {
                playerView.setAlpha(ZERO);
                playerView.setVisibility(GONE);
                noVideosTV.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= SDK_MARSHMALLOW) {
            if (null == exoPlayer && null != videosUrl && !videosUrl.isEmpty()) {
                if (null != recipeExoPlayer) {
                    recipeExoPlayer.hideSystemUi();
                    playerView.setAlpha(ONE);
                    playerView.setVisibility(VISIBLE);
                    exoPlayer = recipeExoPlayer.initializePlayerForMp4(playerView, getString(R.string.media_url_dash));
                    if (currentPositionExoPlayer > ZERO) exoPlayer.seekTo(currentPositionExoPlayer);
                }
            } else {
                noVideosTV.setVisibility(VISIBLE);
                playerView.setAlpha(ZERO);
                playerView.setVisibility(GONE);
            }
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


    public void setFragmentData(RecipeEntry recipe, int position) {
        this.recipe = recipe;
        this.positionStep = position;
    }
}
