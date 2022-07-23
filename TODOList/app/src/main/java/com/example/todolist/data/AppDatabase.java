package com.example.todolist.data;

import androidx.room.Database;

import com.example.todolist.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase {
    public abstract NoteDao noteDao();
}
