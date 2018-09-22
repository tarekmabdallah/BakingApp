package com.gmail.tarekmabdallah91.bakingapp.room.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM userProfile ")
    LiveData<List<UserEntry>> loadAllUsers ();

    @Query("SELECT * FROM userProfile ORDER BY `rowId` DESC LIMIT 1")
    UserEntry getLastUser ();

    @Query("SELECT * FROM userProfile WHERE userId = :userId")
    LiveData<UserEntry> getUsersByUserId (String userId);

    @Insert
    void insertUser (UserEntry userEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUser(UserEntry userEntry);

    @Query("DELETE FROM userProfile ")
    int clearUserDb ();

    @Query("DELETE FROM userProfile WHERE `rowId` = :rowId")
    int deleteUser (int rowId);

}

