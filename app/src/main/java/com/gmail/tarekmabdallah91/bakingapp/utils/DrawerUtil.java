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
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.EditProfileActivity;
import com.gmail.tarekmabdallah91.bakingapp.activities.MainActivity;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.models.UserEntry;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.EMPTY_TEXT;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.INVALID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.THREE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.TWO;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class DrawerUtil {


    private final Activity activity;
    private final Toolbar toolbar;
    private final int colorRes;
    private final String title;
    private final String subTitle;
    private AccountHeader headerResult;
    private Bitmap image;


    public DrawerUtil(final Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;

        colorRes = R.color.nav_background_default_theme;
        UserEntry user = RoomPresenter.getLastUserEntry(activity);
        if (null != user) {
            title = user.getFirstName();
            subTitle = user.getLastName();
            image = BitmapUtils.rotateBitmap(BitmapUtils.uriToBitmap
                    (activity, Uri.parse(user.getImageFilePath())));
        } else {
            title = activity.getString(R.string.app_name);
            subTitle = EMPTY_TEXT;
        }

        setHeaderResult();
        buildDrawer();
    }

    public Drawer buildDrawer() {
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(ZERO).withName(EMPTY_TEXT);
        drawerEmptyItem.withEnabled(false);

        SecondaryDrawerItem main = new SecondaryDrawerItem().withIdentifier(TWO)
                .withName(R.string.title_activity_main).withIcon(R.drawable.ic_recipe_book_main_list_512dp);
        SecondaryDrawerItem editProfile = new SecondaryDrawerItem().withIdentifier(THREE)
                .withName(R.string.profile).withIcon(R.drawable.ic_user2);

        // set divider
        //DividerDrawerItem dividerDrawerItem = new DividerDrawerItem();

        // set onDrawerClickListener
        Drawer.OnDrawerItemClickListener onDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = null;
                if (drawerItem.getIdentifier() == THREE && !(activity instanceof EditProfileActivity)) {
                    intent = new Intent(activity, EditProfileActivity.class);
                } else if (drawerItem.getIdentifier() == TWO && !(activity instanceof MainActivity)) {
                    intent = new Intent(activity, MainActivity.class);
                }
                if (null != intent) activity.startActivity(intent);
                return true;
            }
        };

        return new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withInnerShadow(true)
                .withSliderBackgroundDrawableRes(colorRes)
                .withCloseOnClick(true)
                .withSelectedItem(INVALID)
                .addDrawerItems(main, editProfile)
                .withOnDrawerItemClickListener(onDrawerItemClickListener)
                .withAccountHeader(headerResult) //Now create your drawer and pass the AccountHeader.Result
                .build();
    }

    private void setHeaderResult() {
        headerResult = getAccountHeader(activity, title, subTitle, image);
    }

    private AccountHeader getAccountHeader(Activity activity, String title, String subTitle, Bitmap image) {
        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder();
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(title).withEmail(subTitle);
        if (null != image) {
            profileDrawerItem.withIcon(image);
        }
        accountHeaderBuilder
                // .withHeaderBackground(R.color.nav_background)
                .withActivity(activity)
                .addProfiles(profileDrawerItem)
                .withCurrentProfileHiddenInList(true)
                .withOnlyMainProfileImageVisible(true)
                .withResetDrawerOnProfileListClick(true);

        return accountHeaderBuilder.build();
    }
}