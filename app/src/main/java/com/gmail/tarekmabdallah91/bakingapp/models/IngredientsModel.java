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

import android.annotation.SuppressLint;

public class IngredientsModel {

    /**
     * example
     * "quantity": 2,
     * "measure": "CUP",
     * "ingredient": "Graham Cracker crumbs"
     */

    private int quantity;
    private String measure;
    private String ingredient;

    public IngredientsModel(int quantity, String ingredient, String measure) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        final String INGREDIENT_IN_LINE = "%s\nQuantity: %d %s\n";
        return String.format(INGREDIENT_IN_LINE, ingredient, quantity, measure);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

}

