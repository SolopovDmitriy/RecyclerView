package com.example.notepad;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.notepad.data.DBManager;
import com.example.notepad.data.Note;
import com.example.notepad.tools.Keys;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Cursor cursor;
    private ActivityResultLauncher<Intent> resultLauncher;
    private ActivityResultLauncher<Intent> createNoteResultLauncher;
    private List<Note> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DBManager dbManager = new DBManager(this);
//        notes = Arrays.asList(
//                new Note(0, "Tank", LocalDateTime.now(), "Tiger - Panzerkampfwagen VI Ausf.H - E, «Тигр» — немецкий тяжёлый танк времён Второй мировой войны, прототипом которого являлся танк VK4501, разработанный в 1942 году фирмой «Хеншель»."),
//                new Note(0, "Airplane", LocalDateTime.now(), "Истреби́тель — военный самолёт, предназначенный в первую очередь для уничтожения воздушных целей противника."),
//                new Note(0, "Submarine", LocalDateTime.now(), "Подво́дная ло́дка — класс кораблей, способных погружаться и длительное время действовать в подводном положении. ")
//        );
//        for(Note note : notes){
//            dbManager.save(note);
//        }
        notes= dbManager.findAllToList();
        notes.forEach(note -> Log.e("FF", note.toString() + "\n"));

        ListView listView = findViewById(R.id.listView);

        /*ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, notes
        );
        listView.setAdapter(arrayAdapter);*/

        /*Cursor cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.two_line_list_item, cursor,
                new String[]{DBManager.HEADER, DBManager.TIME},
                new int[]{android.R.id.text1, android.R.id.text2}, 0
        );
        listView.setAdapter(cursorAdapter);*/

        cursor = dbManager.findAllToCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, R.layout.list_item, cursor,
                new String[]{DBManager.ID, DBManager.HEADER, DBManager.TIME, DBManager.TEXT},
                new int[]{R.id.idItemList, R.id.headerItemList, R.id.timeItemList, R.id.textItemList}, 0
        );
        listView.setAdapter(cursorAdapter);



        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e("FF", " " + result);
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Note note = (Note) data.getSerializableExtra(Keys.NOTE_KEY.name());
                        System.out.println("RESULT_OK");
                        int index = data.getIntExtra("index", -1);
                        if(note != null && index > -1){
                            dbManager.update(note);
                            cursor = dbManager.findAllToCursor();
                            cursorAdapter.changeCursor(cursor);
                            // notes.set(index, note);
                            System.out.println("note save " + note.getText());
                        }
                    }
                    else if(result.getResultCode() == RESULT_CANCELED){

                    }
                }
        );


        createNoteResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e("FF", " " + result);
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Note note = (Note) data.getSerializableExtra(Keys.NOTE_KEY.name());
                        System.out.println("RESULT_OK");
                        if(note != null){
                            dbManager.save(note);
                            cursor = dbManager.findAllToCursor();
                            cursorAdapter.changeCursor(cursor); // update listView
                            System.out.println("note was added " + note.getText());
                        }
                    }
                    else if(result.getResultCode() == RESULT_CANCELED){
                        System.out.println("note was not added");
                    }
                }
        );


        listView.setOnItemLongClickListener((adapterView, view, i, id) -> {
            System.out.println("id = " + id);
            dbManager.deleteByID((int)id);
            cursor = dbManager.findAllToCursor();
            cursorAdapter.changeCursor(cursor);
            return true;
        });



        listView.setOnItemClickListener((adapterView, view, i, id) -> {
            Intent intent = new Intent(ListActivity.this, NoteActivity.class);
//            intent.putExtra("note_ID", (int)id);
            Note note = dbManager.findByID((int)id);
            intent.putExtra(Keys.NOTE_KEY.name(), note);
            intent.putExtra("index", i);
//            startActivity(intent);
//            finish();
            resultLauncher.launch(intent);
        });


        Button deleteAllButton = findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(view -> {
            dbManager.deleteAllItems();
            cursorAdapter.notifyDataSetChanged();
            cursor = dbManager.findAllToCursor();
            cursorAdapter.changeCursor(cursor);
        });


        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, AddNoteActivity.class);
            createNoteResultLauncher.launch(intent);
        });


    }

}