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

package com.gmail.tarekmabdallah91.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.gmail.tarekmabdallah91.bakingapp.R;

import java.util.ArrayList;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INGREDIENT_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ONE;

class IngredientsWidgetService implements RemoteViewsFactory {

    private final Context context;
    private final ArrayList<String> ingredients;

    IngredientsWidgetService(Context context, Intent intent) {
        this.context = context;
        this.ingredients = intent.getStringArrayListExtra(INGREDIENT_KEYWORD);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // bind data with list view
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_list_view_widget);
        row.setTextViewText(R.id.ingredient_item, ingredients.get(position));
        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return ONE;
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {
    }
}