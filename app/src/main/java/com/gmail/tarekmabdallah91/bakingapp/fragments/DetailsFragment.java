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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.RecipeStepActivity;
import com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter.ImagesRecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter.OnStepClickListener;
import com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter.OnStepClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter.StepsAdapter;
import com.gmail.tarekmabdallah91.bakingapp.models.ParentInExpendableRecyclerView;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.models.StepModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.POSITION_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;


public class DetailsFragment extends Fragment implements OnStepClickListener {


    private static final String TAG = DetailsFragment.class.getSimpleName();
    @BindView(R.id.steps_rv)
    RecyclerView recyclerViewStepDescription;

    private Context context;
    private RecipeEntry recipe;
    @BindView(R.id.images_recipe_rv)
    RecyclerView imagesRecipeRecyclerView;
    private Activity activity;
    private List<ParentInExpendableRecyclerView> parentInExpendableRecyclerViews;
    private OnStepClickedOnFragment callbacks;

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
        setUI();
    }

    private void setUI() {
        activity = getActivity();
        context = getContext();

        getComingIntents();
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (null != actionBar) actionBar.setTitle(recipe.getName());
        setListParentInExpendableRecyclerView();
        List<StepModel> steps = recipe.getStepsList();


        setExpandableRecyclerView();
        setImagesRecipeRecyclerView();
    }

    private void setListParentInExpendableRecyclerView() {
        parentInExpendableRecyclerViews = new ArrayList<>();
        // add ingredientsList to parents List to be shown in adapter
        List<String> ingredientsList = recipe.getIngredientsList();
        ParentInExpendableRecyclerView ingredients =
                new ParentInExpendableRecyclerView(getString(R.string.ingredients_label), ingredientsList);
        parentInExpendableRecyclerViews.add(ingredients);
        // add shortDescriptionList to parents List to be shown in adapter
        List<String> shortDescriptionList = recipe.getShortDescriptionsList();
        ParentInExpendableRecyclerView shortDescription =
                new ParentInExpendableRecyclerView(getString(R.string.steps_label), shortDescriptionList);
        parentInExpendableRecyclerViews.add(shortDescription);
    }

    private void setExpandableRecyclerView() {
        StepsAdapter stepsAdapter = new StepsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewStepDescription.setLayoutManager(linearLayoutManager);
        recyclerViewStepDescription.setHasFixedSize(false);
        recyclerViewStepDescription.setAdapter(stepsAdapter);
        stepsAdapter.swapList(parentInExpendableRecyclerViews);
    }

    private void setImagesRecipeRecyclerView() {
        List<String> recipeImages = recipe.getImagesList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        ImagesRecipesAdapter adapter = new ImagesRecipesAdapter(recipeImages);
        imagesRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        imagesRecipeRecyclerView.setHasFixedSize(true);
        imagesRecipeRecyclerView.setAdapter(adapter);
    }

    /**
     * to set recipe with coming intent if it wasn't = null
     */
    private void getComingIntents() { // when the app run on a mobile screen by screen
        if (null != activity) {
            Intent comingIntent = activity.getIntent();
            if (null != comingIntent) {
                recipe = comingIntent.getParcelableExtra(RECIPE_KEYWORD);
            }
        }
    }

    @Override
    public void onStepClicked(int position) {
        if (null != getActivity() && getActivity().findViewById(R.id.fragment_master_sw600) == null) {
            Intent openRecipeStepActivity = new Intent(context, RecipeStepActivity.class);
            openRecipeStepActivity.putExtra(RECIPE_KEYWORD, recipe);
            openRecipeStepActivity.putExtra(POSITION_KEYWORD, position);
            startActivity(openRecipeStepActivity);
        } else {
            callbacks.onStepClickedOnFragment(recipe, position);
        }
    }

    /**
     * Override onAttach to make sure that the container activity has implemented the callback
     *
     * @param context -
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            callbacks = (OnStepClickedOnFragment) context;
        } catch (ClassCastException e) {
            Log.e(TAG, e.toString());
        }

    }
}


