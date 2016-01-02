package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.List;

public class Search extends AppCompatActivity {

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
        setContentView(R.layout.activity_search);

        Log.i("AppInfo","AA");
        // To add 999 size String for course number into Course query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Course");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("AppInfo", "BB");

                Log.i("AppInfo","CC");
                String[] courseNum = new String[999];
                for(int i = 1; i < 1000; i++){
                    if(i < 10) {
                        courseNum[i - 1] = "00" + i;
                    }
                    else if(i < 100){
                        courseNum[i-1]= "0" + i;
                    }
                    else{
                        courseNum[i-1] = String.valueOf(i);
                    }
                }
                Log.i("AppInfo", ""+courseNum[0].toString() + " " + objects.get(0).get("objectId"));


                for(ParseObject obj: objects){

                    Log.i("AppInfo", "array is inserted");
                    obj.put("courseNum",courseNum);

                }



            }
        });



    }

}
