package com.example.todolist.screens.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.databinding.FragmentNotesDetailsBinding;
import com.example.todolist.model.Note;
import com.example.todolist.screens.noteslist.NotesListFragment;

public class NotesDetailsFragment extends Fragment {

    private FragmentNotesDetailsBinding binding;
    private static final String EXTRA_NOTE = "NotesDetailsFragment.EXTRA_NOTE";
    private Note note;

    public static void startFragment(Fragment fragment, Note note){
        if (note != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_NOTE, note);
            fragment.setArguments(bundle);
        }
        FragmentManager fragmentManager = fragment.getFragmentManager();
        //todo
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public View onCreateView(
            LayoutInflater  inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentNotesDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments().getParcelable(EXTRA_NOTE) != null){
            note = getArguments().getParcelable(EXTRA_NOTE);
            binding.noteEditText.setText(note.text);
        }
        else {
            note = new Note();
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.noteEditText.length() > 0){
                    note.text = binding.noteEditText.getText().toString();
                    note.isDone = false;
                    note.time = System.currentTimeMillis();
                    if (getArguments().getParcelable(EXTRA_NOTE) != null){
                        App.getInstance().getNoteDao().update(note);
                    }
                    else {
                        App.getInstance().getNoteDao().insert(note);
                    }
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new NotesListFragment());
                    fragmentTransaction.commit();
                }
            }
        });

        binding.backToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, new NotesListFragment());
                fragmentTransaction.commit();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}