package com.example.loginapiapplication.activity.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Repository.MyPostRepository;

import java.util.List;

public class MyPostViewModel extends AndroidViewModel {
    public MyPostRepository myPostRepository;


    public MyPostViewModel(@NonNull Application application) {
        super(application);
        myPostRepository=MyPostRepository.getInstanceMyPost(application);
    }

    public LiveData<List<MyPost>>getSelfPost_v(Context applicationContext){

    return myPostRepository.getSelfPost();}



    public LiveData<String>deleteSelfPost_v(Context applicationContext,String d1){


        return myPostRepository.deleteMyPost(d1);}





}
