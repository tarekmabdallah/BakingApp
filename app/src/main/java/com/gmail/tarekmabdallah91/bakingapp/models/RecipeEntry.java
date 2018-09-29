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
package com.gmail.tarekmabdallah91.bakingapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.COMMA;

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
    private String title;
    private String ingredients;
    private String instructions;
    private String images;
    private String videos;

    @Ignore
    public RecipeEntry(String title, String ingredients, String instructions, String images, String videos) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.images = images;
        this.videos = videos;
    }

    public RecipeEntry(int rowId, String title, String ingredients, String instructions, String images, String videos) {
        this.rowId = rowId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.images = images;
        this.videos = videos;
    }

    private RecipeEntry(Parcel in) {
        rowId = in.readInt();
        title = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
        images = in.readString();
        videos = in.readString();
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

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowId);
        dest.writeString(title);
        dest.writeString(ingredients);
        dest.writeString(instructions);
        dest.writeString(images);
        dest.writeString(videos);
    }

    public String[] getImagesUrls() {
        String imagesUrls[] = null;
        if (null != images) {
            if (images.contains(COMMA)) { // more than one
                imagesUrls = images.split(COMMA);
            } else { // one image
                imagesUrls = new String[]{images};
            }
        }
        return imagesUrls;
    }

    public String[] getVideosUrls() {
        String videosUrls[] = null;
        if (null != videos) {
            if (videos.contains(COMMA)) { // more than one
                videosUrls = videos.split(COMMA);
            } else { // one video link
                videosUrls = new String[]{videos};
            }
        }
        return videosUrls;
    }


}
