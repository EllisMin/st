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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;


    String[] courses;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    // when CREAT button is tapped
    public void createBtn(View view){
        Intent i = new Intent(getApplicationContext(), Create.class);
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
        setContentView(R.layout.activity_search_result);
        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);


        //Chaning the button colors
        searchBtn.setTextColor(0xFFFFFFFF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);

        listView= (ListView)findViewById(R.id.courseListView);
        editText = (EditText)findViewById(R.id.courseEditText);
        listItems = new ArrayList<>();
        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject room: objects)
                        listItems.add(String.valueOf(room.get("date " + " roomName " + " category " + " availability")));
                    Log.i("Appinfo", "A");
                }else{
                    Log.i("Appinfo","B");
                    e.printStackTrace();
                }

            }
        });
        initList();
    }
    public void initList(){

        Log.i("Appinfo", "C");



        adapter = new ArrayAdapter<String>(this, R.layout.list_list, R.id.txtitem, listItems);
        listView.setAdapter(adapter);


    }
}
