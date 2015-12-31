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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends Activity implements View.OnClickListener {
  EditText usernameField;
  EditText passwordField;

  TextView loginBtn;

  // Method to show Search view
  public void showSearch(){
    Intent i = new Intent(getApplicationContext(), Search.class);
    startActivity(i);
  }
  // When anyting that has OnClickListener is clicked (in this case, login button)
  public void onClick(View v) {
    if(v.getId() == R.id.loginBtn){
      ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {

          if (user != null) {
            Log.i("AppInfo", "Login Successful");
            showSearch();
          }
          else
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
        }
      });
    }
  }

  // When click Register button
  public void registerBtn(View view){

    // Shows up the Register activity
    Intent i = new Intent(getApplicationContext(), Register.class);
    startActivity(i);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // If the user is logged in, go to Search (main) page
    if (ParseUser.getCurrentUser() != null){
      showSearch();
    }

    // Textfield setting
    usernameField = (EditText) findViewById(R.id.username);
    passwordField = (EditText) findViewById(R.id.password);
    loginBtn = (TextView) findViewById(R.id.loginBtn);

    // Setting textView clickable
    loginBtn.setOnClickListener(this);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}
