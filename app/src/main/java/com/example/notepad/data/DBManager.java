package com.example.notepad.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME="notes.db";
    public static final String TAB="notes";
    public static final String ID="_id";
    public static final String HEADER="header";
    public static final String TIME="time";
    public static final String TEXT="text";

    private SQLiteDatabase db;

    public DBManager(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        createTab();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTab(){
        db=getWritableDatabase();
        String sql = "create table if not exists %s(%s integer primary key autoincrement, " +
                "%s text, %s text, %s text)";
        sql=String.format(sql, TAB, ID, HEADER, TIME, TEXT);
        db.execSQL(sql);
    }

    public void save(Note note){
        db=getWritableDatabase();
        String sql = "insert into %s(%s, %s, %s) values ('%s', '%s', '%s')";
        sql=String.format(sql, TAB, HEADER, TIME, TEXT,
                note.getHeader(), note.getTimeInString(), note.getText());
        db.execSQL(sql);
    }

    public Cursor findAllToCursor(){
        db = getReadableDatabase();
        String sql = "select * from " + TAB;
        return db.rawQuery(sql, new String[]{});
    }

    @SuppressLint("Range")
    public List<Note> findAllToList(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = findAllToCursor();
        Note note;
        while (cursor.moveToNext()){
            note=new Note(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(HEADER)),
                    cursor.getString(cursor.getColumnIndex(TIME)),
                    cursor.getString(cursor.getColumnIndex(TEXT))
            );
            notes.add(note);
        }
        return notes;
    }


    @SuppressLint("Range")
    public Note findByID(int id){
        db=getReadableDatabase();
        String sql = "select * from %s where %s=%s";
        sql=String.format(sql, TAB, ID, id);
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if(cursor.moveToFirst())
            return new Note(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(HEADER)),
                    cursor.getString(cursor.getColumnIndex(TIME)),
                    cursor.getString(cursor.getColumnIndex(TEXT))
            );
        return null;
    }

    @SuppressLint("Range")
    public void deleteByID(int id){
        db=getReadableDatabase();
//        String sql = "delete  from %s where %s=%s";
//        sql = String.format(sql, TAB, ID, id);
        String sql = "delete  from " + TAB + " where " + ID + "=" + id;  // "delete from notes where _id=1"
        db.execSQL(sql);
    }

    @SuppressLint("Range")
    public void deleteAllItems(){
        db=getReadableDatabase();
        String sql = "delete  from " + TAB ;  // "delete from notes"
        db.execSQL(sql);
    }

    public void update(Note note) {
        db = getWritableDatabase();
        String sql = "update %s set %s = '%s', %s = '%s', %s = '%s' where %s = %s";
        // update notes set header = 'hello', time = '05:08....', text = 'qwerty' where _id = 2

        sql = String.format(sql, TAB,
                HEADER, note.getHeader(),
                TIME, note.getTimeInString(),
                TEXT, note.getText(),
                ID, note.getId());
        db.execSQL(sql);
    }

}
