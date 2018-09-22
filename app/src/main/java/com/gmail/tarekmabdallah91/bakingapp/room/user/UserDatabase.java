package com.gmail.tarekmabdallah91.bakingapp.room.user;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gmail.tarekmabdallah91.bakingapp.R;

import timber.log.Timber;

@Database(entities = {UserEntry.class} , version = 1 , exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance ;
    private static final String DB_NAME = "usersDb";
    private static final Object LOCK = new Object();

    public static UserDatabase getInstance(Context context){
        if (null == instance){
            synchronized (LOCK){
                Timber.v(context.getString(R.string.creating_new_db_instance_msg));
                instance = Room.databaseBuilder(context.getApplicationContext()
                        , UserDatabase.class
                        , UserDatabase.DB_NAME)
                        .build();
            }
        }
        Timber.v(context.getString(R.string.getting_db_instance_msg));
        return instance;
    }

    public abstract UserDao userDao ();
}
