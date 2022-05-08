package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.notepad.data.Note;
import com.example.notepad.databinding.ActivityNoteBinding;
import com.example.notepad.tools.Keys;
import com.example.notepad.tools.TextStyle;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.time.LocalDateTime;

public class NoteActivity extends AppCompatActivity implements ColorPickerDialogListener {
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

//        Log.e("FF", String.valueOf(note));
        if (note != null) {
            binding.noteId.setText(String.valueOf(note.getId()));
            binding.noteHeader.setText(note.getHeader());
            binding.noteTime.setText(note.getTimeInString());
            binding.noteText.setText(note.getText());
        }

//        binding.styleButton.setOnCreateContextMenuListener(this);
//        binding.styleButton.setOnClickListener(NoteActivity.this::openContextMenu);
        PopupMenu stylePopupMenu = new PopupMenu(this, binding.styleButton);
        stylePopupMenu.inflate(R.menu.style_popup_menu);
        stylePopupMenu.setOnMenuItemClickListener(item -> {
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
                    TextStyle.styleUnderline(binding.noteText);
                    break;
            }
            return true;
        });
        binding.styleButton.setOnClickListener(view -> stylePopupMenu.show());

        // binding.colorButton.setOnClickListener(this::createColorDialog);
        binding.colorButton.setOnClickListener(view -> createColorDialog(view));

        binding.backgroundButton.setOnClickListener(this::createColorDialog);
        binding.clearAllStylesButton.setOnClickListener(view->{
            new AlertDialog.Builder(this)
                    .setTitle("Clear all styles?")
                    .setCancelable(true)
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        Log.e("FF", "alert cancelled");
                    })
                    .setPositiveButton("OK", ((dialogInterface, i) -> {
                        TextStyle.clearAll(binding.noteText);
                    }))
                    .create()
                    .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveNoteMenu) {
            if (note == null) note = new Note();
            note.setHeader(binding.noteHeader.getText().toString());
            note.setTime(LocalDateTime.now());
            note.setText(binding.noteTime.getText().toString());

            Intent intent = new Intent();
            intent.putExtra("note", note);
            intent.putExtra("index", getIntent().getIntExtra("index", -1));
            setResult(RESULT_OK, intent);
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
                getMenuInflater().inflate(R.menu.style_context_menu, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        return super.onContextItemSelected(item);
        switch(item.getItemId()){
            case R.id.clearStylesMenu:
                break;
        }
        return true;
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        if(dialogId == R.id.colorButton){
            Log.e("FF", "" + color);
            TextStyle.setColor(binding.noteText, color);
        }
        else if(dialogId == R.id.backgroundButton){
            TextStyle.setBackColor(binding.noteText, color);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    private void createColorDialog(View view){
        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setColor(Color.RED)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(view.getId())
                .show(this);

        InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}