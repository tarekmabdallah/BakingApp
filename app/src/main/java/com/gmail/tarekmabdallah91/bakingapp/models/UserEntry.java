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

@Entity(tableName = "userProfile")
public class UserEntry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int rowId;

    private String firstName;
    private String lastName;
    private String userId;
    private int gender;
    private String imageFilePath; // when user capture image
    private String locationUrl;
    private double latitude;
    private double longitude;


    @Ignore
    public UserEntry(String userId, String firstName, String lastName, int gender, String imageFilePath,
                     String locationUrl, double latitude, double longitude) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.imageFilePath = imageFilePath;
        this.locationUrl = locationUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserEntry(int rowId, String userId, String firstName, String lastName, int gender,
                     String imageFilePath, String locationUrl, double latitude, double longitude) {
        this.rowId = rowId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.imageFilePath = imageFilePath;
        this.locationUrl = locationUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    private UserEntry(Parcel in) {
        rowId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        userId = in.readString();
        gender = in.readInt();
        imageFilePath = in.readString();
        locationUrl = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<UserEntry> CREATOR = new Creator<UserEntry>() {
        @Override
        public UserEntry createFromParcel(Parcel in) {
            return new UserEntry(in);
        }

        @Override
        public UserEntry[] newArray(int size) {
            return new UserEntry[size];
        }
    };

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUserId() {
        return userId;
    }

    public int getRowId() {
        return rowId;
    }

    public int getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userId);
        dest.writeInt(gender);
        dest.writeString(imageFilePath);
        dest.writeString(locationUrl);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}