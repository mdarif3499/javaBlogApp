package com.example.loginapiapplication.activity.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginapiapplication.activity.service.Repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    LoginRepository loginRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = LoginRepository.getInstance(application);

    }

    public LiveData<String>getLoginUser(Context ctx){
        Log.e("check","view_model");
     //   loginRepository = LoginRepository.getInstance(ctx);
    return loginRepository.userLogin();

    }
}
