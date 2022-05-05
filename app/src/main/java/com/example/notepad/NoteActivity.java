package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.notepad.data.Note;
import com.example.notepad.databinding.ActivityNoteBinding;
import com.example.notepad.tools.Keys;
import com.example.notepad.tools.TextStyle;
import java.time.LocalDateTime;

public class NoteActivity extends AppCompatActivity {
    private ActivityNoteBinding binding;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent data = new Intent();
//        data.putExtra("val", 123);
//        setResult(1, data);
        /*DBManager manager = new DBManager(this);
        int id = getIntent().getIntExtra("note_ID", 0);
        Note note = manager.findByID(id);*/

        note = (Note) getIntent().getSerializableExtra(Keys.NOTE_KEY.name());

        Log.e("FF", String.valueOf(note));
        binding.noteId.setText(String.valueOf(note.getId()));
        binding.noteHeader.setText(note.getHeader());
        binding.noteTime.setText(note.getTimeInString());
        binding.noteText.setText(note.getText());

        binding.styleButton.setOnCreateContextMenuListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.saveNoteMenu){
            if(note == null) note = new Note();
            note.setHeader(binding.noteHeader.getText().toString());
            note.setTime(LocalDateTime.now());
            note.setText(binding.noteText.getText().toString());

//            dbManager.save(note);

            Intent intent = new Intent();
            intent.putExtra(Keys.NOTE_KEY.name(), note);
            intent.putExtra("index", getIntent().getIntExtra("index", -1));
            setResult(RESULT_OK, intent);
            System.out.println("finish in NoteActivity");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.styleButton:
                menu.setHeaderTitle("STYLE");
                getMenuInflater().inflate(R.menu.style_note_menu, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        return super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case R.id.regularMenu:
                TextStyle.styleClear(binding.noteText);
                break;
            case R.id.boldMenu:
                TextStyle.style(binding.noteText, Typeface.BOLD);
                break;
            case R.id.italicMenu:
                TextStyle.style(binding.noteText, Typeface.ITALIC);
                break;
            case R.id.underlinedMenu:
                break;
        }
        return true;
    }








}