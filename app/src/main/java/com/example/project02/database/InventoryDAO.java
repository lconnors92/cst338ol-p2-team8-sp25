package com.example.project02.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project02.database.entities.Inventory;

import java.util.List;

@Dao
public interface InventoryDAO {
    @Insert
    void insert(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Query("SELECT * FROM inventory WHERE userId = :userId")
    List<Inventory> getInventoryByUserId(int userId);
}
