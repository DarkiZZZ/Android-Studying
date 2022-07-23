package com.example.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "isDone")
    public boolean isDone;

    public Note(){}


    protected Note(Parcel in) {
        uId = in.readInt();
        text = in.readString();
        time = in.readLong();
        isDone = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uId);
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeByte((byte) (isDone ? 1 : 0));
    }
}
