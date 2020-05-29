package com.example.exercise;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EntryDao {
    @Query("SELECT * FROM entry")
    List<Entry> getAllEntry();

     @Insert
    void insertAll(Entry... entries);

     @Query("DELETE FROM entry")
    void deleteAll();

    @Query("DELETE FROM entry WHERE id = :id")
    void deleteById(int id);
    @Delete
    void delete(Entry entry);
}
