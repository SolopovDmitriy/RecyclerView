package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.notepad.adapters.NotesAdapter;
import com.example.notepad.data.DBManager;
import com.example.notepad.data.Note;
import com.example.notepad.databinding.ActivityRecyclerBinding;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private ActivityRecyclerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Note> notes = Arrays.asList(
                new Note(0, "A", LocalDateTime.now(), "AAAAAAAAAAAA"),
                new Note(0, "B", LocalDateTime.now(), "BBBBBBBBBBBB"),
                new Note(0, "C", LocalDateTime.now(), "CCCCCCCCCCCC")
        );

        DBManager manager = new DBManager(this);
        notes.forEach(manager::save);
        notes = manager.findAllToList();

        binding.recyclerView.setAdapter(new NotesAdapter(notes, this));

//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                this, LinearLayoutManager.HORIZONTAL, false);

//        binding.recyclerView.setLayoutManager(layoutManager);


//        // in  activity_recycler.xml
//        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
//        android:orientation="vertical"


    }
}