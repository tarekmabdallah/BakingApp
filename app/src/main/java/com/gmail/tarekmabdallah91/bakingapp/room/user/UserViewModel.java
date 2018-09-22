package com.gmail.tarekmabdallah91.bakingapp.room.user;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


class UserViewModel extends AndroidViewModel {

    private final UserDatabase userDatabase;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userDatabase = UserDatabase.getInstance(application);

    }

    public LiveData<UserEntry> getUserById (String userId){
        return userDatabase.userDao().getUsersByUserId(userId);
    }
}
