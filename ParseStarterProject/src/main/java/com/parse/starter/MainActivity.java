/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends Activity {
  EditText usernameField;
  EditText passwordField;



  // When click Register button
  public void registerBtn(View view){

    Log.i("AA", String.valueOf(passwordField.getText()));
    
    // Shows up the Register activity
    Intent i = new Intent(getApplicationContext(), Register.class);
    startActivity(i);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Textfield setting
    usernameField = (EditText) findViewById(R.id.username);
    passwordField = (EditText) findViewById(R.id.password);

    // If the user is logged in, go to Search (main) page
//    if(ParseUser.getCurrentUser() != null){
//      Intent i = new Intent(getApplicationContext(), CLASSNAME);
//      startActivity(i);
//    }


    ParseObject score = new ParseObject("Score");
    score.put("username", "rob");
    score.put("score", 96);
    score.saveInBackground();


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}
