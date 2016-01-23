package com.parse.starter;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ellis on 1/10/16.
 */
public class Group {

    private String title;
    private String studyDate;
    private String studyTime;
    private String courseName;
    private String courseNumber;
    private String category;
    private int capacity;
    private String description;
    private boolean opened; // Variable that becomes false when capacity hits the maximum
    private boolean timePassed; // Variable that becomes false when study time passes
    private List members;
    private List<Comments> cmt;

    public Group(String title, String studyDate, String studyTime, String courseName,
                 String courseNumber, String category,
                 int capacity, String description, boolean opened
            , boolean timePassed, List members) {
        super();
        ParseObject obj = new ParseObject("Room");
        ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
        postACL.setPublicReadAccess(true);
        obj.setACL(postACL);
        obj.put("title", title);
        obj.put("course", courseName);
        obj.put("number", courseNumber);
        obj.put("capacity", capacity);
        obj.put("description", description);
        obj.put("studyDate", studyDate);
        obj.put("studyTime", studyTime);
        obj.put("opened", opened);
        obj.put("timePassed", timePassed);
        obj.put("category", category);
        obj.put("createdBy", ParseUser.getCurrentUser());
        cmt = new ArrayList<Comments>();
        obj.put("comment", cmt);
        members = new ArrayList<ParseUser>();
        members.add(ParseUser.getCurrentUser());
        Log.i("AppInfo", String.valueOf(ParseUser.getCurrentUser().getObjectId()));
        obj.put("member", members);
//
//        this.title = title;
//        this.courseName = courseName;
//        this.courseNumber = courseNumber;
//        this.capacity = capacity;
//        this.description = description;
//        this.studyDate = studyDate;
//        this.studyTime = studyTime;
//        this.opened = opened;
//        this.timePassed = timePassed;
//        this.category = category;
        obj.saveInBackground();
    }
    private void createQuery() {
//        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
////        roomQuery.whereEqualTo("course", courseName);
//        //  roomQuery.whereEqualTo("number" , courseNumber);
//        roomQuery.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    Log.i("APPINFO", "Retrieved " + objects.size() + " results");
//                    for (ParseObject obj : objects) {
//                        dates.add(String.valueOf(obj.get("studyDate")));
//                    }
//
//                }
//            }
//        });
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public String getCourseName() {
//        return courseName;
//    }
//
//    public String getCourseNumber() {
//        return courseNumber;
//    }
//
//    public int getCapacity() {
//        return capacity;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getStudyDate() {
//        return studyDate;
//    }
//
//    public String getStudyTime() {
//        return studyTime;
//    }
//
//    public boolean getOpened() {
//        return opened;
//    }
//
//    public boolean getTimePassed() {
//        return timePassed;
//    }
//
//    public String getCategory() {
//        return category;
//    }
}
