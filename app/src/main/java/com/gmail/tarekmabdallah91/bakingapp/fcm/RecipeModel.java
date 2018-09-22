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
package com.gmail.tarekmabdallah91.bakingapp.fcm;

class RecipeModel {

    private String title;
    private String ingredients;
    private String instructions;
    private String images;
    private String videos;


    public RecipeModel(String title, String ingredients, String instructions, String imagesUrls ,String videosUrls){
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.images = imagesUrls;
        this.videos = videosUrls;
    }

    public String getImages() {
        return images;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getVideos() {
        return videos;
    }

    public String getTitle() {
        return title;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
