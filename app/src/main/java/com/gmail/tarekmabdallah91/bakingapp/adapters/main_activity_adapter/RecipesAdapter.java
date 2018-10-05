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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesViewHolder> {
    private List<RecipeEntry> recipeEntries;
    private final OnRecipeClickListener onRecipeClickListener;

    public RecipesAdapter(OnRecipeClickListener onRecipeClickListener){
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recipes_list_item , parent ,false);
        return new RecipesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        RecipeEntry recipeEntry = recipeEntries.get(position);
        holder.name.setText(recipeEntry.getName());
        holder.setOnRecipeClickListener(onRecipeClickListener);
        holder.setRecipeEntries(recipeEntries);
        holder.itemView.setTag(recipeEntry.getRowId());
        String imageUrl = recipeEntry.getImages();
        if (null != imageUrl && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.recipeImage);
        } else {
            Picasso.get()
                    .load(R.drawable.icon_app)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.recipeImage);
        }

    }

    @Override
    public int getItemCount() {
        if (recipeEntries == null) return ZERO;

        return recipeEntries.size();
    }

    public void swapList (List<RecipeEntry> recipeEntries){
        this.recipeEntries = recipeEntries;
        notifyDataSetChanged();
    }

}
