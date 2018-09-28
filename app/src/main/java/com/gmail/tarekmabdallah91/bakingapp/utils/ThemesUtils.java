package com.gmail.tarekmabdallah91.bakingapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.EditProfileActivity;
import com.gmail.tarekmabdallah91.bakingapp.activities.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.BLACK_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.GREEN_KEYWORD;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.PREFERENCES_FILE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.PURPLE_KEYWORD;

public class ThemesUtils {

    private static SharedPreferences sharedPreferences;
    private static String themeOfApp;
    private static String THEMES_LIST_KEY;
    private static String DEFAULT_THEME_KEY;


    /**
     * get theme by selected shared preferences key
     *
     * @param context for activity context
     * @return id of theme to be used by setTheme()
     */
    public static int getThemeByKey(Context context) {
        if (sharedPreferences == null) {
            final String SHARED_PREFERENCES_FILE = context.getPackageName() + PREFERENCES_FILE;
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);
        }
        THEMES_LIST_KEY = context.getString(R.string.key_background_color_list);
        DEFAULT_THEME_KEY = context.getString(R.string.value_color_white);
        themeOfApp = sharedPreferences.getString(THEMES_LIST_KEY, DEFAULT_THEME_KEY);
        int themeId;
        switch (themeOfApp) {
            case BLACK_KEYWORD:
                themeId = R.style.Base_Theme_App;
                break;
            case GREEN_KEYWORD:
                themeId = R.style.Theme_App_Mint;
                break;
            case PURPLE_KEYWORD:
                themeId = R.style.Theme_App_purple;
                break;
            default:
                themeId = R.style.AppTheme;
        }
        return themeId;
    }

    /**
     * to check if the user selected new theme or not
     *
     * @return false if themeOfApp.equals(currentTheme) or  sharedPreferences == null (to handle the  null pointer Exception)
     */
    public static boolean isThemeChanged() {
        if (null != sharedPreferences) {
            String currentTheme = sharedPreferences.getString(THEMES_LIST_KEY, DEFAULT_THEME_KEY);
            return !themeOfApp.equals(currentTheme);
        }
        return false;
    }

    /**
     * to be usable in each activity
     *
     * @param context -
     * @param id      -
     */
    public static void setNavSelections(Context context, int id) {
        if (id == R.id.profile_user) {
            context.startActivity(new Intent(context, EditProfileActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            context.startActivity(new Intent(context, SettingsActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }
}
