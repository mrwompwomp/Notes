package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class editNote extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    @NotEmpty
    private EditText noteTitle, noteContent;
    private Button saveButton;
    private TextView dateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.noteContent);
        saveButton = findViewById(R.id.saveButton);
        dateTV = findViewById(R.id.dateTV);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            String date = bundle.getString("dueDate");
            noteTitle.setText(title);
            noteContent.setText(content);
            dateTV.setText(date);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
                /*
                save(view, getIntent().getExtras());
                Intent intent = new Intent(editNote.this, MainActivity.class);
                startActivity(intent);
                */

            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dueDateString = DateFormat.getDateInstance().format(c.getTime());
        DatabaseHandler db = new DatabaseHandler(editNote.this);
        String id =  getIntent().getExtras().getString("id");
        long i= db.updateDate(id, formatter2.format(c.getTime()));
        Log.d("OnDateSet()", Long.toString(i));
        dateTV.setText(dueDateString);
    }

    private void save(Bundle bundle){
        DatabaseHandler db = new DatabaseHandler(editNote.this);
        String id =  bundle.getString("id");
        db.update(id, noteTitle.getText().toString(), noteContent.getText().toString());
        Toast.makeText(getApplicationContext(),"Note Updated!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidationSucceeded() {
        save(getIntent().getExtras());
        Intent intent = new Intent(editNote.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Validation Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
