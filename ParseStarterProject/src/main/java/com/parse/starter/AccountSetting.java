package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AccountSetting extends AppCompatActivity {

    // Buttons
    private Button createBtn;
    private Button searchBtn;
    private Button myGroupBtn;
    private Button settingBtn;

    // new information fields
    private EditText newPwdField;
    private EditText confirmPwdField;
    private EditText newEmailField;
    private EditText confirmEmailField;

    // warning fields
    private TextView pwdWarningField;
    private TextView emailWarningField;

    public void searchBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Search.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    public void myGroupBtn(View view) {
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    public void createBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Create.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    public void settingBtn(View view) {
        Intent i = new Intent(getApplicationContext(), Setting.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    public void update(View view) {

        // current username
        ParseUser user = ParseUser.getCurrentUser();

        boolean pwdUpdated = false;
        boolean emailUpdated = false;

        // String values of all variables
        String newPwd = String.valueOf(newPwdField.getText());
        String confirmPwd = String.valueOf(confirmPwdField.getText());
        String newEmail = String.valueOf(newEmailField.getText());
        String confirmEmail = String.valueOf(confirmEmailField.getText());

        // check new password
        if(!newPwd.equals("") && !confirmPwd.equals("")) {  // if password fields are filled
            // if confirmPwd doesn't match, display error message
            if(!newPwd.equals(confirmPwd)) {
                Toast.makeText(getApplicationContext(), "Password Doesn't Match", Toast.LENGTH_LONG).show();
                pwdWarningField.setVisibility(View.VISIBLE);
            }
            // if they match, set pwdUpdated to true
            else {
                Log.i("Setting", "PwdUpdated= True");
                pwdUpdated = true;
            }
        }


        // change new email
        if(!newEmail.equals("") && !confirmEmail.equals("")) {  // if email fields are filled
            // if emails don't match, display an error message
            if(!newEmail.equals(confirmEmail)) {
                Toast.makeText(getApplicationContext(), "Email Doesn't Match", Toast.LENGTH_LONG).show();
                emailWarningField.setVisibility(View.VISIBLE);
            }
            // if match, set emailUpdated to true
            else {
                Log.i("Setting", "EmailUpdated = True");
                emailUpdated = true;
            }
        }

        // update password and email
        if(pwdUpdated && emailUpdated) {
            user.setPassword(newPwd);
            user.setEmail(newEmail);
            user.saveInBackground();
            Toast.makeText(getApplicationContext(), "Password & Email Updated", Toast.LENGTH_LONG).show();
        }
        // update password only
        else if(pwdUpdated) {
            user.setPassword(newPwd);
            user.saveInBackground();
            Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_LONG).show();
        }
        // update email only
        else if(emailUpdated) {
            user.setEmail(newEmail);
            user.saveInBackground();
            Toast.makeText(getApplicationContext(), "Email Updated", Toast.LENGTH_LONG).show();
        }

/*      Testing in Log
        Log.i("New Pwd", String.valueOf(newPwdField.getText()));
        Log.i("Confirm Pwd ", String.valueOf(confirmPwdField.getText()));
        Log.i("New Email", String.valueOf(newEmailField.getText()));
        Log.i("Confirm Email", String.valueOf(confirmEmailField.getText()));
*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        // link buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);

        //Changing the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFBFBFBF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFFFFFFF);

        // new information
        newPwdField = (EditText) findViewById(R.id.newPwd);
        confirmPwdField = (EditText) findViewById(R.id.confirmPwd);
        newEmailField = (EditText) findViewById(R.id.newEmail);
        confirmEmailField = (EditText) findViewById(R.id.confirmEmail);

        // warning fields (default set to invisible)
        pwdWarningField = (TextView) findViewById(R.id.pwdWarning);
        pwdWarningField.setVisibility(View.INVISIBLE);
        emailWarningField = (TextView) findViewById(R.id.emailWarning);
        emailWarningField.setVisibility(View.INVISIBLE);

        confirmPwdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if user has deleted all fields, set the text invisible
                if (s.toString().equals("")) {
                    pwdWarningField.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        confirmEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    emailWarningField.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

}
