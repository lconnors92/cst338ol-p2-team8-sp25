package com.example.project02.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE isPublic = 1")
    LiveData<List<Character>> getPublicCharacters();

    /**
     * LiveData was making the accompanying test angry, the Query below allows test for public characters the same way
     * as above. As above, so below.
     */

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE isPublic = 1")
    List<Character> getPublicCharactersList();

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :loggedInUserId ORDER BY userId DESC")
    LiveData<List<Character>> getRecordSetUserId(int loggedInUserId);

    @Query("SELECT * FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :loggedInUserId ORDER BY userId DESC")
    LiveData<List<Character>> getRecordsetUserIdLiveData(int loggedInUserId);

    @Query("DELETE from " + AppDatabase.CHARACTER_TABLE) void deleteAll();

    @Query("SELECT COUNT(*) FROM " + AppDatabase.CHARACTER_TABLE + " WHERE userId = :userId")
    LiveData<Integer> getCharacterCountByUser(int userId);

    @Update
    void update(Character selectedCharacter);
}

