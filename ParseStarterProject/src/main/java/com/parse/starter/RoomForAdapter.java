package com.parse.starter;

/**
 * Created by se7en_000 on 2016-01-16.
 */
public class RoomForAdapter {
    private int id;
    private String date;
    private String time;
    private String title;
    private String category;
    private String availability;

    //constructor
    public RoomForAdapter(String date, String time, String title, String category, String availability) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.category = category;
        this.availability = availability;
    }
    //getter,setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}