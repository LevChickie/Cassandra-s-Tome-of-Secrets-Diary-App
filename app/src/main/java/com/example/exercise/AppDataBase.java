package com.example.exercise;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Entry.class},version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EntryDao entryDao();
}
