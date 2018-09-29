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
package com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.squareup.picasso.Picasso;

public class ImagesRecipesAdapter extends RecyclerView.Adapter<ImagesRecipesViewHolder> {


    private String[] imagesUrls;
    private final OnImagesRecipesClickListener onRecipeClickListener;

    public ImagesRecipesAdapter(OnImagesRecipesClickListener onRecipeClickListener){
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public ImagesRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.images_recipes_item , parent ,false);
        return new ImagesRecipesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesRecipesViewHolder holder, int position) {

        String url = imagesUrls[position];
        Picasso.get().load(url)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_notify_sync)
                .into(holder.imagesRecipesIV);
    }

    @Override
    public int getItemCount() {
        if (imagesUrls == null) return 0;

        return imagesUrls.length;
    }

    public void swapList (String [] imagesUrls){
        this.imagesUrls = imagesUrls;
        notifyDataSetChanged();
    }
}
