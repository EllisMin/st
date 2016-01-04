package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Create extends FragmentActivity {

    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    EditText roomTitle;
    EditText courseName;
    EditText courseNumber;
    EditText capacity;
    EditText description;
    Button doneBtn;

    static TextView dateTextView;
    static TextView timeTextView;

    // When hit done button
    public void doneBtn(View view) {
        Log.i("APPINFO", String.valueOf(roomTitle.getText()));
        Log.i("APPINFO", String.valueOf(dateTextView.getText()));
        Log.i("APPINFO", String.valueOf(timeTextView.getText()));
        if (roomTitle.getText().toString().matches("") || courseName.getText().toString().matches("") ||
                courseNumber.getText().toString().matches("") || capacity.getText().toString().matches("")
                || description.getText().toString().matches("") || dateTextView.getText().toString().matches("Set Date")
        || timeTextView.getText().toString().matches("Set Time")){
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // Creating room
            ParseObject obj = new ParseObject("Room");
            obj.put("title", String.valueOf(roomTitle.getText()));
            obj.put("course", String.valueOf(courseName.getText()));
            obj.put("number", Integer.parseInt(String.valueOf(courseNumber.getText())));
            obj.put("capacity", Integer.parseInt(String.valueOf(capacity.getText())));
            obj.put("description", String.valueOf(description.getText()));
            obj.put("studyDate", String.valueOf(dateTextView.getText()));
            obj.put("studyTime", String.valueOf(timeTextView.getText()));
            obj.put("opened", true);
            obj.put("timePassed", true);
            obj.saveInBackground();

            Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MyGroup.class);
                    startActivity(i);

        }
    }

    // Method for date picker
    public void setDate(View view) {
        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getSupportFragmentManager(), "date_picker");

    }

    public void setTime(View view) {
        PickerDialogs_Time pickerDialogs = new PickerDialogs_Time();
        pickerDialogs.show(getSupportFragmentManager(), "time_picker");
    }

    // When search button is tapped
    public void searchBtn(View view){
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // When myGroup button is tapped
    public void myGroupBtn(View view){
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when Setting button is tapped
    public void settingBtn(View view){
        Intent i = new Intent(getApplicationContext(), Setting.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);


        //Changing the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFFFFFFF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);


        // Links to fields
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        roomTitle = (EditText) findViewById(R.id.roomTitle);
        courseName = (EditText) findViewById(R.id.courseName);
        courseNumber = (EditText) findViewById(R.id.courseNumber);
        capacity = (EditText) findViewById(R.id.capacity);
        description = (EditText) findViewById(R.id.description);
        doneBtn = (Button) findViewById(R.id.doneBtn);




    }
}
