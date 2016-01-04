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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Search extends AppCompatActivity {
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> copiedAdapter;
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


        //Chaning the button colors
        searchBtn.setTextColor(0xFFFFFFFF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);

        listView= (ListView)findViewById(R.id.listview);
        editText = (EditText)findViewById(R.id.txtSearch);
        listItems = new ArrayList<>();

        ParseQuery<ParseObject> courseQuery = ParseQuery.getQuery("Course");
        courseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject course : objects) {
                        listItems.add(String.valueOf(course.get("courseName")));
                    }
                    Log.i("Appinfo", "A");
                } else {
                    Log.i("Appinfo", "B");
                    e.printStackTrace();
                }

            }
        });
        // Alphabetical order ?? working correctly? TODO
        Collections.sort(listItems);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    // reset listview
                    // List invisible

                }else{
                    // perform search
                    // List visible
                    searchItem(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void searchItem(String textToSearch){
        List<String> copiedItems = new ArrayList<String>();
        for(String item: listItems){
            copiedItems.add(item);
        }



        for (Iterator<String> iterator = copiedItems.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            if(!value.contains(textToSearch)){
                iterator.remove();
            }
        }
        copiedAdapter = new ArrayAdapter<String>(this, R.layout.list_copieditems, R.id.txtcopiedItems, copiedItems);
        listView.setAdapter(copiedAdapter);

//        copiedAdapter.notifyDataSetChanged();

    }


    public void initList(){
        Log.i("Appinfo", "C");
        adapter = new ArrayAdapter<String>(this, R.layout.list_list, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
    }




}