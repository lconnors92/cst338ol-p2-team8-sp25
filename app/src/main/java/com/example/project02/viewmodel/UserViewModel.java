package com.example.project02.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.User;

public class UserViewModel extends AndroidViewModel {
    private final AppDatabase database;

    public UserViewModel(Application application) {
        super(application);
        database = AppDatabase.getDatabase(application);
    }

    public LiveData<User> getUserByUsername(String username){
        return database.userDAO().getUserByUserName(username);
    }

    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            database.userDAO().insert(user);
        });
    }
}
