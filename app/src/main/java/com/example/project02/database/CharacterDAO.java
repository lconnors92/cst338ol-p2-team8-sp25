package com.example.project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02.database.entities.Character;


import java.util.List;

@Dao
public interface CharacterDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Character character);

    @Delete
    void delete(Character character);

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :userId")
    List<Character> getCharacterByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE isPublic")
    List<Character> getPublicCharacters();

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :loggedInUserId ORDER BY userId DESC")
    LiveData<List<Character>> getRecordsetUserId(int loggedInUserId);

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :loggedInUserId ORDER BY userId DESC")
    LiveData<List<Character>> getRecordsetUserIdLiveData(int loggedInUserId);

    @Query("DELETE from " + AppDatabase.CHARACTER_TABLE) void deleteAll();
}

