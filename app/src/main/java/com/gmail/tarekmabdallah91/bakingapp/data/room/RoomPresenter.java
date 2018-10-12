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
package com.gmail.tarekmabdallah91.bakingapp.data.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeDao;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeDatabase;
import com.gmail.tarekmabdallah91.bakingapp.data.room.user.UserDao;
import com.gmail.tarekmabdallah91.bakingapp.data.room.user.UserDatabase;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.models.UserEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.NotificationUtils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ID_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INGREDIENTS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.LATITUDE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.LONGITUDE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.NAME_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SERVINGS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.STEPS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_FIRST_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_GENDER;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_LAST_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_LOCATION;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_PICTURE_PATH;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_STRING_ID;

public class RoomPresenter {

    private static final String TAG = RoomPresenter.class.getSimpleName();
    private static RecipeDao recipeDao;
    private static UserDao userDao;
    private static RoomPresenter instance;

    private RoomPresenter(Context context) {
        if (null == recipeDao){
            recipeDao = RecipeDatabase.getInstance(context).recipeDao();
        }
        if (null == userDao){
            userDao = UserDatabase.getInstance(context).userDao();
        }
    }

    static public RoomPresenter getInstance(Context context) {
        if (null == instance){
            instance = new RoomPresenter(context);
        }
        return instance;
    }

