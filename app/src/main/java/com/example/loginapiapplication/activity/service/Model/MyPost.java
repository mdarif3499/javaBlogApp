package com.example.loginapiapplication.activity.service.Model;

import java.util.ArrayList;

public class MyPost {
    private  String title;
    private   String body;
    private   String photo;
    private   String userId;
    private   String _id;
    private ArrayList<String>category;

    User user;

    public MyPost(String title, String body, String photo, String userId, ArrayList<String> category) {
        this.title = title;
        this.body = body;
        this.photo = photo;
        this.userId = userId;
        this.category = category;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    class Category {


    }
}
