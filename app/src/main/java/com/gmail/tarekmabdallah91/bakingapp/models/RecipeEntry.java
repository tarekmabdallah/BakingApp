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
package com.gmail.tarekmabdallah91.bakingapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.tarekmabdallah91.bakingapp.utils.JsonUtils;

import java.util.List;

@Entity(tableName = "recipes")
public class RecipeEntry implements Parcelable {

    public static final Creator<RecipeEntry> CREATOR = new Creator<RecipeEntry>() {
        @Override
        public RecipeEntry createFromParcel(Parcel in) {
            return new RecipeEntry(in);
        }

        @Override
        public RecipeEntry[] newArray(int size) {
            return new RecipeEntry[size];
        }
    };
    // columns names is the variables names as default
    // if wanted to change it use @ColumnInfo(name = "wanted named") before the variable declaration
    @PrimaryKey(autoGenerate = true)
    private int rowId;
    //        example
//        "id": 1,
//        "name": "Nutella Pie",
//        "ingredients":[] ,
//        "steps":[],
//        "servings": 8,
//        "images": ""
    private int recipeId;
    private String name;
    private String ingredients;
    private String steps;
    private String images;
    private int serving;


    @Ignore
    public RecipeEntry(int recipeId, String name, String ingredients, String steps, String images, int serving) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.images = images;
        this.serving = serving;
    }

    public RecipeEntry(int rowId, int recipeId, String name, String ingredients, String steps, String images, int serving) {
        this.rowId = rowId;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.images = images;
        this.recipeId = recipeId;
        this.serving = serving;
    }

    private RecipeEntry(Parcel in) {
        rowId = in.readInt();
        name = in.readString();
        ingredients = in.readString();
        steps = in.readString();
        images = in.readString();
        recipeId = in.readInt();
        serving = in.readInt();
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getIngredients() {
        return ingredients;
    }

    public List<String> getIngredientsList() {
        return JsonUtils.getIngredientsFromJson(ingredients);
    }

    public List<StepModel> getStepsList() {
        return JsonUtils.getStepsFromJson(steps);
    }

    public List<String> getShortDescriptionsList() {
        return JsonUtils.getShortDescriptionListFromJson(steps);
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowId);
        dest.writeString(name);
        dest.writeString(ingredients);
        dest.writeString(steps);
        dest.writeString(images);
        dest.writeInt(recipeId);
        dest.writeInt(serving);
    }
}
