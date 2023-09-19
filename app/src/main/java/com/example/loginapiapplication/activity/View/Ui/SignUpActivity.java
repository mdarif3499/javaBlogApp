package com.example.loginapiapplication.activity.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.loginapiapplication.activity.ViewModel.SignUp_UpDate_ViewModel;
import com.example.loginapiapplication.activity.service.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.example.loginapiapplication.databinding.ActivitySignUpBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding bindingS;
    private SignUp_UpDate_ViewModel signUp_upDate_viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingS = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(bindingS.getRoot());
        signUp_upDate_viewModel=new ViewModelProvider(SignUpActivity.this).get(SignUp_UpDate_ViewModel.class);


        String str = getIntent().getExtras().getString("update");
        if (str.equals("update")) {
            bindingS.btnSinUP.setText("Update");
            bindingS.pasword1.setFocusable(false);

        }
        bindingS.btnSinUP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    userRegistrat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void userRegistrat() throws JSONException {
        String email = bindingS.email1.getText().toString().trim();
        String password = bindingS.pasword1.getText().toString().trim();
        if (email.isEmpty()) {

            bindingS.email1.setError("Enter is email address");
            bindingS.email1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bindingS.email1.setError("Enter a valid  email address");

            bindingS.email1.requestFocus();
            return;
        }

//        if (password.isEmpty()) {
//
//            bindingS.pasword1.setError("Enter is password address");
//            bindingS.pasword1.requestFocus();
//            return;
//        }
//        if (password.length() < 6) {
//            bindingS.pasword1.setError("Enter a valid  password address");
//
//            bindingS.pasword1.requestFocus();
//            return;
//        }


        String str = getIntent().getExtras().getString("update");
        if (str != null && str.equals("update")) {

            update();

        } else {
            signup();
        }


    }


    private void signup() {

        try {

            String name = bindingS.etName.getText().toString().trim();
            String userName = bindingS.etUserName.getText().toString().trim();
            String email = bindingS.email1.getText().toString().trim();
            String password = bindingS.pasword1.getText().toString().trim();

            User user = new User(name, userName, email, password);


            RelativeLayout layout = new RelativeLayout(this);
            bindingS.progressbar.setVisibility(View.VISIBLE);

            signUp_upDate_viewModel.signUpUser(user).observe(SignUpActivity.this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {

                    if (strings.size()>0){
                        showError(strings.get(0),strings.get(1));

                    }else {
                        Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                }
            });


        } catch (Exception e) {


        }


    }


    private void update()  {
        try {

            String name = bindingS.etName.getText().toString().trim();
            String userName = bindingS.etUserName.getText().toString().trim();
            String email = bindingS.email1.getText().toString().trim();
            User user = new User("" + name, "" + userName, "arif12", "1111111");
            bindingS.progressbar.setVisibility(View.VISIBLE);

             signUp_upDate_viewModel.UpDateUser(user).observe(SignUpActivity.this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("success"))
                    { Intent intent=new Intent(SignUpActivity.this,UserDetailsActivity.class);
                    startActivity(intent);}
                    else {
                        Toast.makeText(SignUpActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }catch (Exception e){

        }




    }

    private void showError(String key, String message) {
        switch (key) {

            case "userName":
                bindingS.progressbar.setVisibility(View.GONE);
                bindingS.etUserName.setError(message);
                Log.e("message", "......." + message);
                break;
            case "email":
                bindingS.progressbar.setVisibility(View.GONE);

                bindingS.email1.setError("" + message);
                Log.e("message", "......." + message);


        }


    }

}