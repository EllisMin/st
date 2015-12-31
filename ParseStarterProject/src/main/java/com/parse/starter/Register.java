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
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Bitmap bitmapImage;
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

        ParseUser user = new ParseUser();
        // Creating new user based on fields
        user.setUsername(String.valueOf(usernameField.getText()));
        user.setEmail(String.valueOf(emailField.getText()));
        user.setPassword(String.valueOf(passwordField.getText()));

        // Storing image in byteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ParseFile file = new ParseFile("profile.png", byteArray);

        user.put("photo", file);



        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Log.i("APPINFO", "Image uploaded successfully");

                } else {
                    e.printStackTrace();
                    Log.i("APPINFO", "Image upload fail");
                }
            }
        });

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("AppInfo", "Signup Successful");
                    // Code to show search page
                    Intent i = new Intent(getApplicationContext(), Search.class);
                    startActivity(i);
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
        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);

        profilePhoto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profilePhoto){

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            try {

                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                // Displaying image
                ImageView profile = (ImageView) findViewById(R.id.profilePhoto);
                profile.setImageBitmap(bitmapImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
