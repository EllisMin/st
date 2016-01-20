package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyGroup extends AppCompatActivity {


    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;
//  List<ParseObject> groups = new ArrayList<>(); // All rooms
    List<ParseObject> myGroups = new ArrayList<>();
    List<ParseObject> otherGroups = new ArrayList<>();
    List<String> objectIDs;

    // When search button is tapped
    public void searchBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when Create button is tapped
    public void createBtn(View view) {
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
        setContentView(R.layout.activity_my_group);


        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);


        // Changing the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFFFFFFF);
        settingBtn.setTextColor(0xFFBFBFBF);


        populateListView(); // method to create the list
        registerClickCallback(); // method to implement click behavior of the list
    }

    private void populateListView() {

        // Use ACL to list the rooms created by oneself
        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
//        roomQuery.whereEqualTo("course", courseName);
        //  roomQuery.whereEqualTo("number" , courseNumber);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("APPINFO", "Retrieved " + objects.size() + " results");
                    for (ParseObject obj : objects) {
                        if (obj.get("createdBy").equals(ParseUser.getCurrentUser())) {
                            myGroups.add(obj);
                        }

                        //@TODO
                        // groups that I'm joined in
//                        else{
//                            otherGroups.add(obj);
//                        }
                    }
                    // Build adapter
                    ArrayAdapter<Group> adapter_myGroup = new CustomAdapter(myGroups); // For first listView
                    ArrayAdapter<Group> adapter_otherGroup = new CustomAdapter(otherGroups);
                    // Configure list view
                    ListView list_myGroup = (ListView) findViewById(R.id.myList);
                    list_myGroup.setAdapter(adapter_myGroup);
                    ListView list_otherGroup = (ListView) findViewById(R.id.otherList);
                    list_otherGroup.setAdapter(adapter_otherGroup);
                }
            }
        });
    }

    // When tapping each item
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.myList);
        // if onClick, is when tapping the list, not item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                // behavior when tapped
//                String message = "you clicked #" + position + ", which is string: " + parent.getItemAtPosition(position);
//                Toast.makeText(MyGroup.this, message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Room.class);
//                String passingID = objectIDs.get(position); // room's obj id
                ParseObject obj = (ParseObject) parent.getItemAtPosition(position);
                Log.i("APPINFO", "Object id: "+obj.getObjectId());
                i.putExtra("objectID", obj.getObjectId());
                startActivity(i);
            }
        });
    }

    // Custom Adapter class
    private class CustomAdapter extends ArrayAdapter {
        List<ParseObject> list; // Either myGroups or otherGroups
        public CustomAdapter(List<ParseObject> list) {
            super(MyGroup.this, R.layout.list_createdroom, list);
            this.list = list;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("APPINFO", "view call");

            View itemView = convertView;
            // Make sure we have a view to work with (may have been null)
            if (itemView == null) {
                Log.i("APPINFO", "view is null");
                itemView = getLayoutInflater().inflate(R.layout.list_createdroom, parent, false);
            }
            // Find the group
            ParseObject currentGroup = list.get(position);

            // Fill the view
            TextView studyDate = (TextView) itemView.findViewById(R.id.date);
            studyDate.setText(String.valueOf(currentGroup.get("studyDate")));

            TextView studyTime = (TextView) itemView.findViewById(R.id.time);
            studyTime.setText(String.valueOf(currentGroup.get("studyTime")));

            TextView title = (TextView) itemView.findViewById(R.id.roomTitle);
            title.setText(String.valueOf(currentGroup.get(("title"))));

            TextView category = (TextView) itemView.findViewById(R.id.category);
            category.setText(String.valueOf(currentGroup.get("category")));

            TextView courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseName.setText(String.valueOf(currentGroup.get("course")));

            TextView courseNumber = (TextView) itemView.findViewById(R.id.courseNumber);
            courseNumber.setText(String.valueOf(currentGroup.get("number")));

            Button deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
//            deleteBtn.setOnClickListener();


            return itemView;
        }
    }

}


