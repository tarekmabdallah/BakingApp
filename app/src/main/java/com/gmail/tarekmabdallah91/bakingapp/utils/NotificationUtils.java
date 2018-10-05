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
package com.gmail.tarekmabdallah91.bakingapp.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.DetailsActivity;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.NOTIFICATION_CHANNEL_INT_ID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.NOTIFICATION_CHANNEL_STRING_ID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.NOTIFICATION_REQUEST_CODE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.RECIPE_KEYWORD;


public class NotificationUtils {

    public static void startNotification (Context context, RecipeEntry recipeEntry){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context , NOTIFICATION_CHANNEL_STRING_ID);
        notificationBuilder.setContentIntent(pendingIntent(context , getIntent(context , recipeEntry)))
                .setSmallIcon(R.drawable.ic_icon_app2)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(recipeEntry.getName())
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_CHANNEL_INT_ID,notificationBuilder.build());
        }
    }

    private static Intent getIntent (Context context, RecipeEntry recipeEntry){
        Intent intent = new Intent(context , DetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(RECIPE_KEYWORD, recipeEntry);
        return intent;
    }

    private static PendingIntent pendingIntent (Context context , Intent intent){
        return PendingIntent.getActivity(context
                ,NOTIFICATION_REQUEST_CODE
                ,intent
                ,PendingIntent.FLAG_ONE_SHOT);
    }
}
