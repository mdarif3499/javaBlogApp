package com.example.loginapiapplication.activity.service.Repository;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loginapiapplication.activity.View.Ui.LoginActivity;
import com.example.loginapiapplication.activity.View.Ui.UserPostActivity;
import com.example.loginapiapplication.activity.View.adapter.UserPostAdapter;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserPostRepository {
    private List<MyPost> list;
    private List<User> list1;
    private static UserPostRepository userPostRepository;
    private static Context context;
    private static MySharedPreferences mySharedPreferences;

    private UserPostRepository() {
    }

    public static UserPostRepository getInestance(Context context1) {
        if (userPostRepository == null) {
            userPostRepository = new UserPostRepository();
        }
        context = context1;
        mySharedPreferences = new MySharedPreferences(context);
        return userPostRepository;
    }

    public MutableLiveData getUserPost() {
        MutableLiveData liveData = new MutableLiveData();

        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);


        String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);

        Call<Object> call = apiService.getUserPost("Bearer " + token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<java.lang.Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    try {


                        String str = new Gson().toJson(response.body());
                        JSONObject jsonObject = new JSONObject(str);
                        String message = jsonObject.getString("message");
                        JSONArray jsonObj2 = jsonObject.getJSONArray("data");


                        Gson gson = new Gson();
                        Type typeOfList = new TypeToken<List<MyPost>>() {
                        }.getType();
                        list = gson.fromJson(jsonObj2.toString(), typeOfList);
                       liveData.postValue(list);


                    } catch (Exception e) {

                    }


                } else {
                    try {

                        JSONObject mainmErrorBody = new JSONObject(response.errorBody().string());
                        mySharedPreferences.setboolean(MySharedPrefernceKey.ISCHECK, false);
//                        Intent ii = new Intent(UserPostActivity.this, LoginActivity.class);
//                        startActivity(ii);

                    } catch (Exception e) {

                    }


                }

            }

            @Override
            public void onFailure(Call<java.lang.Object> call, Throwable t) {

            }
        });


        return liveData;
    }


}
