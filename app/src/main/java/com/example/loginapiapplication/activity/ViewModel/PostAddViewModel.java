package com.example.loginapiapplication.activity.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginapiapplication.activity.service.Model.MyCategory;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Repository.PostAddRepository;

import java.util.ArrayList;
import java.util.List;

public class PostAddViewModel extends AndroidViewModel {
    private PostAddRepository postAddRepository;


    public PostAddViewModel(@NonNull Application application) {
        super(application);
        postAddRepository = PostAddRepository.getInstancePostAddRepository(application);
    }

    public LiveData<String>mYPostAdd(Context applicationContex, MyPost myPost){



   return postAddRepository.AddPost(myPost); }





    public  LiveData<ArrayList<MyCategory>>getCategory(){


    return postAddRepository.getCategory();}


    public  LiveData<String>updatePost(String title,String body,String id){


    return postAddRepository.updatePost(title,body,id);}



}
