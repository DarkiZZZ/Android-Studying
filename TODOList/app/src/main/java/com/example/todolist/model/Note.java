package com.example.todolist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
