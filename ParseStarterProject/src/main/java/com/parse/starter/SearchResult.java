package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by se7en_000 on 2016-01-16.
 */
public class SearchResult extends Activity {
    private Button createBtn;
    private Button searchBtn;
    private Button myGroupBtn;
    private Button settingBtn;
    private ListView roomView;
    private CustomAdapter adapter;
    private List<RoomForAdapter> roomList;
    private TextView textView;
    private List<String> objectIDs;
    // When hit back button
    public void back_searchResult(View view){
        // Goes back to Login page
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    // When myGroup button is tapped
    public void myGroupBtn(View view) {
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
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

        // Changing the button colors
        searchBtn.setTextColor(0xFFFFFFFF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);

        //Intent from Search.Class
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("courseName");
        String courseNumber = intent.getStringExtra("courseNumber");

        //Initialize arraylist for objectID
        objectIDs = new ArrayList<>();

        roomView = (ListView)findViewById(R.id.listView);
        roomList = new ArrayList<>();
        textView= (TextView)findViewById(R.id.textView2);
        textView.setText(courseName + " " + courseNumber);
        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
        roomQuery.whereEqualTo("course" , courseName);
        roomQuery.whereEqualTo("number", courseNumber);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (ParseObject room : objects){
                        //availability's visual modification
                        String opened = String.valueOf(room.get("opened"));
                        String x;
                        if(opened.equals(true)){

                            x = "Open";

                        }else{
                            x = "Closed";
                        }
                        //Retrieve objectID from parse
                        String objectId = room.getObjectId();
                        //Add rooms to the arraylist from Parse
                        roomList.add(new RoomForAdapter(String.valueOf(room.get("studyDate")),String.valueOf(room.get("studyTime")),String.valueOf(room.get("title")),String.valueOf(room.get("category")),x));
                        //Add objectID to the arraylist from Parse
                        objectIDs.add(objectId);


                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
        //Initialize adapter
        adapter = new CustomAdapter(getApplicationContext(), roomList);
        roomView.setAdapter(adapter);
        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Room.class);
                String passingID = objectIDs.get(position);
                intent.putExtra("objectID", passingID);
                startActivity(intent);
            }

        });
    }



}