package com.example.loginapiapplication.activity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Repository.SignUp_UodateRepository;

import java.util.ArrayList;

public class SignUp_UpDate_ViewModel extends AndroidViewModel {
    private SignUp_UodateRepository suViewModel;

    public SignUp_UpDate_ViewModel(@NonNull Application application) {
        super(application);
        suViewModel=SignUp_UodateRepository.getInstanceSingUpdata(application);
    }


    public LiveData<ArrayList<String>>signUpUser(User user){



   return suViewModel.singupUser(user); }



public LiveData<String>UpDateUser(User user){



   return suViewModel.upDateUserId(user); }
}
