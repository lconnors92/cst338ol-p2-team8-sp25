package com.example.project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project02.database.entities.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);
}

