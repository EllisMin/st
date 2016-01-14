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
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SearchResult extends AppCompatActivity  {
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;


    String[] courses;
    List<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView textView;



    // When hit back button
    public void back_searchResult(View view){
        // Goes back to Login page
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    // when Create button is tapped
    public void createBtn(View view){
        Intent i = new Intent(getApplicationContext(), Create.class);
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
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("courseName");
        String courseNumber = intent.getStringExtra("courseNumber");

        Log.i("APPINFO", ""+courseName);
        Log.i("APPINFO", ""+courseNumber);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);


        //Changing the button colors
        searchBtn.setTextColor(0xFFFFFFFF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);

        listView= (ListView)findViewById(R.id.listView);
        textView= (TextView)findViewById(R.id.textView2);
        textView.setText(courseName + " " + courseNumber);

        listItems = new ArrayList<>();


        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
        roomQuery.whereEqualTo("course" , courseName);
        roomQuery.whereEqualTo("number", courseNumber);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    for (ParseObject room : objects) {

                        Log.i("Appinfo", String.valueOf(room.get("title")));
                        String stringToAdd = "";
                        stringToAdd = stringToAdd + String.valueOf(room.get("studyDate")) + "   " +
                                String.valueOf(room.get("category")) + "    " + String.valueOf(room.get("opened") + "\n")
                                + String.valueOf(room.get("title")) +
                                "            "
                        ;
                        listItems.add(stringToAdd);
                        Log.i("Appinfo", "A");
                    }
                } else {
                    Log.i("Appinfo", "B");
                    e.printStackTrace();
                }

            }
        });
        initList();
//        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }
    public void initList(){

        Log.i("Appinfo", "C");



        adapter = new ArrayAdapter<String>(this, R.layout.list_list, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Room.class);
                String category = listItems.get(position);


            }
        });


    }



}