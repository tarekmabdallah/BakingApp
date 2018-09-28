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
package com.gmail.tarekmabdallah91.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.DetailsActivity;
import com.gmail.tarekmabdallah91.bakingapp.activities.MainActivity;
import com.gmail.tarekmabdallah91.bakingapp.data.room.PresenterRoom;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        PresenterRoom.getInstance(context); // to set instance in the single tone then be ready to be used .
        RecipeEntry lastRecipe = PresenterRoom.getLastRecipeEntry(context);
        String widgetText;
        Intent intent;
        if (lastRecipe != null) {
            widgetText = lastRecipe.getRowId() + lastRecipe.getTitle();
            intent = new Intent(context , DetailsActivity.class);
            intent.putExtra(RECIPE_KEYWORD, lastRecipe);
        }else {
            // if the recipe Entry is null make the widget text is the app name and when the user clicks on it open the MainActivity
            widgetText = context.getString(R.string.app_name);
            intent = new Intent(context , MainActivity.class);
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.title, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ZERO, intent, ZERO);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.title , pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

