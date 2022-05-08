package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notepad.databinding.ActivityMainBinding;
import com.example.notepad.tools.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        binding.helloButton.setOnClickListener(view -> {
            binding.textNote.setText("Hello world");
        });
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textNote.setText("");
            }
        });
        Log.e("FF", "create");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
//            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.saveMenu:
                Toast.makeText(MainActivity.this,
                        "The text has been saved successfully",
                        Toast.LENGTH_LONG).show();

                String textToSave = binding.textNote.getText().toString();
                FileManager.write(MainActivity.this, textToSave);
                break;
            case R.id.openMenu:
                Toast.makeText(
                        MainActivity.this,
                        "File is open successfully",
                        Toast.LENGTH_LONG).show();
                String textFromFile = FileManager.read(MainActivity.this);
                binding.textNote.setText(textFromFile);
                break;

            case R.id.settingsMenu:
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;

            case R.id.listMenu:
                Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
                MainActivity.this.startActivity(listIntent);
                break;

            case R.id.recyclerListMenu:
                Intent recyclerListIntent = new Intent(MainActivity.this, RecyclerActivity.class);
                MainActivity.this.startActivity(recyclerListIntent);
                break;


        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("FF", "start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("FF", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("FF", "resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("FF", "restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("FF", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("FF", "destroy");
    }

}