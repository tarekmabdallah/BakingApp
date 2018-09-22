package com.gmail.tarekmabdallah91.bakingapp.room.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "userProfile")
public class UserEntry implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int rowId;

    private String firstName;
    private String lastName;
    private String userId;
    private int gender;
    private String imageUrl;
    private String locationUrl;


    @Ignore
    public UserEntry(String userId ,String firstName, String lastName, int gender, String imageUrl ,String locationUrl){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.locationUrl = locationUrl;
    }

    UserEntry(int rowId, String userId, String firstName, String lastName, int gender, String imageUrl, String locationUrl) {
        this.rowId = rowId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.locationUrl = locationUrl;
    }

    private UserEntry(Parcel in) {
        rowId = in.readInt();
        userId = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readInt();
        imageUrl = in.readString();
        locationUrl = in.readString();
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowId);
        dest.writeString(userId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(gender);
        dest.writeString(imageUrl);
        dest.writeString(locationUrl);
    }
}





