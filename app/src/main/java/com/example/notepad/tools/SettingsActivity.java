package com.example.notepad.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.notepad.R;
import com.example.notepad.Settings;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view -> {
           Settings.save(SettingsActivity.this);
        });


        Button openButton = findViewById(R.id.openButton);

        openButton.setOnClickListener(view -> {
            Settings.open(SettingsActivity.this);
        });

    }


}
