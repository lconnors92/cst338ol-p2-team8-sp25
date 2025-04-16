package com.example.project02;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.project02.database.AppDatabase;
import com.example.project02.database.CharacterDAO;
import com.example.project02.database.entities.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the Character Entity and DAO.
 */
public class ExampleUnitTest {

    private AppDatabase db;
    private CharacterDAO characterDAO;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()  // For testing only
                .build();
        characterDAO = db.characterDAO();
    }

    @After
    public void teardown() {
        db.close();
    }

    @Test
    public void testInsertCharacter() {
        Character c = new Character("TestHero", "Elf", "Rogue", 25, true, 101);
        characterDAO.insert(c);

        List<Character> results = characterDAO.getCharacterByUserId(101);
        assertEquals(1, results.size());
        assertEquals("TestHero", results.get(0).getName());
    }

    @Test
    public void testGetPublicCharacters() {
        Character publicChar = new Character("PublicOne", "Human", "Cleric", 28, true, 201);
        Character privateChar = new Character("PrivateOne", "Dwarf", "Barbarian", 40, false, 202);

        characterDAO.insert(publicChar);
        characterDAO.insert(privateChar);

        List<Character> publicList = characterDAO.getPublicCharacters();
        assertEquals(1, publicList.size());
        assertEquals("PublicOne", publicList.get(0).getName());
    }
}
