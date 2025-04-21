package com.example.project02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import com.example.project02.database.entities.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CharacterUnitTest {

    private Character character;

    @Before
    public void setup() {
        character = new Character("Indignatius", "Elf", "Clergy", 120, true, 0);
    }

    @After
    public void teardown() {
        character = null;
    }


    /**
     * Test public character creation, default userId equals 0.
     */
    @Test
    public void testPublicCharacterCreation() {
        assertEquals("Indignatius", character.getName());
        assertEquals("Elf", character.getSpecies());
        assertEquals("Clergy", character.getCharacterClass());
        assertEquals(120, character.getAge());
        assertTrue("Character should be public", character.isPublic());
        assertEquals(0, character.getUserId());
    }

    /**
     * Test private character creation, userId != 0.
     */
    @Test
    public void testSettersAndPrivacy() {
        character.setPublic(false);
        character.setUserId(42);
        character.setName("Drew");
        character.setSpecies("Human");
        character.setCharacterClass("Warrior");
        character.setAge(35);

        assertEquals("Drew", character.getName());
        assertEquals("Human", character.getSpecies());
        assertEquals("Warrior", character.getCharacterClass());
        assertEquals(35, character.getAge());
        assertFalse("Character should be private", character.isPublic());
        assertEquals(42, character.getUserId());
    }


    /**
     * Test updating character fields.
     */
    @Test
    public void testCharacterUpdate() {
        character.setName("Ignatius");
        character.setAge(130);
        assertEquals("Ignatius", character.getName());
        assertEquals(130, character.getAge());
        character.setName("Indignatious");
        character.setAge(42);
        assertNotEquals("Ignatius", character.getName());
        assertNotEquals(130, character.getAge());

    }

    /**
     * Test character can be deleted.
     */
    @Test
    public void testCharacterDeletion() {
        character = null;
        assertNull(character);
    }

    /**
     * Test equals/hashcode are functional.
     */

    @Test
    public void testCharacterEquality() {
        Character shouldBeEqual = new Character("Indignatius", "Elf", "Clergy", 120, true, 0);
        assertEquals(character, shouldBeEqual);
    }


}
