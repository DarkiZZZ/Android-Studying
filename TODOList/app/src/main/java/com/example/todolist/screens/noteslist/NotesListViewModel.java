package com.example.todolist.screens.noteslist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.App;
import com.example.todolist.model.Note;

import java.util.List;

public class NotesListViewModel extends ViewModel {

    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllWithLiveData();

    public LiveData<List<Note>> getNoteLiveData(){
        return noteLiveData;
    }
}
