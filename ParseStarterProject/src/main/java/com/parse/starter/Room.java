package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Room extends AppCompatActivity implements OnItemSelectedListener {
    Button createBtn;
    Button searchBtn;
    Button myGroupBtn;
    Button settingBtn;

    EditText roomTitle;
    TextView courseName;
    TextView courseNumber;
    EditText capacity;
    EditText description;
    Button deleteBtn;
    Button editBtn;
    Button joinBtn;
    Button editDoneBtn;
    Spinner category;
    ImageView masterPhoto;
    EditText commentBox;
    Button commentBtn;
    ListView commentList;
    TextView categoryText;

    Comments commentObj;
    List<Comments> commentItem;
    ArrayAdapter<Comments> commentAdapter;
    ParseFile userPhoto;
    ImageView commentPhoto;
    TextView commentary;
    TextView commentUsername;
    TextView commentDate;
    String commentUser;

    Boolean timepassed;
    Boolean opened;
    String userId;
    Boolean roomEditable;
    String objectIdRoom;

    String selectedCourse;
    String selectedNumber;
    String selectedCategory;

    static TextView dateTextView;
    static TextView timeTextView;


    // Method for date picker
    public void setDate(View view) {
        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getSupportFragmentManager(), "date_picker");

    }

    public void setTime(View view) {
        PickerDialogs_Time pickerDialogs = new PickerDialogs_Time();
        pickerDialogs.show(getSupportFragmentManager(), "time_picker");
    }

    // When search button is tapped
    public void searchBtn(View view){
        Intent i = new Intent(getApplicationContext(), Search.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // When myGroup button is tapped
    public void myGroupBtn(View view){
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    // when Setting button is tapped
    public void settingBtn(View view){
        Intent i = new Intent(getApplicationContext(), Setting.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Making Links to Buttons on Create
        createBtn = (Button) findViewById(R.id.createBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        myGroupBtn = (Button) findViewById(R.id.myGroupBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);

        // Changing the button colors
        searchBtn.setTextColor(0xFFBFBFBF);
        createBtn.setTextColor(0xFFFFFFFF);
        myGroupBtn.setTextColor(0xFFBFBFBF);
        settingBtn.setTextColor(0xFFBFBFBF);


        // Links to fields
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        roomTitle = (EditText) findViewById(R.id.roomTitle);
        courseName = (TextView) findViewById(R.id.courseName);
        courseNumber = (TextView) findViewById(R.id.courseNumber);
        capacity = (EditText) findViewById(R.id.capacity);
        masterPhoto = (ImageView) findViewById(R.id.masterPhoto);
        description = (EditText) findViewById(R.id.description);
        category = (Spinner) findViewById(R.id.category);
        categoryText = (TextView) findViewById(R.id.categoryText);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        editDoneBtn = (Button) findViewById(R.id.editDoneBtn);
        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBox = (EditText) findViewById(R.id.commentBox);
        commentList = (ListView) findViewById(R.id.commentList);

        category.setOnItemSelectedListener(this);




        // Get objectID from search_result
        Intent intent = getIntent();
        objectIdRoom = intent.getStringExtra("objectID");
        Log.i("AppInfo",objectIdRoom);
        // Get objectId

        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
        roomQuery.whereEqualTo("objectId", objectIdRoom);
        roomQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("AppInfo", "roomQuery success" + objects.size());
                    dateTextView.setText(String.valueOf(objects.get(0).get("studyDate")));
                    timeTextView.setText(String.valueOf(objects.get(0).get("studyTime")));
                    roomTitle.setText(String.valueOf(objects.get(0).get("title")));
                    capacity.setText(String.valueOf(objects.get(0).get("capacity")));

                    String categoryTemp = String.valueOf(objects.get(0).get("category"));
                    int cateNum;
                    if (categoryTemp.equals("Exam")) {
                        cateNum = 1;
                    } else if (categoryTemp.equals("Quiz")) {
                        cateNum = 2;
                    } else if (categoryTemp.equals("Homework")) {
                        cateNum = 3;
                    } else if (categoryTemp.equals("Project")) {
                        cateNum = 4;
                    } else {
                        cateNum = 5;
                    }
                    category.setSelection(cateNum);
                    categoryText.setText(categoryTemp);
                    courseName.setText(String.valueOf(objects.get(0).get("course")));
                    courseNumber.setText(String.valueOf(objects.get(0).get("number")));
                    description.setText(String.valueOf(objects.get(0).get("description")));
                    if (String.valueOf(objects.get(0).get("timepassed")).equals("True")) {
                        timepassed = true;
                    } else {
                        timepassed = false;
                    }
                    if (String.valueOf(objects.get(0).get("opened")).equals("True")) {
                        opened = true;
                    } else {
                        opened = false;
                    }

                    // Get userId of bang-jang

                    ParseObject bangjang = (ParseObject) objects.get(0).get("createdBy");
                    userId = bangjang.getObjectId();

                    Log.i("AppinfoBangjang", userId);
                    Log.i("AppinfoBangjang", ParseUser.getCurrentUser().getObjectId());
                    String currentUserId = ParseUser.getCurrentUser().getObjectId();
                    // If login user is not creater of room, no show of edit and delete buttons.
                    if(currentUserId.equals(userId)){
                        roomEditable = true;
                        Log.i("AppinfoBangjang","roomeditable is true");
                        joinBtn.setVisibility(View.INVISIBLE);
                    }else{
                        roomEditable = false;
                        Log.i("AppinfoBangjang","roomeditable is false");
                        // Make buttons invisible when it is not edit mode
                        editBtn.setVisibility(View.INVISIBLE);
                        deleteBtn.setVisibility(View.INVISIBLE);

                    }


                } else {
                    Log.i("AppInfo", "ParseException");
                }
            }
        });

        category.setVisibility(View.INVISIBLE);
        editDoneBtn.setVisibility(View.VISIBLE);






        // Disable all the views and make buttons invisible when it is not edit mode
        editMode(false);

        // COMMENT
        // Get Comment Items list
        commentItem = new ArrayList<Comments>();
        ParseQuery<ParseObject> commentQuery = ParseQuery.getQuery("Room");
        commentQuery.whereEqualTo("objectId", objectIdRoom);
        commentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if(objects.size() == 0){}
                    else {
                        ParseObject course = objects.get(0);

                        ArrayList<Comments> temp = (ArrayList<Comments>) course.get("comment");
                        Log.i("Appinfo", "" + temp.size());
                        for (Comments obj : temp) {
                            commentItem.add(obj);
                        }
                    }
                } else {
                    Log.i("Appinfo", "failed to get comments");
                }
            }
        });




        // comment adapter
        commentAdapter = new commentAdapt();
        commentList.setAdapter(commentAdapter);





    }



    private class commentAdapt extends ArrayAdapter{
        public commentAdapt(){
            super(Room.this, R.layout.comment_view, commentItem);
            Log.i("CommentInfo", "Constructor");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            // Make sure we have a view to work with (may have been given null)
            Log.i("CommentInfo", "getView method1");
            View cmtView = convertView;
            if(cmtView == null){
                cmtView = getLayoutInflater().inflate(R.layout.comment_view, parent, false);
            }
            // Find the comment to work with.
            Comments commentObject = commentItem.get(position);

            // Fill the view.
            // ImageView
//            commentPhoto= (ImageView) cmtView.findViewById(R.id.commentPhoto);

            final String objectIdOfCommentUser = commentObject.getCommenterId();

            // Get the user and image from him

            ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("User");
            userQuery.whereEqualTo("objectId",objectIdOfCommentUser);
            userQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        Log.i("Appinfo","CommentUser Id : "+ objectIdOfCommentUser);
                        Log.i("Appinfo", "Objects size: "+ objects.size());
                        ParseObject userObj = objects.get(0);
                        userPhoto = userObj.getParseFile("photo");
                        commentUser = userObj.getString("username");
                    } else {
                        Log.i("Appinfo", "failed to get user for photo");
                    }
                }
            });



