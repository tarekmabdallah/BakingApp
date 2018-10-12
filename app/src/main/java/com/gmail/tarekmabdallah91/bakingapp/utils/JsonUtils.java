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
package com.gmail.tarekmabdallah91.bakingapp.utils;

import android.content.Context;

import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.models.IngredientsModel;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.models.StepModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.CHARSET_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.DESCRIPTION_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ID_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INGREDIENTS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INGREDIENT_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.MEASURE_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.NAME_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.QUANTITY_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SAMPLE_RECIPES_JSON_FILE_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SERVINGS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.SHORT_DESCRIPTION_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.STEPS_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.THUMBNAIL_URL;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.VIDEO_URL;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class JsonUtils {


    /**
     * to get string from json file in assets
     *
     * @param context to get assets
     * @return string json
     */
    @SuppressWarnings("unused")
    private static String loadJsonFromAssets(Context context) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(SAMPLE_RECIPES_JSON_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            int read = inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, CHARSET_NAME);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * to save samples in Room after fetching Json
     *
     * @param context-
     */
    public static void saveSampleRecipesInRoom(Context context) {
        String json = loadJsonFromAssets(context);
        try {
            JSONArray samples = new JSONArray(json);
            for (int i = ZERO; i < samples.length(); i++) {
                JSONObject recipe = samples.getJSONObject(i);
                int id = recipe.optInt(ID_KEYWORD);
                int serving = recipe.optInt(SERVINGS_KEYWORD);
                String name = recipe.optString(NAME_KEYWORD);
                String image = recipe.optString(IMAGE_KEYWORD);
                String ingredients = recipe.optString(INGREDIENTS_KEYWORD);
                String steps = recipe.optString(STEPS_KEYWORD);
                RecipeEntry recipeEntry = new RecipeEntry(id, name, ingredients, steps, image, serving);
                RoomPresenter.getInstance(context).getRecipeDataFromJsonStoreItInRoom(context, recipeEntry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * to get Strings from JsonArray of Ingredients
     *
     * @param jsonString -
     * @return List<Strings>
     */
    public static List<String> getIngredientsFromJson(String jsonString) {
        List<String> ingredientsStringList = new ArrayList<>();

        try {
            JSONArray ingredientsJsonArray = new JSONArray(jsonString);
            for (int i = ZERO; i < ingredientsJsonArray.length(); i++) {
                JSONObject ingredientJsonObject = ingredientsJsonArray.getJSONObject(i);
                int quantity = ingredientJsonObject.optInt(QUANTITY_KEYWORD);
                String measure = ingredientJsonObject.optString(MEASURE_KEYWORD);
                String ingredient = ingredientJsonObject.optString(INGREDIENT_KEYWORD);
                IngredientsModel ingredientsModel = new IngredientsModel(quantity, ingredient, measure);
                ingredientsStringList.add(ingredientsModel.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredientsStringList;
    }

    /**
     * to get Strings from JsonArray of Steps
     *
     * @param jsonString -
     * @return List<StepModel>
     */
    public static List<StepModel> getStepsFromJson(String jsonString) {
        List<StepModel> steps = new ArrayList<>();

        try {
            JSONArray stepsJsonArray = new JSONArray(jsonString);
            for (int i = ZERO; i < stepsJsonArray.length(); i++) {
                JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(i);
                int id = stepsJsonObject.optInt(ID_KEYWORD);
                String description = stepsJsonObject.optString(DESCRIPTION_KEYWORD);
                String shortDescription = stepsJsonObject.optString(SHORT_DESCRIPTION_KEYWORD);
                String videoUrl = stepsJsonObject.optString(VIDEO_URL);
                String thumbnailUrl = stepsJsonObject.optString(THUMBNAIL_URL);
                StepModel stepModel = new StepModel(id, description, shortDescription, videoUrl, thumbnailUrl);
                steps.add(stepModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return steps;
    }

    /**
     * to get Strings from JsonArray of shortDescription
     *
     * @param jsonString -
     * @return List<StepModel>
     */
    public static List<String> getShortDescriptionListFromJson(String jsonString) {
        List<String> shortDescriptionList = new ArrayList<>();

        try {
            JSONArray stepsJsonArray = new JSONArray(jsonString);
            for (int i = ZERO; i < stepsJsonArray.length(); i++) {
                JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(i);
                String shortDescription = stepsJsonObject.optString(SHORT_DESCRIPTION_KEYWORD);
                shortDescriptionList.add(shortDescription);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shortDescriptionList;
    }

}
