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

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;

import java.util.Random;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.EMPTY_TEXT;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.LETTERS_ONLY_REGEX;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.MAX_RANDOM_BOUND;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_STRING_ID;

public class UserDataUtils {


    private static final String TAG = UserDataUtils.class.getSimpleName();
    /**
     * get text from edit texts
     * check the value
     * if not null or bad save it
     * else show toast msg for him
     *
     * @param editText -
     * @return -
     */
    public static String getTextsFromEditText(Context context, EditText editText) {
        return checkTexts(context, editText.getText().toString().trim());
    }

    /**
     * check the value
     * if not null or bad save it
     * else show toast msg for him
     */
    private static String checkTexts(Context context, String text) {
        String newText = EMPTY_TEXT;
        if (null != text && !text.isEmpty()) {
            Log.d(TAG, String.format(context.getString(R.string.text_is_msg), text));
            if (text.matches(LETTERS_ONLY_REGEX)) {
                newText = text;
            }
        }
        return newText;
    }

    /**
     * to show Toast msg
     *
     * @param context -
     * @param msg     to be shown
     */
    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * to set user Id temporarily tell get the right value from Firebase
     *
     * @return -
     */
    public static String setUserId() {
        int randomValue = getRandomValue();
        return String.valueOf(USER_STRING_ID + randomValue);
    }

    /**
     * @return random value
     */
    private static int getRandomValue() {
        return new Random().nextInt(MAX_RANDOM_BOUND);
    }


}
