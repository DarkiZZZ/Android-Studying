package com.example.todolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.model.Note;

import java.util.List;

public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllWithLiveData();

    @Query("SELECT * FROM Note WHERE uId IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM Note WHERE uId = :uId LIMIT 1")
    Note findById(int uId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
