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
package com.gmail.tarekmabdallah91.bakingapp.adapters.images_recipes_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ONE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class ImagesRecipesAdapter extends RecyclerView.Adapter<ImagesRecipesViewHolder> {

    private final List<String> imagesUrls;

    public ImagesRecipesAdapter(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
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

        if (null != imagesUrls && !imagesUrls.isEmpty()) {
            String url = imagesUrls.get(position);
            Picasso.get().load(url)
                    .error(android.R.drawable.stat_notify_error)
                    .placeholder(android.R.drawable.stat_notify_sync)
                    .into(holder.imagesRecipesIV);
        } else {
            Picasso.get().load(R.drawable.recipe_book_main_list)
                    .error(android.R.drawable.stat_notify_error)
                    .placeholder(android.R.drawable.stat_notify_sync)
                    .into(holder.imagesRecipesIV);
        }
    }

    @Override
    public int getItemCount() {
        if (imagesUrls == null) return ZERO;
        if (imagesUrls.isEmpty()) return ONE; // to show icon app as default image
        return imagesUrls.size();
    }

}
