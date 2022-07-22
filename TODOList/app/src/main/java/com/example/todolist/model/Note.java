package com.example.todolist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int uId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "isDone")
    public boolean isDone;

    public Note(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return uId == note.uId && time == note.time && isDone == note.isDone && Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, text, time, isDone);
    }
}
