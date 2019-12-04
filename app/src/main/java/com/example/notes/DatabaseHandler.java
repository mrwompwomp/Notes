package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, "notes_db2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE notes(id INTEGER PRIMARY KEY, title TEXT, content TEXT,createdDate DATETIME DEFAULT CURRENT_TIMESTAMP, dueDate DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf("DROP TABLE IF EXISTS");
        db.execSQL(DROP_TABLE, new String[]{"notes_db"});
        onCreate(db);
    }
    public void addNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put("createdDate", formatter2.format(c.getTime()));
        values.put("dueDate", formatter2.format(c.getTime()));
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        //values.put("category", "Grocery");

        db.insert("notes", null, values);
        Log.d("Test", "addNote: " + "item added");
        db.close();
    }

    public Note getNote(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("notes", new String[]{"id", "title", "content"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            Note note = new Note();
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            note.setCreatedDate(formatter2.parse(cursor.getString(3)));
            note.setDueDate(formatter2.parse(cursor.getString(4)));
            db.close();
            return note;
        } else {
            db.close();
            return null;
        }
    }

    public List<Note> getAllNotes() throws ParseException {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM notes";
        Cursor cursor = db.rawQuery(selectAll, null);
        if (cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.d("tracking handler: ",cursor.getString(4));
                note.setDueDate(formatter2.parse(cursor.getString(4)));
                note.setCreatedDate(formatter2.parse(cursor.getString(3)));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public long delete(String ID){
        return this.getReadableDatabase().delete("notes","ID=?",new String[]{ID});
    }

    public long update(String id, String title, String content) {
        ContentValues contentValues= new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        return this.getReadableDatabase().update("notes", contentValues,"ID=?",new String[] {id});
    }

    public long updateDate(String id, String date) {
        ContentValues contentValues= new ContentValues();
        Log.d("updateDate()", date);
        contentValues.put("dueDate", date);
        return this.getReadableDatabase().update("notes", contentValues,"ID=?",new String[] {id});
    }

}
