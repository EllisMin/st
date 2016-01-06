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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MyGroup extends AppCompatActivity {


    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    // When search button is tapped
    public void searchBtn(View view){
        Intent i = new Intent(getApplicationContext(), Search.class);
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
        setContentView(R.layout.activity_my_group);


        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);



        //Chaning the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFFFFFFF);
        settingBtn.setTextColor(0xFFBFBFBF);


        // Use ACL to list the rooms created by oneself
        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
//        roomQuery.whereEqualTo("course", courseName);
        //  roomQuery.whereEqualTo("number" , courseNumber);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    Log.i("APPINFO", "Retrieved" + objects.size() + "results");
                }
            }
        });

        populateListView(); // method to create the list
        registerClickCallback(); // method to implement click behavior of the list
    }

    private void populateListView() {
        // Create list of items
        String[] date = {"1", "2", "3"};
        String[] name;
        String[] category;
        String[] open;


        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for the activity
                R.layout.list_createdroom,  // Layout to use (create)
                date);                      // Items to be displayed

        // Configure list view
        ListView list = (ListView) findViewById(R.id.myList);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.myList);

        // if onlick, is when tapping the list, not item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                // behavior when tapped
                String message = "you clicked #" + position +", which is string: " + textView.getText().toString();
                Toast.makeText(MyGroup.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


}

