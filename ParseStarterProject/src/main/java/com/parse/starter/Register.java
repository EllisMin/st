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

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Bitmap bitmapImage;
    EditText usernameField;
    EditText emailField;
    EditText passwordField;
    ImageView profilePhoto;
    ParseFile file = null;
    Boolean imageSelected = false;

    // When hit back button
    public void back_reg(View view){
        // Goes back to Login page
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    // When hit signup button
    public void signupBtn(View view){

        if(imageSelected) {
            // Storing image in byteArray
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            file = new ParseFile(String.valueOf(usernameField.getText()) + ".png", byteArray);
        } else { // When the user does not select an image
            profilePhoto.buildDrawingCache();
            Bitmap bm = profilePhoto.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            file = new ParseFile(String.valueOf(usernameField.getText()) + "_default.png", byteArray);
        }

        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(usernameField.getText()));
        user.setEmail(String.valueOf(emailField.getText()));
        user.setPassword(String.valueOf(passwordField.getText()));

        user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if (e == null) {
                                            Log.i("AppInfo", "Signup Successful");
                                            //Adding the image in
                                            Log.i("APPINFO", "" + ParseUser.getCurrentUser().getUsername() + " now has file" + file.getName());
                                            ParseUser.getCurrentUser().put("photo", file);
                                            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast.makeText(getApplication().getBaseContext(), "successfully", Toast.LENGTH_LONG).show();

                                                    } else {
                                                        Toast.makeText(getApplication().getBaseContext(), "error", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                            // Code to show search page
                                            Intent i = new Intent(getApplicationContext(), Search.class);
                                            startActivity(i);

                                        } else {
                                            Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

        );
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
                // Displaying image
                ImageView profile = (ImageView) findViewById(R.id.profilePhoto);
                profile.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmapImage, 440));
                imageSelected = true;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
