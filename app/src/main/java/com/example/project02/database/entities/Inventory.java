package com.example.project02.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "inventory",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Character.class,
                        parentColumns = "id",
                        childColumns = "characterId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)

public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId; //Links to user
    private int characterId; //Links to Character

    public Inventory(int userId, int characterId) {
        this.userId = userId;
        this.characterId = characterId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return id == inventory.id && userId == inventory.userId && characterId == inventory.characterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, characterId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
}
