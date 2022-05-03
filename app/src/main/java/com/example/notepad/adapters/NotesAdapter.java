package com.example.notepad.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.NoteActivity;
import com.example.notepad.R;
import com.example.notepad.RecyclerActivity;
import com.example.notepad.adapters.NotesAdapter.NoteViewHolder;
import com.example.notepad.data.Note;
import com.example.notepad.tools.Keys;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final List<Note> notes;
    private final RecyclerActivity activity;
    private final LayoutInflater inflater;
    private final ActivityResultLauncher<Intent> resultLauncher;

    public NotesAdapter(List<Note> notes, RecyclerActivity activity) {
        this.notes = notes;
        this.activity = activity;
        inflater=LayoutInflater.from(activity);
        resultLauncher=activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e("RESULT: ", String.valueOf(result));
                }
        );
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.list_item, parent, false);
        item.setOnClickListener(view -> {
            Intent intent = new Intent(activity, NoteActivity.class);
            intent.putExtra(
                    Keys.NOTE_KEY.name(),
                    (Serializable) item.getTag(R.id.itemList)
            );

            resultLauncher.launch(intent);
        });
        return new NoteViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.idItemList.setText(String.valueOf(note.getId()));
        holder.headerItemList.setText(note.getHeader()); //
        holder.textItemList.setText(note.getText()); //
        holder.timeItemList.setText(note.getTimeInString());
        holder.itemView.setTag(R.id.itemList, note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView idItemList;
        TextView headerItemList;
        TextView timeItemList;
        TextView textItemList; //
        View itemView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            idItemList=itemView.findViewById(R.id.idItemList);
            headerItemList=itemView.findViewById(R.id.headerItemList);
            timeItemList=itemView.findViewById(R.id.timeItemList);
            textItemList = itemView.findViewById(R.id.textItemList); //
            this.itemView = itemView;
        }
    }
}