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
 * Unit tests for the Character Entity and DAO. Similar to file in test, but runs/works in androidTest.
 * testInsertCharacter and testGetPublicCharacters written by KN
 * testPublicCharacterCreationSetsUserIdZero and testPrivateCharacterCreationRetainsUserId by LC
 * testDeleteAllCharacters by LC
 */
public class CharacterCreationUnitTest {

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

        List<Character> publicList = characterDAO.getPublicCharactersList();
        assertEquals(1, publicList.size());
        assertEquals("PublicOne", publicList.get(0).getName());
    }

    /**
     * Test public character creation works by setting userID to default non-player user ID:0.
     */
    @Test
    public void testPublicCharacterCreationSetsUserIdZero() {
        Character publicChar = new Character("DefaultUserIDTest", "Chicken", "Necromancer", 100, true, 123);
        if (publicChar.isPublic()) {
            publicChar.setUserId(0);
        }
        characterDAO.insert(publicChar);

        List<Character> results = characterDAO.getCharacterByUserId(0);
        assertEquals(1, results.size());
        assertEquals("DefaultUserIDTest", results.get(0).getName());
    }

    /**
     * Test private character creation works by associating character with logged in user's ID.
     */
    @Test
    public void testPrivateCharacterCreationRetainsUserId() {
        Character privateChar = new Character("RetainUserIDTest", "Angel", "Warrior", 3000, false, 42);
        characterDAO.insert(privateChar);

        List<Character> results = characterDAO.getCharacterByUserId(42);
        assertEquals(1, results.size());
        assertEquals("RetainUserIDTest", results.get(0).getName());
    }

    /**
     * Verify characters can be deleted.
     */
    @Test
    public void testDeleteAllCharacters() {
        characterDAO.insert(new Character("A", "B", "C", 10, true, 1));
        characterDAO.insert(new Character("X", "Y", "Z", 20, false, 2));

        characterDAO.deleteAll();

        List<Character> allFromUser1 = characterDAO.getCharacterByUserId(1);
        List<Character> allFromUser2 = characterDAO.getCharacterByUserId(2);

        assertTrue(allFromUser1.isEmpty());
        assertTrue(allFromUser2.isEmpty());
    }


}
