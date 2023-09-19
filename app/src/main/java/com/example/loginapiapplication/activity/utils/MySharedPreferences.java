package com.example.loginapiapplication.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class MySharedPreferences {
    Context context;
    SharedPreferences sharedPreferences;


    public MySharedPreferences(final Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);

    }

    public void saveString(String key,String value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);

        editor.commit();

    }
    public void setint(String key,int value){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setboolean(String key,Boolean value){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setFloat(String key,Float value){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setLong(String key,Long value){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }



    public int getint(String Key){
        return sharedPreferences.getInt(Key,3);
    }

    public String getString(String key){


        return    sharedPreferences.getString(key,null);

    }

    public boolean getIsCheck (String key){


        return    sharedPreferences.getBoolean(key,false);

    }


}
