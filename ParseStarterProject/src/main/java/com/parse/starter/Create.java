package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Create extends FragmentActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

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
    ListView courseList;
    ListView numberList;
    Spinner category;

    String selectedCourse;
    String selectedNumber;
    String selectedCategory;

    List<String> courseItems;
    List<String> numberItems;

    List<String> courseCopiedItems;
    List<String> numCopiedItems;
    ArrayAdapter<String> courseAdapter;
    ArrayAdapter<String> numberAdapter;

    List<String> members;

//    static List<Group> rooms = new ArrayList<>(); //
    static TextView dateTextView;
    static TextView timeTextView;

    // When hit done button
    //TODO specification
    public void doneBtn(View view) {
        Log.i("APPINFO", String.valueOf(roomTitle.getText()));
        Log.i("APPINFO", String.valueOf(dateTextView.getText()));
        Log.i("APPINFO", String.valueOf(timeTextView.getText()));
        if (selectedCategory.toString().matches("Category") || roomTitle.getText().toString().matches("") || courseName.getText().toString().matches("") ||
                courseNumber.getText().toString().matches("") || capacity.getText().toString().matches("")
                || description.getText().toString().matches("") || dateTextView.getText().toString().matches("Set Date")
                || timeTextView.getText().toString().matches("Set Time")) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        } else {

            // Creating room
            Group newGroup = new Group(String.valueOf(roomTitle.getText()), String.valueOf(dateTextView.getText()),
                    String.valueOf(timeTextView.getText()), selectedCourse, selectedNumber, selectedCategory,
                    Integer.parseInt(String.valueOf(capacity.getText())), String.valueOf(description.getText()),
                    true, true, members);
//            rooms.add(newGroup);

            // Toast message
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
    public void searchBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // When myGroup button is tapped
    public void myGroupBtn(View view) {
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when Setting button is tapped
    public void settingBtn(View view) {
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


        capacity = (EditText) findViewById(R.id.capacity);
        description = (EditText) findViewById(R.id.description);
        doneBtn = (Button) findViewById(R.id.doneBtn);
        category = (Spinner) findViewById(R.id.category);


        category.setOnItemSelectedListener(this);


        // For course search
        courseName = (EditText) findViewById(R.id.courseName);
        courseList = (ListView) findViewById(R.id.courseList);
        courseItems = new ArrayList<String>();

        ParseQuery<ParseObject> courseQuery = ParseQuery.getQuery("Course");
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject course : objects) {
                        courseItems.add(String.valueOf(course.get("courseName")));
                    }
                    Log.i("Appinfo", "A");
                } else {
                    Log.i("Appinfo", "B");
                    e.printStackTrace();
                }

            }
        });
        courseList.setVisibility(View.INVISIBLE);

        //For number search
        courseNumber = (EditText) findViewById(R.id.courseNumber);
        numberList = (ListView) findViewById(R.id.numberList);
        numberItems = new ArrayList<String>();

        //Disable number
        courseNumberEnable(false);

        // Course List Text Change
        courseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    // List invisible
                    courseList.setVisibility(View.GONE);
                    courseNumber.setText("");
                    courseNumber.setHint("Select Subject First");
                    courseNumberEnable(false);


                } else {
                    // perform search
                    // List visible
                    courseList.setVisibility(View.VISIBLE);
                    courseNumber.setText("");
                    courseNumber.setHint("Select Subject First");
                    courseNumberEnable(false);
                    courseList.bringToFront();
                    searchCourseItem(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numberList.setVisibility(View.INVISIBLE);

    }

    // Search Course List
    public void searchCourseItem(String textToSearch) {
        courseCopiedItems = new ArrayList<String>();
        for (String item : courseItems) {
            courseCopiedItems.add(item);
        }


        for (Iterator<String> iterator = courseCopiedItems.iterator(); iterator.hasNext(); ) {
            String value = iterator.next().toLowerCase();
            if (!value.contains(textToSearch.toLowerCase())) {
                iterator.remove();
            }
        }
        Collections.sort(courseCopiedItems);
        if (courseCopiedItems.size() == 0) {
            courseCopiedItems.add("No Result");
        }

        courseAdapter = new ArrayAdapter<String>(this, R.layout.list_copieditems, R.id.txtcopiedItems, courseCopiedItems);
        courseList.setAdapter(courseAdapter);
        courseList.setOnItemClickListener(this);


    }

    // Search Number List
    public void searchNumItem(String textToSearch) {
        // Copy number list to temporary list
        numCopiedItems = new ArrayList<String>();
        for (String item : numberItems) {
            numCopiedItems.add(item);
        }

        for (Iterator<String> iterator = numCopiedItems.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            if (!value.contains(textToSearch)) {
                iterator.remove();
            }
        }
        Collections.sort(numCopiedItems);
        numberAdapter = new ArrayAdapter<String>(this, R.layout.list_list, R.id.txtitem, numCopiedItems);
        numberList.setAdapter(numberAdapter);
        numberList.setOnItemClickListener(this);


    }

    /*
        For category
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        category.setSelection(position);
        String selected = (String) category.getSelectedItem();
        selectedCategory = selected;
        Log.i("Appinfo", selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Log.i("AppInfo", "Nothing is selected");
    }

    /*
        To disable number list until course is selected
     */
    public void courseNumberEnable(boolean sw) {

        courseNumber.setEnabled(sw);
        courseNumber.setClickable(sw);
        courseNumber.setFocusableInTouchMode(sw);
        courseNumber.setFocusable(sw);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.courseList) {
            String selected = courseCopiedItems.get(position);
            if (selected.equals("No Result")) {
                courseList.setVisibility(View.GONE);
                courseName.setText("");
                return;
            }
            courseName.setText(selected);
            selectedCourse = selected;
            courseList.setVisibility(View.GONE);

            courseNumber.setHint("Course Number");
            courseNumberEnable(true);
            // Get Course number List from parse AFTER Course is selected


            ParseQuery<ParseObject> numQuery = ParseQuery.getQuery("Course");

            numQuery.whereEqualTo("courseName", selected);
            numQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {

                        ParseObject course = objects.get(0);
                        numberItems = (ArrayList<String>) course.get("courseNum");

                    } else {
                        e.printStackTrace();
                    }

                }
            });

            // Number List Text Change
            courseNumber.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        // reset listview
                        // List invisible
                        numberList.setVisibility(View.GONE);

                    } else {
                        // perform search
                        // List visible
                        numberList.setVisibility(View.VISIBLE);
                        numberList.bringToFront();
                        searchNumItem(s.toString());

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        } else if (adapterView.getId() == R.id.numberList) {
            String selected = numCopiedItems.get(position);
            courseNumber.setText(selected);
            selectedNumber = selected;
            numberList.setVisibility(View.GONE);
        }


    }
}
