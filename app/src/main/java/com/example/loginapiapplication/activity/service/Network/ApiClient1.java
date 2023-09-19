package com.example.loginapiapplication.activity.service.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient1 {
    public static final String  BASE_URL="https://learningportal.cyclic.app";
    private static Retrofit retrofit=null;


    public static Retrofit getRetrofit(){

        if (retrofit==null){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }return retrofit;
    }
}
