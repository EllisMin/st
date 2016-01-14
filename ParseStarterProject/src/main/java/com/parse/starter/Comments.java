package com.parse.starter;

import java.util.Date;

/**
 * Created by Junwoo on 1/13/2016.
 */
public class Comments {

    private String comment;
    private String commenterId;
    private Date date;


    public Comments(String comment, String commenterId, Date date){
        this.comment = comment;
        this.commenterId = commenterId;
        this.date = date;
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

    public String getComment(){
        return comment;
    }

    public String getCommenterId(){
        return commenterId;
    }

    public Date getDate(){
        return date;
    }







}
