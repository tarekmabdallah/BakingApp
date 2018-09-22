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
package com.gmail.tarekmabdallah91.bakingapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.room.user.UserEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.BitmapUtils;
import com.gmail.tarekmabdallah91.bakingapp.utils.UserDataUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.EMPTY_TEXT;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.GENDER_FEMALE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.GENDER_MALE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_INTENT_TYPE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.PROVIDER_AUTHORITY;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.REQUEST_CAPTURE_IMAGE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.REQUEST_PICK_IMAGE_FROM_GALLERY;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.REQUEST_STORAGE_PERMISSION;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_FIRST_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_GENDER;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_LAST_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_LOCATION;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_PICTURE_PATH;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.USER_STRING_ID;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_picture)
    ImageView profilePictureIv;
    @BindView(R.id.take_picture)
    TextView takePictureTV;
    @BindView(R.id.get_image_from_gallery)
    TextView getImageFromGalleryTV;
    @BindView(R.id.get_first_name_tv)
    TextView getFirstNameTV;
    @BindView(R.id.get_last_name_tv)
    TextView getLastNameTV;
    @BindView(R.id.get_gender)
    RadioGroup getGenderRG;
    @BindView(R.id.get_location)
    TextView getLocationIV;
    @BindView(R.id.save_btn)
    ImageView saveBtn;
    @BindView(R.id.cancel_btn)
    ImageView cancelBtn;
    @BindView(R.id.get_first_name_et)
    EditText getFirstNameET;
    @BindView(R.id.get_last_name_et)
    EditText getLastNameET;

    private Bundle userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initiateValues();
    }

    private void initiateValues() {
        userData = new Bundle();
    }

    private void setUserEntry() {
        // get data from bundle
        UserEntry userEntry = new UserEntry(
                userData.getString(USER_STRING_ID, USER_STRING_ID),
                userData.getString(USER_FIRST_NAME, USER_FIRST_NAME),
                userData.getString(USER_LAST_NAME, USER_LAST_NAME),
                userData.getInt(USER_GENDER, GENDER_MALE),
                userData.getString(USER_PICTURE_PATH, EMPTY_TEXT),
                userData.getString(USER_LOCATION, EMPTY_TEXT));
    }

    private void openTheCamera() {
        Intent openTheCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (openTheCamera.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                String imageFilePath = photoFile.getAbsolutePath();
                userData.putString(USER_PICTURE_PATH, imageFilePath);
                Uri photoURI = FileProvider.getUriForFile(this, PROVIDER_AUTHORITY, photoFile);
                openTheCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(openTheCamera, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private void pickImageFromGallery() {
        Intent pickImageFromGallery = new Intent(Intent.ACTION_PICK); //  , Uri.parse("images/*")
        pickImageFromGallery.setType(IMAGE_INTENT_TYPE);
        startActivityForResult(pickImageFromGallery, REQUEST_PICK_IMAGE_FROM_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // to get uri for profile picture to save it in Room (UserEntry)
        String imageFilePath = userData.getString(USER_PICTURE_PATH);
        if (REQUEST_CAPTURE_IMAGE == requestCode && RESULT_OK == resultCode) {
            Glide.with(this).load(imageFilePath).into(profilePictureIv);
        } else if (REQUEST_PICK_IMAGE_FROM_GALLERY == requestCode && RESULT_OK == resultCode) {
            if (data != null) {
                Uri uri = data.getData();
                userData.putString(USER_PICTURE_PATH, String.valueOf(uri));
                Glide.with(this).load(imageFilePath).into(profilePictureIv);
            }
        }

    }

    @OnClick(R.id.take_picture)
    void takePicture() {
        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            openTheCamera();
        }
    }

    @OnClick(R.id.get_image_from_gallery)
    void getImageFormGallery() {
        pickImageFromGallery();
    }

    @OnClick(R.id.save_btn)
    void saveUserData() {
        // get texts from EditTexts
        if (!getValidUserName()) {
            UserDataUtils.showToastMsg(this, getString(R.string.not_valid_data_msg));
            return;
        }
        // get user String Id - TODO to get it's value from Firebase.
        String userId = UserDataUtils.setUserId();
        userData.putString(USER_STRING_ID, userId);
        // get gender if not choose any tell him to chose one
        getUserGender();
        // get location if null tell him to get location

        // TODO show picture profile and name in nav drawer instead of android Icon and dummy data
        setUserEntry();
    }

    /**
     * get texts from EditTexts
     *
     * @return true if  the user entered valid text or false if not valid .
     */
    private boolean getValidUserName() {
        String firstName = UserDataUtils.getTextsFromEditText(this, getFirstNameET);
        String lastName = UserDataUtils.getTextsFromEditText(this, getLastNameET);
        if (EMPTY_TEXT.equals(firstName)) return false;
        else userData.putString(USER_FIRST_NAME, firstName);

        if (EMPTY_TEXT.equals(lastName)) return false;
        else userData.putString(USER_LAST_NAME, lastName);

        return true;
    }

    /**
     * to get user gender from user choice on the profile Activity
     */
    private void getUserGender() {
        RadioGroup.OnCheckedChangeListener changeListenerRadioGroup = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = getGenderRG.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                switch (radioButton.getId()) {
                    case R.id.female_choice:
                        userData.putInt(USER_GENDER, GENDER_FEMALE);
                        break;
                    default:
                        userData.putInt(USER_GENDER, GENDER_MALE);
                }
            }
        };
        getGenderRG.setOnCheckedChangeListener(changeListenerRadioGroup);
        Timber.d(getString(R.string.user_choose_gender_msg), userData.getInt(USER_GENDER));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > ZERO
                        && grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    openTheCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


}
