package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private noteAdapter recyclerviewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Note> notesArrayList;
    private ConstraintLayout CL;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.test_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNote:
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                db.addNote(new Note(1, "Grocery", "Test Title8", "Test Content8", new java.util.Date(), new java.util.Date()));
                finish();
                startActivity(getIntent());
                //Parkour
                return true;
            case R.id.color1:
                CL.setBackgroundColor(Color.parseColor("#00FF8B"));
                return true;
            case R.id.color2:
                CL.setBackgroundColor(Color.parseColor("#B31931"));
                return true;
            case R.id.color3:
                CL.setBackgroundColor(Color.parseColor("#238846"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CL = findViewById(R.id.constrLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        notesArrayList = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        //db.addNote(new Note(1, "Grocery", "Test Title1", "Test Content1", new java.util.Date(), new java.util.Date()));
        //db.delete("2");
        //db.update("3", "updatingTest", "updatingTestcontent");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        List<Note> noteList = null;
        try {
            noteList = db.getAllNotes();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Note note : noteList) {
            Log.d("Test", "onCreate: " + note.getTitle());
            Log.d("Test", "onCreate: " + note.getContent());
            Log.d("Test", "onCreate: " + note.getId());
            notesArrayList.add(note);
        }
        recyclerviewAdapter = new noteAdapter(this, notesArrayList);
        recyclerView.setAdapter(recyclerviewAdapter);

    }
}