//            //TODO DongBin
//            // Set the image to the commentPhoto imageView
//            if(userPhoto != null){
//                userPhoto.getDataInBackground(new GetDataCallback() {
//                    @Override
//                    public void done(byte[] data, ParseException e) {
//                        if(e == null){
//                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            commentPhoto.setImageBitmap(bmp);
//                        }
//                        else{
//                            Log.i("Appinfo", "converting Parsefile image to bitmap fails");
//                        }
//                    }
//                });
//            }else{
//                Log.i("Appinfo", "Getting the user's photo fails");
//            }

            // Commentary Textview
            commentary = (TextView) cmtView.findViewById(R.id.commentary);
            commentary.setText(commentObject.getComment());

            // CommentUsername Textview
            commentUsername = (TextView) cmtView.findViewById(R.id.commentUsername);
            commentUsername.setText(commentUser);


            // Comment Date
            commentDate = (TextView) cmtView.findViewById(R.id.commentDate);
            commentDate.setText(printDate(commentObject.getDate()));



            return cmtView;
        }
    }

    /*
        return print version of Dates of comments.
        Converting Date to comment format date String
     */
    private String printDate(Date cmtdate){
        Date todayNow = new Date();

        if(todayNow.getYear() == cmtdate.getYear()){
            // Same year
            if(todayNow.getMonth() == cmtdate.getMonth()){
                // same month
                if(todayNow.getDate() == cmtdate.getDate()){
                    // same date
                    if(todayNow.getHours()==cmtdate.getHours()){
                        // same hours
                        if(todayNow.getMinutes()==cmtdate.getMinutes()){
                            //same minutes
                            return "Now";
                        }else{
                            //same year, month,date, hours but diff minutes
                            // "OO minutes ago"
                            return todayNow.getMinutes()-cmtdate.getMinutes() + " minutes ago";
                        }
                    }else{
                        //same year,month,date but diff hours
                        // "OO hours ago"
                        return todayNow.getHours() - cmtdate.getHours() + " hours ago";
                    }
                }else{
                    // same year, same month, different date
                    // "OO days ago"
                    // "OO weeks ago"
                    String dayResult;
                    int days = todayNow.getDate() - cmtdate.getDate();
                    if (days < 14){
                        dayResult = days + " days ago";
                    }else if(days > 13 && days < 21){
                        dayResult = "2 weeks ago";
                    }else if(days > 20 && days < 28){
                        dayResult = "3 weeks ago";
                    }else{
                        dayResult = "4 weeks ago";
                    }
                    return dayResult;
                }
            }else {
                // same year different month
                //"OO months ago"
                return todayNow.getMonth() - cmtdate.getMonth() + " months ago";
            }
        }else{
            // different year
            //"OO years ago"
            return todayNow.getYear() - cmtdate.getYear() + " years ago";
        }



    }



    private void editMode(boolean b) {

        viewEnable(b, dateTextView);
        viewEnable(b, timeTextView);
        viewEnable(b, roomTitle);
        viewEnable(b, capacity);
        viewEnable(b, description);
        viewEnable(b, masterPhoto);

        if(b){
            categoryText.setVisibility(View.INVISIBLE);
            category.setVisibility(View.VISIBLE);
            editDoneBtn.setVisibility(View.VISIBLE);
        }else{
            category.setVisibility(View.INVISIBLE);
            categoryText.setVisibility(View.VISIBLE);
            editDoneBtn.setVisibility(View.INVISIBLE);
        }
    }

    /*
        For category
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category.setSelection(position);
        String selected = (String) category.getSelectedItem();
        selectedCategory = selected;
        Log.i("Appinfo", selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("AppInfo", "Nothing is selected");
    }

    /*
        edit button is tapped
     */
    public void editBtn(View view){
        editMode(true);
    }

    /*
        edit Done Button is tapped.
     */
    public void editDoneBtn(View view){
        //update the info in Parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.whereEqualTo("objectId", objectIdRoom);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("Appinfo", "Updating editted infos fail");
                    //If all required fields are not filled
                    if(selectedCategory.toString().matches("Category")||
                            capacity.getText().toString().matches("")||
                            dateTextView.getText().toString().matches("Set Date")||
                            timeTextView.getText().toString().matches("Set Time")||
                            description.getText().toString().matches("")||
                            roomTitle.getText().toString().matches("")){
                        //Error message
                        Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
                        return;

                    }else{
                        //If all required fields are filled
                        ParseObject obj = objects.get(0);
                        obj.put("title", String.valueOf(roomTitle.getText()));
                        obj.put("category", selectedCategory);
                        obj.put("studyDate", String.valueOf(dateTextView.getText()));
                        obj.put("studyTime", String.valueOf(timeTextView.getText()));
                        obj.put("description", String.valueOf(description.getText()));
                        obj.put("capacity", Integer.parseInt(String.valueOf(capacity.getText())));
                        obj.saveInBackground();

                        editMode(false);
                    }
                } else {
                    Log.i("Appinfo", "Updating editted infos fail");
                }
            }
        });
        //update the room page(category)
        categoryText.setText(selectedCategory);
    }

    /*
        delete button is tapped
     */
    public void deleteBtn(View view){

        // Delete the room object
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.whereEqualTo("objectId", objectIdRoom);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i("Appinfo", "Delete the Room");
                    objects.get(0).deleteInBackground();
                }else{
                    Log.i("Appinfo","Delete fail");
                }
            }
        });
        //Redirect to mygroup activity
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    /*
        when join button is tapped
     */
    public void joinBtn(View view){
        // join to parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.whereEqualTo("objectId", objectIdRoom);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject roomObj = objects.get(0);
                    // Get member List
                    ArrayList<ParseUser> memberList = (ArrayList<ParseUser>) roomObj.get("member");
                    // See if current user is already on the list
                    Boolean found = false;
                    for (ParseUser member : memberList) {
                        if (member.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                            found = true;
                            Toast.makeText(getApplicationContext(), "Already Joined this Study Group", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    // If current user is not on the member list, add him/her to the list
                    if (!found) {
                        roomObj.add("member", ParseUser.getCurrentUser());
                        roomObj.saveEventually();
                    }

                } else {
                    Log.i("Appinfo", "Join fail");
                }
            }
        });

        // Toast message
        Toast.makeText(getApplicationContext(), "Succesfully joined the group!", Toast.LENGTH_SHORT).show();
        // Go to my group page
        Intent i = new Intent(getApplicationContext(), MyGroup.class);
        // Removes animation
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

    }



    /*
        If comment button is tapped
     */
    public void commentBtn(View view) {
        if(commentBox.getText().equals("")){
            // Do Nothing
            Log.i("Appinfo","Nothing on comment Box!");
        }else{
            Log.i("Appinfo", "Something on comment box!");

            // Add the comment to the parse
            String comment = String.valueOf(commentBox.getText());
            String commenterId = String.valueOf(ParseUser.getCurrentUser().getObjectId());

            // Get date of today
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date rightnow = new Date();

            // Comment object
            commentObj = new Comments(comment, commenterId, rightnow);


            ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
            query.whereEqualTo("objectId", objectIdRoom);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        Log.i("Appinfo", "put comment successfully");
                        objects.get(0).add("comment", commentObj);
                        objects.get(0).saveInBackground();
                    } else {
                        Log.i("Appinfo", "putting comment fails");

                    }
                }
            });


            // Update Comment list
            commentItem.add(commentObj);
            Log.i("Appinfo", "Comment is updated!");
            commentList.setAdapter(commentAdapter);
            commentBox.setText("");



        }
    }
    /*
        To disable view
     */
    public void viewEnable(boolean sw, View v){

        v.setEnabled(sw);
        v.setClickable(sw);
        v.setFocusableInTouchMode(sw);
        v.setFocusable(sw);
    }



}