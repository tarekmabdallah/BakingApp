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

public interface BakingConstants {


    String RECIPE_KEYWORD = "recipe";
    String TITLE_KEYWORD = "title";
    String NAME_KEYWORD = "name";
    String ID_KEYWORD = "id";
    String SERVING_KEYWORD = "serving";
    String INSTRUCTIONS_KEYWORD = "instructions";
    String STEPS_KEYWORD = "steps";
    String INGREDIENTS_KEYWORD = "ingredients";
    String VIDEOS_KEYWORD = "videos";
    String QUANTITY_KEYWORD = "quantity";
    String MEASURE_KEYWORD = "measure";
    String INGREDIENT_KEYWORD = "ingredient";
    String SHORT_DESCRIPTION_KEYWORD = "shortDescription";
    String DESCRIPTION_KEYWORD = "description";
    String VIDEO_URL = "videoURL";
    String THUMBNAIL_URL = "thumbnailURL";
    String IMAGES_KEYWORD = "images";
    String POSITION_KEYWORD = "position";
    String NOTIFICATION_CHANNEL_STRING_ID = "Baking Notification";
    String PROVIDER_AUTHORITY = "com.gmail.tarekmabdallah91.bakingapp.provider";
    String DATE_PATTERN = "yyyyMMdd_HHmmss";
    String IMAGE_FILE_NAME = "IMG_%s_";
    String IMAGE_SUFFIX = ".jpg";
    String IMAGE_INTENT_TYPE = "image/*";
    String CHARSET_NAME = "UTF-8";
    String SAMPLE_RECIPES_JSON_FILE_NAME = "sample_recipes.json";

    String USER_FIRST_NAME = "USER_FIRST_NAME";
    String USER_LAST_NAME = "USER_LAST_NAME";
    String USER_LOCATION = "USER_LOCATION";
    String USER_GENDER = "USER_GENDER";
    String USER_PICTURE_PATH = "USER_PICTURE_PATH";
    String USER_PICTURE_URI = "USER_PICTURE_URI";
    String USER_STRING_ID = "USER_STRING_ID";

    String PLACE_KEYWORD = "place";
    String FORMAT_GEO = "geo:%f,%f";
    String LATITUDE_KEYWORD = "latitude";
    String LONGITUDE_KEYWORD = "longitude";

    String PREFERENCES_FILE = "_preferences";
    String BLACK_KEYWORD = "black";
    String GREEN_KEYWORD = "green_dark";
    String PURPLE_KEYWORD = "purple";

    String EMPTY_TEXT = "";
    String COMMA = ",";
    String LETTERS_ONLY_REGEX = "^[a-zA-Z]+$";

    int INVALID = -1;
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FOUR = 4;

    int IMAGE_SIZE = 100;

    int GENDER_MALE = 1;
    int MAX_RANDOM_BOUND = 1000;
    int SDK_MARSHMALLOW = 23;
    int NOTIFICATION_CHANNEL_INT_ID = 10;
    int NOTIFICATION_REQUEST_CODE = 10;

    int REQUEST_CAPTURE_IMAGE = 20;
    int REQUEST_PICK_IMAGE_FROM_GALLERY = 22;
    int REQUEST_PLACE_PIKER = 25;
    int REQUEST_STORAGE_PERMISSION = 30;

    int ZOOM_RATIO = 16;
}
