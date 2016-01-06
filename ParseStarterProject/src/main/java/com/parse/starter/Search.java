package com.parse.starter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Search extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;
    Button grpSearchBtn;

    List<String> courseCopiedItems;
    List<String> numCopiedItems;

    // Course search
    ArrayList<String> courseItems;
    ArrayAdapter<String> courseAdapter;
    ListView courseListView;
    EditText courseEditText;

    // Course Number Search
    ArrayList<String> listNums;
    ArrayAdapter<String> numberAdapter;
    ListView numListView;
    EditText numEditText;

    // When group Search button is tapped
    public void grpSearchBtn(View view){
        Intent i = new Intent(getApplicationContext(), SearchResult.class);
        i.putExtra("courseName", courseEditText.getText().toString());
        i.putExtra("courseNumber", numEditText.getText().toString());
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }


    // when Create button is tapped
    public void createBtn(View view){
        Intent i = new Intent(getApplicationContext(), Create.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }
    // When myGroup is tapped
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
        setContentView(R.layout.activity_search);
        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);

        // Making Links to group search button
        grpSearchBtn = (Button) findViewById(R.id.grpSearchbtn);




        //Chaning the button colors
        searchBtn.setTextColor(0xFFFFFFFF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);

        // Get Course List from parse
        courseListView= (ListView)findViewById(R.id.courseListView);
        courseEditText = (EditText)findViewById(R.id.courseEditText);
        courseItems = new ArrayList<>();

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
        courseListView.setVisibility(View.INVISIBLE);


        // Initialize Number parts
        numListView = (ListView) findViewById(R.id.numListView);
        numEditText = (EditText) findViewById(R.id.numEditText);
        listNums = new ArrayList<>();
        numListEnable(false);

        // Course List Text Change
        courseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    // List invisible
                    courseListView.setVisibility(View.GONE);
                    numEditText.setText("");
                    numEditText.setHint("Select Subject First");
                    numListEnable(false);


                }else{
                    // perform search
                    // List visible
                    courseListView.setVisibility(View.VISIBLE);
                    numEditText.setText("");
                    numEditText.setHint("Select Subject First");
                    numListEnable(false);
                    courseListView.bringToFront();
                    searchCourseItem(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numListView.setVisibility(View.INVISIBLE);




    }

    // Search Course List
    public void searchCourseItem(String textToSearch){
        courseCopiedItems = new ArrayList<String>();
        for(String item: courseItems){
            courseCopiedItems.add(item);
        }



        for (Iterator<String> iterator = courseCopiedItems.iterator(); iterator.hasNext(); ) {
            String value = iterator.next().toLowerCase();
            if(!value.contains(textToSearch.toLowerCase())){
                iterator.remove();
            }
        }
        Collections.sort(courseCopiedItems);
        if(courseCopiedItems.size() == 0){
            courseCopiedItems.add("No Result");
        }

        courseAdapter = new ArrayAdapter<String>(this, R.layout.list_copieditems, R.id.txtcopiedItems, courseCopiedItems);
        courseListView.setAdapter(courseAdapter);
        courseListView.setOnItemClickListener(this);



    }

    // Search Number List
    public void searchNumItem(String textToSearch){
        // Copy number list to temporary list
        numCopiedItems = new ArrayList<String>();
        for(String item: listNums){
            numCopiedItems.add(item);
        }

        for (Iterator<String> iterator = numCopiedItems.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            if(!value.contains(textToSearch)){
                iterator.remove();
            }
        }
        Collections.sort(numCopiedItems);
        numberAdapter = new ArrayAdapter<String>(this, R.layout.list_list, R.id.txtitem, numCopiedItems);
        numListView.setAdapter(numberAdapter);
        numListView.setOnItemClickListener(this);


    }

    // For courses
    @Override
    public void onItemClick(AdapterView<?> adapterView,View v, int i, long l) {

        if(adapterView.getId() == R.id.courseListView){
            String selected = courseCopiedItems.get(i);
            if(selected.equals("No Result")){
                courseListView.setVisibility(View.GONE);
                courseEditText.setText("");
                return;
            }
            courseEditText.setText(selected);
            courseListView.setVisibility(View.GONE);

            numEditText.setHint("Course Number");
            numListEnable(true);
                // Get Course number List from parse AFTER Course is selected


                ParseQuery<ParseObject> numQuery = ParseQuery.getQuery("Course");

                numQuery.whereEqualTo("courseName", selected);
                numQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {

                            ParseObject course = objects.get(0);
                            listNums = (ArrayList<String>) course.get("courseNum");

                        } else {
                            e.printStackTrace();
                        }

                    }
                });

                // Number List Text Change
                numEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")) {
                            // reset listview
                            // List invisible
                            numListView.setVisibility(View.GONE);

                        } else {
                            // perform search
                            // List visible
                            numListView.setVisibility(View.VISIBLE);
                            numListView.bringToFront();
                            searchNumItem(s.toString());

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });








        }else if (adapterView.getId() == R.id.numListView){
            String selected = numCopiedItems.get(i);
            numEditText.setText(selected);
            numListView.setVisibility(View.GONE);
        }



    }

    public void numListEnable(boolean sw){

        numEditText.setEnabled(sw);
        numEditText.setClickable(sw);
        numEditText.setFocusableInTouchMode(sw);
        numEditText.setFocusable(sw);
    }


}