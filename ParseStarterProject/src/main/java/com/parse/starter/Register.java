package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends AppCompatActivity {

    EditText usernameField;
    EditText emailField;
    EditText passwordField;

    // When hit back button
    public void back_reg(View view){
        // Goes back to Login page
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    // When hit signup button
    public void signupBtn(View view){

        // Creating new user based on fields
        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(usernameField.getText()));
        user.setEmail(String.valueOf(emailField.getText()));
        user.setPassword(String.valueOf(passwordField.getText()));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("AppInfo", "Signup Successful");
                    // Code to show search page

                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameField = (EditText) findViewById(R.id.Username);
        emailField = (EditText) findViewById(R.id.Email);
        passwordField = (EditText) findViewById(R.id.password);

    }

}
