package com.parse.starter;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;
    ImageView profilePhoto;
    TextView userName;
    TextView version;
    CheckBox emailCheckBox;
    final String APPVERSION = "0.0.0.000001";

    // when Search button is tapped
    public void searchBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // When myGroup button is tapped
    public void myGroupBtn(View view) {
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when Create button is tapped
    public void createBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Create.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // Temporary logout button
    public void tempLogOut(View view) {
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

        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);
        profilePhoto = (ImageView) findViewById(R.id.profilePhoto_setting);
        userName = (TextView) findViewById(R.id.userNameLabel);
        version = (TextView) findViewById(R.id.version);
        emailCheckBox = (CheckBox) findViewById(R.id.emailCheckBox);


        //Changing the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFFFFFFF);
        try {
            loadTheImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Load user ID
        userName.setText(String.valueOf(ParseUser.getCurrentUser().getUsername()));
        // Load app version (final variable that is on the top)
        version.setText(APPVERSION);
    }

    // Loading the image from parse
    private void loadTheImage() throws IOException {
        // Getting the photo from current user's parse data
        ParseFile image = ParseUser.getCurrentUser().getParseFile("photo");
        image.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                // Get bitmap from the parse file
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                // Displaying the image
                ImageView profile = (ImageView) findViewById(R.id.profilePhoto_setting);
                profile.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmap, 220));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profilePhoto_setting) {
            // When the user taps the user photo
            Log.i("APPINFO", "HEEHE");

            // Giving alert
//            new AlertDialog.Builder(this)
//                    .setTitle("Changing photo")
//                    .setMessage("Would you want to change your photo?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue changing photo
//
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // do nothing
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();

//            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(i, 1);
        }

        // Sending an e-mail to debug
        if (v.getId() == R.id.debugLabel) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, ParseUser.getCurrentUser().getEmail());
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your app has a bug");
            intent.putExtra(Intent.EXTRA_TEXT, "YOLO");
            startActivity(Intent.createChooser(intent, "Send Email"));
        }

        // Changing account setting
        if (v.getId() == R.id.accountSetting){

            // move to AccountSetting page
            Intent i = new Intent(getApplicationContext(), AccountSetting.class);
            startActivity(i);
        }
    }
}