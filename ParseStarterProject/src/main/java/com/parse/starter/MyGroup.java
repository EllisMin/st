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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyGroup extends AppCompatActivity {


    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    List<List> listForAdapter;
    List<String> dates;
    List<String> categories;
    List<String> titles;

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


        //Chaning the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFFFFFFF);
        settingBtn.setTextColor(0xFFBFBFBF);


        populateListView(); // method to create the list
        registerClickCallback(); // method to implement click behavior of the list
    }

    private void populateListView() {
        listForAdapter = new ArrayList<>();
        titles = new ArrayList<>();
        dates = new ArrayList<>();
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
                        dates.add(String.valueOf(obj.get("studyDate")));
                        Collections.sort(dates);
                        titles.add(String.valueOf(obj.get("title")));
                    }
                    listForAdapter.add(dates);
                    listForAdapter.add(titles);
                    Log.i("APPINFO", "" + listForAdapter.size());
                    // Build adapter
//                    ArrayAdapter adapter = new CustomAdapter();
                    ArrayAdapter adapter = new ArrayAdapter<String>(MyGroup.this, R.layout.list_createdroom, R.id.date, dates);
                    ArrayAdapter testAdapter = new ArrayAdapter<String>(MyGroup.this, R.layout.list_createdroom, R.id.roomTitle, titles);
                    // Configure list view
                    ListView list = (ListView) findViewById(R.id.myList);
                    list.setAdapter(adapter);
                    ListView tList = (ListView) findViewById(R.id.otherList);
                    tList.setAdapter(testAdapter);
                }
            }
        });
    }

    // When tapping each item
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.myList);
        // if onlick, is when tapping the list, not item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                // behavior when tapped
                String message = "you clicked #" + position + ", which is string: " + parent.getItemAtPosition(position);
                Toast.makeText(MyGroup.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Custom Adapter class
    private class CustomAdapter extends ArrayAdapter {
        public CustomAdapter() {
            super(MyGroup.this, R.layout.list_createdroom, listForAdapter);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("APPINFO", "333");
            // date
            // title
            // category
            // capacity
            // delete button
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View customView = inflater.inflate(R.layout.list_createdroom, parent, false);
            String dateItem = (String) getItem(position);
            TextView dateText = (TextView) customView.findViewById(R.id.date);
            dateText.setText(dateItem);

//            ImageView capImage = (ImageView) customView.findViewById(R.id.capacityImage);
//            capImage.setImageResource(R.drawable.open);

            return customView;

        }
    }

}


