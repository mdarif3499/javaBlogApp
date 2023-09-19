package com.example.loginapiapplication.activity.service.Model;

public class MyCategory {
    private  String _id;
    private  String name;
    private boolean isCHeck;

    public MyCategory() {
    }

    public MyCategory(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public boolean isCHeck() {
        return isCHeck;
    }

    public void setCHeck(boolean CHeck) {
        isCHeck = CHeck;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
