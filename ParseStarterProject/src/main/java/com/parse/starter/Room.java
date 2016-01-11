package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Room extends AppCompatActivity implements OnItemSelectedListener {
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    EditText roomTitle;
    TextView courseName;
    TextView courseNumber;
    EditText capacity;
    EditText description;
    Button deleteBtn;
    Button editBtn;
    Button joinBtn;
    Spinner category;
    ImageView masterPhoto;
    EditText commentBox;
    Button commentBtn;
    ListView commentList;

    List commentItem;

    Boolean timepassed;
    Boolean opened;
    String userId;
    Boolean roomEditable;
    String objectIdRoom;

    String selectedCourse;
    String selectedNumber;
    String selectedCategory;

    static TextView dateTextView;
    static TextView timeTextView;

    Object[] commentArray;

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
        setContentView(R.layout.activity_room);

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
        courseName = (TextView) findViewById(R.id.courseName);
        courseNumber = (TextView) findViewById(R.id.courseNumber);
        capacity = (EditText) findViewById(R.id.capacity);
        masterPhoto = (ImageView) findViewById(R.id.masterPhoto);
        description = (EditText) findViewById(R.id.description);
        //TODO
        // When it is not meant to be edited, change to textview
        category = (Spinner) findViewById(R.id.category);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBox = (EditText) findViewById(R.id.commentBox);
        commentList = (ListView) findViewById(R.id.commentList);

        category.setOnItemSelectedListener(this);



        //TODO
        // Get course name and number from search_result
        Intent intent = getIntent();
        // Get objectId
        objectIdRoom = "2nqFDMwPyq";


        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
        roomQuery.whereEqualTo("objectId", objectIdRoom);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("AppInfo", "roomQuery success");
                    dateTextView.setText(String.valueOf(objects.get(0).get("studyDate")));
                    timeTextView.setText(String.valueOf(objects.get(0).get("studyTime")));
                    roomTitle.setText(String.valueOf(objects.get(0).get("title")));
                    capacity.setText(String.valueOf(objects.get(0).get("capacity")));

                    String categoryTemp = String.valueOf(objects.get(0).get("category"));
                    int cateNum;
                    if (categoryTemp.equals("Exam")) {
                        cateNum = 0;
                    } else if (categoryTemp.equals("Quiz")) {
                        cateNum = 1;
                    } else if (categoryTemp.equals("Homework")) {
                        cateNum = 2;
                    } else if (categoryTemp.equals("Project")) {
                        cateNum = 3;
                    } else {
                        cateNum = 4;
                    }
                    category.setSelection(cateNum);
                    courseName.setText(String.valueOf(objects.get(0).get("course")));
                    courseNumber.setText(String.valueOf(objects.get(0).get("number")));
                    description.setText(String.valueOf(objects.get(0).get("description")));
                    if (String.valueOf(objects.get(0).get("timepassed")).equals("True")) {
                        timepassed = true;
                    } else {
                        timepassed = false;
                    }
                    if (String.valueOf(objects.get(0).get("opened")).equals("True")) {
                        opened = true;
                    } else {
                        opened = false;
                    }
                    userId = (String.valueOf(objects.get(0).get("ACL")));

                } else {
                    Log.i("AppInfo", "ParseException");
                }
            }
        });


        // If login user is not create of room, no show of edit and delete buttons.
        if(String.valueOf(ParseUser.getCurrentUser().getObjectId()).equals(userId)){
            roomEditable = true;

        }else{
            roomEditable = false;
            // Make buttons invisible when it is not edit mode
            editBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);

        }



        // Disable all the views and make buttons invisible when it is not edit mode
        editMode(false);






    }

    private void editMode(boolean b) {

        viewEnable(b, dateTextView);
        viewEnable(b, timeTextView);
        viewEnable(b, roomTitle);
        viewEnable(b, capacity);
        viewEnable(b, description);
        viewEnable(b, masterPhoto);
    }

    /*
        For category
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category.setSelection(position);
        String selected = (String) category.getSelectedItem();
        selectedCategory = selected;
        Log.i("Appinfo", selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("AppInfo", "Nothing is selected");
    }

    /*
        edit button is tapped
     */
    public void editBtn(View view){
        editMode(true);
    }

    /*
        delete button is tapped
     */
    public void deleteBtn(View view){

        // Delete the room object
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.whereEqualTo("objectId", objectIdRoom);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i("Appinfo", "Delete the Room");
                    objects.get(0).deleteInBackground();
                }else{
                    Log.i("Appinfo","Delete fail");
                }
            }
        });
        //Redirect to mygroup activity
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }


    /*
        If comment button is tapped
     */
    public void commentBtn(View view) {
        if(commentBox.getText().equals("")){
            // Do Nothing
        }else{
            // add it to the commentItems for commentList and update the change.
            String comment = String.valueOf(commentBox.getText());
            String commenterId = String.valueOf(ParseUser.getCurrentUser().getObjectId());

            // Get date of today
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date rightnow = new Date();

            commentArray = new Object[3];

            commentArray[0] = comment;
            commentArray[1] = commenterId;
            commentArray[2] = rightnow;

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
            query.whereEqualTo("objectId", objectIdRoom);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        Log.i("Appinfo", "put comment successfully");
                        objects.get(0).addAll("comment", Arrays.asList(commentArray));
                        objects.get(0).saveInBackground();
                    }else{
                        Log.i("Appinfo","putting comment fails");

                    }
                }
            });



        }
    }
    /*
        To disable view
     */
    public void viewEnable(boolean sw, View v){

        v.setEnabled(sw);
        v.setClickable(sw);
        v.setFocusableInTouchMode(sw);
        v.setFocusable(sw);
    }



}