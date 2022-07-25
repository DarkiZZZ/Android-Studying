package com.example.todolist.screens.noteslist;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.databinding.ListNoteItemBinding;
import com.example.todolist.model.Note;
import com.example.todolist.screens.details.NotesDetailsFragment;

import java.util.List;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent,  false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }


    private SortedList<Note> sortedList;

    public Adapter(){

        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (!o2.isDone && o1.isDone){
                    return 1;
                }
                if (o2.isDone && o1.isDone){
                    return -1;
                }
                return (int) (o2.time - o1.time);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uId == item2.uId;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    public void setItems(List<Note> notes){
        sortedList.replaceAll(notes);
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder{

        private ListNoteItemBinding binding;
        Note note;
        Boolean isSilentUpdate;



        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                NotesDetailsFragment fragment = new NotesDetailsFragment();
                if (note != null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(NotesDetailsFragment.EXTRA_NOTE, note);
                    fragment.setArguments(bundle);
                }
                FragmentManager fragmentManager = fragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });

            binding.deleteButton.setOnClickListener(v -> App.getInstance().getNoteDao().delete(note));

            binding.isDoneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isSilentUpdate){
                    note.isDone = isChecked;
                    App.getInstance().getNoteDao().update(note);
                }
                updateStrokeOut();
            });
        }

        public void bind(Note note){
            this.note = note;
            binding.noteTextView.setText(note.text);

            updateStrokeOut();

            isSilentUpdate = true;
            binding.isDoneCheckBox.setChecked(note.isDone);
            isSilentUpdate = false;
        }

        private void updateStrokeOut(){
            if (note.isDone){
                binding.noteTextView.setPaintFlags(binding.noteTextView.getPaintFlags() |
                        Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                binding.noteTextView.setPaintFlags(binding.noteTextView.getPaintFlags() &
                        ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
