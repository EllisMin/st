package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class Setting extends AppCompatActivity {

    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    // when CREAT button is tapped
    public void searchBtn(View view){
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when CREAT button is tapped
    public void createBtn(View view){
        Intent i = new Intent(getApplicationContext(), Create.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }




    // Temporary logout button
    public void tempLogOut(View view){
        Log.i("AppINFO", "btn clicked!");
        // Logout
        ParseUser.logOut();
        // Show login Page
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //Chaning the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFFFFFFF);

    }

}