package com.example.project02.database.entities;
//testing123123123123

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02.database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.CHARACTER_TABLE)
public class Character {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String species;
    private String characterClass;
    private int age;
    private boolean isPublic;
    private int userId;

    @Override
    @NonNull
    public String toString() {
        if (userId == 0) {
            return "Character: " + name + "\n" +
                    "Class: " + characterClass + "\n" +
                    "Species: " + species + "\n" +
                    "Age: " + age;
        } else {
            return "Character: " + name + "\n" +
                    "Class: " + characterClass + "\n" +
                    "Species: " + species + "\n" +
                    "Age: " + age + "\n" +
                    "Creator ID: " + userId;
        }
    }

    public Character(String name, String species, String characterClass, int age, boolean isPublic, int userId) {
        this.name = name;
        this.species = species;
        this.characterClass = characterClass;
        this.age = age;
        this.isPublic = isPublic;
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id && age == character.age && isPublic == character.isPublic && userId == character.userId && Objects.equals(name, character.name) && Objects.equals(species, character.species) && Objects.equals(characterClass, character.characterClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, species, characterClass, age, isPublic, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
