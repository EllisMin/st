package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Create extends AppCompatActivity {

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


    }
}
