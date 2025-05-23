package com.example.project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + AppDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("SELECT password FROM " + AppDatabase.USER_TABLE + " WHERE id = :userId")
    String getPasswordByUserId(int userId);

    @Query("UPDATE " + AppDatabase.USER_TABLE + " SET password = :newPassword WHERE id = :userId")
    void updatePassword(int userId, String newPassword);
}
