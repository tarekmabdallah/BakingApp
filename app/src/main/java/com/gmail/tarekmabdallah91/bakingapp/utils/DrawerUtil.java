/*
thanks to
https://android.jlelse.eu/android-using-navigation-drawer-across-multiple-activities-the-easiest-way-b011f152aebd
https://github.com/mikepenz/MaterialDrawer

Copyright 2018 Mike Penz

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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.EditProfileActivity;
import com.gmail.tarekmabdallah91.bakingapp.activities.MainActivity;
import com.gmail.tarekmabdallah91.bakingapp.activities.SettingsActivity;
import com.gmail.tarekmabdallah91.bakingapp.data.room.PresenterRoom;
import com.gmail.tarekmabdallah91.bakingapp.models.UserEntry;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.EMPTY_TEXT;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.FOUR;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INVALID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.THREE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.TWO;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;


public class DrawerUtil {
    public static void getDrawer(final Activity activity, Toolbar toolbar) {

        UserEntry user = PresenterRoom.getLastUserEntry(activity);
        String title;
        String subTitle;
        Bitmap image = null;
        if (null != user) {
            title = user.getFirstName();
            subTitle = user.getLastName();
            image = user.getUserBitmap(activity);
        } else {
            title = activity.getString(R.string.app_name);
            subTitle = EMPTY_TEXT;
        }

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(ZERO).withName(EMPTY_TEXT);
        drawerEmptyItem.withEnabled(false);

       /* PrimaryDrawerItem drawerItemManagePlayers = new PrimaryDrawerItem().withIdentifier(ONE)
                .withName(R.string.app_name).withIcon(R.drawable.ic_launcher_foreground);
        PrimaryDrawerItem drawerItemManagePlayersTournaments = new PrimaryDrawerItem()
                .withIdentifier(TWO).withName(R.string.profile).withIcon(R.drawable.ic_vol_type_speaker_dark);*/

        SecondaryDrawerItem main = new SecondaryDrawerItem().withIdentifier(TWO)
                .withName(R.string.title_activity_main).withIcon(R.drawable.ic_menu_send);
        SecondaryDrawerItem editProfile = new SecondaryDrawerItem().withIdentifier(THREE)
                .withName(R.string.profile).withIcon(R.drawable.ic_menu_camera);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(FOUR)
                .withName(R.string.settings).withIcon(R.drawable.ic_menu_manage);


        // Create the AccountHeader
        AccountHeader headerResult = getAccountHeader(activity, title, subTitle, image);


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(INVALID)
                .addDrawerItems(
                        new DividerDrawerItem(),
                        main,
                        editProfile,
                        settings)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == THREE && !(activity instanceof EditProfileActivity)) {
                            intent = new Intent(activity, EditProfileActivity.class);
                        } else if (drawerItem.getIdentifier() == FOUR && !(activity instanceof SettingsActivity)) {
                            intent = new Intent(activity, SettingsActivity.class);
                        } else if (drawerItem.getIdentifier() == TWO && !(activity instanceof MainActivity)) {
                            intent = new Intent(activity, MainActivity.class);
                        }
                        if (null != intent) view.getContext().startActivity(intent);
                        return true;
                    }
                })
                .withAccountHeader(headerResult) //Now create your drawer and pass the AccountHeader.Result
                .build();
    }

    // TODO to update user data once the user edit the profile (not after re launch the app)
    private static AccountHeader getAccountHeader(Activity activity, String title, String subTitle, Bitmap image) {
        if (null != image) {
            return new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.ic_launcher_background)
                    .addProfiles(new ProfileDrawerItem()
                            .withName(title)
                            .withEmail(subTitle)
                            .withIcon(image))
                    .build();
        } else {
            return new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.ic_launcher_background)
                    .addProfiles(new ProfileDrawerItem()
                            .withName(title)
                            .withEmail(subTitle))
                    .withCurrentProfileHiddenInList(true)
                    .withOnlyMainProfileImageVisible(true)
                    .withResetDrawerOnProfileListClick(true)
                    .build();
        }
    }
}