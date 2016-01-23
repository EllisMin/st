package com.parse.starter;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.util.Date;

/**
 * Created by Junwoo on 1/13/2016.
 */
public class Comments {

    private String comment;
    private String commenterId;
    private Date date;
    private String commenterName;
    private Bitmap commenterPhoto;

    public Comments(String comment, String commenterId, Date date, String commenterName, Bitmap commenterPhoto){
        this.comment = comment;
        this.commenterId = commenterId;
        this.date = date;
        this.commenterName = commenterName;
        this.commenterPhoto = commenterPhoto;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setCommentId(String commenterId){
        this.commenterId = commenterId;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setCommenterName(String commenterName){
        this.commenterName = commenterName;
    }

    public void setCommenterPhoto(Bitmap commenterPhoto){
        this.commenterPhoto = commenterPhoto;
    }

    public String getComment(){
        return comment;
    }

    public String getCommenterId(){
        return commenterId;
    }

    public Date getDate(){
        return date;
    }

    public String getCommenterName(){
        return commenterName;
    }

    public Bitmap getCommenterPhoto(){
        return commenterPhoto;
    }







}
