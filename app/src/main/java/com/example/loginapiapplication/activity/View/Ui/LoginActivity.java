package com.example.loginapiapplication.activity.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.example.loginapiapplication.activity.ViewModel.LoginViewModel;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.example.loginapiapplication.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding bindingM;
    private MySharedPreferences mySharedPreferences;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initi();
        if (mySharedPreferences.getIsCheck(MySharedPrefernceKey.ISCHECK)) {
            Intent ii = new Intent(LoginActivity.this, UserPostActivity.class);
            startActivity(ii);
        }
        bindingM = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bindingM.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        bindingM.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        bindingM.btnCreateAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("update", "create");
                startActivity(intent);
            }
        });
    }


    private void userLogin() {
        String email = bindingM.inputEmail.getText().toString().trim();
        String password = bindingM.inputPassword.getText().toString().trim();
        if (email.isEmpty()) {

            bindingM.inputEmail.setError("Enter is email address");
            bindingM.inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bindingM.inputEmail.setError("Enter a valid  email address");

            bindingM.inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            bindingM.inputPassword.setError("Enter is password address");
            bindingM.inputPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            bindingM.inputPassword.setError("Enter a valid  password address");

            bindingM.inputPassword.requestFocus();
            return;
        }
        login();

    }


    private void login() {
        String userName = bindingM.inputEmail.getText().toString().trim();
        String password = bindingM.inputPassword.getText().toString().trim();
        mySharedPreferences.saveString(MySharedPrefernceKey.LOGIN_EMAIL, userName);
        mySharedPreferences.saveString(MySharedPrefernceKey.LOGIN_PASSWORD, password);
        bindingM.progressbarL.setVisibility(View.VISIBLE);
        loginViewModel.getLoginUser(getApplicationContext()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Mainactivity", "login response:  " + s);
                if (s.equals("old"));

                  else  if (s.equals("Success")) {
                        bindingM.progressbarL.setVisibility(View.GONE);

                        Intent intent = new Intent(LoginActivity.this, UserPostActivity.class);
                   startActivity(intent);

                    } else if (s.equals("Wrong Credentials")) {
                        bindingM.progressbarL.setVisibility(View.GONE);
                        Log.e("Mainactivity", "login response:  " + s);
                        bindingM.inputEmail.setError(s);


                    } else if (s.equals("Wrong password!")) {
                        bindingM.progressbarL.setVisibility(View.GONE);
                        Log.e("Mainactivity", "login response:  " + s);
                        bindingM.inputPassword.setError(s);
                    }
            }

        });

    }


    private void initi() {

        mySharedPreferences = new MySharedPreferences(LoginActivity.this);


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();

    }
}