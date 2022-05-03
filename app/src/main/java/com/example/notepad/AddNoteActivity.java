package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.notepad.data.Note;
import com.example.notepad.databinding.ActivityAddNoteBinding;
import com.example.notepad.tools.Keys;

import java.time.LocalDateTime;

public class AddNoteActivity extends AppCompatActivity {

    private ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // setContentView(R.layout.activity_add_note);

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(view -> {
            Note note = new Note();
            note.setHeader(binding.addNoteHeader.getText().toString());
            note.setTime(LocalDateTime.now());
            note.setText(binding.addNoteText.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(Keys.NOTE_KEY.name(), note);
            setResult(RESULT_OK, intent);
            System.out.println("finish in AddNoteActivity");
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }



}