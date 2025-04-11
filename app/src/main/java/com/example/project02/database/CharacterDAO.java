package com.example.project02.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project02.database.entities.Character;

import java.util.List;

@Dao
public interface CharacterDAO {
    @Insert
    void insert(Character character);

    @Query("SELECT * FROM characters where userId = :userId")
    List<Character> getCharacterByUserId(int userId);

    @Query("SELECT * FROM characters WHERE isPublic = 1")
    List<Character> getPublicCharacters();
}

