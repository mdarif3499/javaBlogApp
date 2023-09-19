package com.example.loginapiapplication.activity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Repository.UserPostRepository;

import java.util.List;

public class UserPostViewModel extends AndroidViewModel {
    private UserPostRepository userPostRepository;

    public UserPostViewModel(@NonNull Application application) {
        super(application);
        userPostRepository = UserPostRepository.getInestance(application);
    }



   public LiveData<List<MyPost>>getUserPost(){



        return userPostRepository.getUserPost();
   }

}
