package com.example.project02;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testCharacterCreation() {
        assertEquals("Indignatius", character.getName());
        assertEquals("Elf", character.getSpecies());
        assertEquals("Clergy", character.getCharacterClass());
        assertEquals(120, character.getAge());
        assertTrue("Character should be public", character.isPublic());
        assertEquals(0, character.getUserId());
    }

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
}
