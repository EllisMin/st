package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.IOException;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;

    EditText usernameField;
    EditText emailField;
    EditText passwordField;
    ImageView profilePhoto;

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
        user.put("photo", profilePhoto);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("AppInfo", "Signup Successful");
                    // Code to show search page

                } else {
                    e.printStackTrace();

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
        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);

        profilePhoto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.profilePhoto:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
//            try {
//                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                Log.i("APPINFO", "AA");
//                // Displaying image
//                ImageView profile = (ImageView) findViewById(R.id.profilePhoto);
//                profile.setImageBitmap(bitmapImage);
//
//                Log.i("APPINFO", "BB");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            profilePhoto.setImageURI(selectedImage);
        }


    }
}