    /**
     * to insert data to Room
     * @param RECIPE_ENTRY as a row to be inserted
     */
    static private void insertRecipeDataToRoom(final Context context, final RecipeEntry RECIPE_ENTRY){
        AsyncTask<Void , Void , Void> insertNewRowInRoom = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                recipeDao.insertRecipe(RECIPE_ENTRY);
                Log.d(TAG, context.getString(R.string.new_row_inserted_msg));
                return null;
            }
        };
        insertNewRowInRoom.execute();
    }

    /**
     * to get the last row in the Room to send it with the intent to the DetailsActivity
     * @param context to get strings for timber
     * @return last Recipe Entry
     */
    static public RecipeEntry getLastRecipeEntry (final Context context){
        AsyncTask<Void , Void , RecipeEntry> getLastRecipeEntry = new AsyncTask<Void, Void, RecipeEntry>() {
            @Override
            protected RecipeEntry doInBackground(Void... voids) {
                Log.d(TAG, context.getString(R.string.get_last_recipe_msg));
                return recipeDao.getLastRecipeEntry();
            }
        };
        getLastRecipeEntry.execute();
        try {
            return getLastRecipeEntry.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to insert data to Room
     *
     * @param USER_ENTRY as a row to be inserted
     */
    static private void insertUserDataToRoom(final Context context, final UserEntry USER_ENTRY) {
        AsyncTask<Void, Void, Void> insertNewRowInRoom = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.insertUser(USER_ENTRY);
                Log.d(TAG, context.getString(R.string.new_row_inserted_msg));
                return null;
            }
        };
        insertNewRowInRoom.execute();
    }

    /**
     * to get the last row in the Room to send it with the intent to the DetailsActivity
     *
     * @param context to get strings for timber
     * @return last Recipe Entry
     */
    static public UserEntry getLastUserEntry(final Context context) {
        AsyncTask<Void, Void, UserEntry> getLastRecipeEntry = new AsyncTask<Void, Void, UserEntry>() {
            @Override
            protected UserEntry doInBackground(Void... voids) {
                Log.d(TAG, context.getString(R.string.get_last_recipe_msg));
                return userDao.getLastUser();
            }
        };
        getLastRecipeEntry.execute();
        try {
            return getLastRecipeEntry.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to get the data from message when the app in background and receive a push notification then save it on the Room
     *
     * @param context          -
     * @param data             bundle contains the RecipeEntry values
     * @param showNotification because this method is used in different cases, one needs showing notifications and the other doesn't
     */
    final public void getRecipeDataFromMessageStoreItInRoom(final Context context, Bundle data, boolean showNotification) {
        if (null != data) {
            if (!data.isEmpty()) {
                Log.d(TAG, String.format(context.getString(R.string.data_is), data.toString()));
                String name = data.getString(NAME_KEYWORD);
                String steps = data.getString(STEPS_KEYWORD);
                String ingredients = data.getString(INGREDIENTS_KEYWORD);
                String images = data.getString(IMAGE_KEYWORD);
                int recipeId = Integer.parseInt(data.getString(ID_KEYWORD));
                int serving = Integer.parseInt(data.getString(SERVINGS_KEYWORD));
                RecipeEntry recipeEntry = new RecipeEntry(recipeId, name, ingredients, steps, images, serving);
                insertRecipeDataToRoom(context, recipeEntry);
                if (showNotification) {
                    NotificationUtils.startNotification(context, recipeEntry);
                }
            }
        }
    }

    /**
     * to get the data from message when the app in foreground and receive a data message then save it on the Room
     *
     * @param context          -
     * @param bodyMsg          Map contains the RecipeEntry values
     * @param showNotification because this method is used in different cases, one needs showing notifications and the other doesn't
     */
    final public void getRecipeDataFromMapStoreItInRoom(final Context context, Map<String, String> bodyMsg, boolean showNotification) {
        if (null != bodyMsg) {
            if (!bodyMsg.isEmpty()) {
                Log.d(TAG, String.format(context.getString(R.string.data_is), bodyMsg.toString()));
                int recipeId = Integer.parseInt(bodyMsg.get(ID_KEYWORD));
                String name = bodyMsg.get(NAME_KEYWORD);
                String ingredients = bodyMsg.get(INGREDIENTS_KEYWORD);
                String steps = bodyMsg.get(STEPS_KEYWORD);
                int serving = Integer.parseInt(bodyMsg.get(SERVINGS_KEYWORD));
                String image = bodyMsg.get(IMAGE_KEYWORD);
                RecipeEntry recipeEntry = new RecipeEntry(recipeId, name, ingredients, steps, image, serving);
                insertRecipeDataToRoom(context, recipeEntry);
                if (showNotification) {
                    NotificationUtils.startNotification(context, recipeEntry);
                }
            }
        }
    }

    /**
     * to get the data from assets then save it on the Room
     *
     * @param context          -
     */
    final public void getRecipeDataFromJsonStoreItInRoom(final Context context, RecipeEntry recipeEntry) {
        insertRecipeDataToRoom(context, recipeEntry);
    }

    /**
     * to delete data from Room
     *
     * @param id as an id for the row wanted to be deleted
     */
    final public void DeleteRecipeDataFromRoom(final Context context, final int id) {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> insertNewRowInRoom = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                recipeDao.deleteRecipe(id);
                Log.d(TAG, context.getString(R.string.new_row_inserted_msg));
                return null;
            }
        };
        insertNewRowInRoom.execute();
    }

    /**
     * to get recipes count in the Room to check if only the room is empty then load the samples recipes in assets
     *
     * @param context to get strings for timber
     * @return last Recipe Entry
     */
    public Integer getRecipesCount(final Context context) {
        AsyncTask<Void, Void, Integer> getLastRecipeEntry = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                Log.d(TAG, context.getString(R.string.get_last_recipe_msg));
                return recipeDao.loadAllRecipesTogetCount().size();
            }
        };
        getLastRecipeEntry.execute();
        try {
            return getLastRecipeEntry.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to set user row using data from bundle
     *
     * @param userData contains user's data
     */
    public void setUserEntry(Context context, Bundle userData) {
        // get data from bundle
        UserEntry userEntry = new UserEntry(
                userData.getString(USER_STRING_ID),
                userData.getString(USER_FIRST_NAME),
                userData.getString(USER_LAST_NAME),
                userData.getInt(USER_GENDER),
                userData.getString(USER_PICTURE_PATH),
                userData.getString(USER_LOCATION),
                userData.getDouble(LATITUDE_KEYWORD),
                userData.getDouble(LONGITUDE_KEYWORD));
        insertUserDataToRoom(context, userEntry);
    }
}
