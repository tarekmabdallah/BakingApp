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
package com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recipe_image)
    ImageView recipeImage;

    private List<RecipeEntry> recipeEntries;
    private OnRecipeClickListener onRecipeClickListener;

    RecipesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        RecipeEntry recipeEntry = recipeEntries.get(position);
        onRecipeClickListener.onRecipeClicked(recipeEntry);
    }

    void setOnRecipeClickListener(OnRecipeClickListener onRecipeClickListener) {
        this.onRecipeClickListener = onRecipeClickListener;
    }

    void setRecipeEntries(List<RecipeEntry> recipeEntries) {
        this.recipeEntries = recipeEntries;
    }
}
