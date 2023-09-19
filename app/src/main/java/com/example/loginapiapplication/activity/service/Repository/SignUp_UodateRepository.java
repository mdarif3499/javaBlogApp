package com.example.loginapiapplication.activity.service.Repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.loginapiapplication.activity.View.Ui.LoginActivity;
import com.example.loginapiapplication.activity.View.Ui.SignUpActivity;
import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp_UodateRepository {

    private static Context context;
    private static MySharedPreferences mySharedPreferences;




    public static SignUp_UodateRepository getInstanceSingUpdata(Context appContext) {
        context = appContext;
        mySharedPreferences=new MySharedPreferences(context);

        return new SignUp_UodateRepository();
    }


    public MutableLiveData singupUser(User user) {
        MutableLiveData mutableLiveData = new MutableLiveData();

        ArrayList<String> list = new ArrayList<>();


        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Object> call = apiService.postData(user);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<java.lang.Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    try {


                        String str = new Gson().toJson(response.body());
                        JSONObject jsonObject = new JSONObject(str);
                        String message = jsonObject.getString("message");
                        JSONObject jsonObj2 = jsonObject.getJSONObject("data");
                        Gson gson = new Gson();


                        User data = gson.fromJson(jsonObj2.toString(), User.class);

                        mutableLiveData.postValue(list);

                    } catch (JSONException e) {

                        throw new RuntimeException(e);
                    }

                } else {
                    try {


                        JSONObject mainmErrorBody = new JSONObject(response.errorBody().string());
                        JSONObject jObjError = mainmErrorBody.getJSONObject("error");
                        JSONObject filedError = jObjError.getJSONObject("errors");


                        Map<String, String> mapt = new HashMap<String, String>();
                        Iterator iter = filedError.keys();
                        list.clear();
                        while (iter.hasNext()) {
                            String key = (String) iter.next();
                            JSONObject value = filedError.getJSONObject(key);
                            String message = value.getString("message");

                            list.add(key);
                            list.add(message);

                            mutableLiveData.postValue(list);


                        }

                    } catch (Exception e) {
                    }

                }
            }


            @Override
            public void onFailure(Call<java.lang.Object> call, Throwable t) {
            }
        });


        return mutableLiveData;
    }


    public MutableLiveData upDateUserId(User user) {
        MutableLiveData liveData1 = new MutableLiveData();


        try {
            Gson gson = new Gson();

            String str = mySharedPreferences.getString(MySharedPrefernceKey.JSON_STRING);
            JSONObject jsonObject4 = new JSONObject(str);
            User data = gson.fromJson(jsonObject4.toString(), User.class);
            String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);



            Retrofit retrofit = ApiClient1.getRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);

            Call<java.lang.Object> call = apiService.updateData(data.get_id(), "Bearer " + token, user);
            call.enqueue(new Callback<java.lang.Object>() {
                @Override
                public void onResponse(Call<java.lang.Object> call, Response<java.lang.Object> response) {
                    if (response.isSuccessful()) {
                        liveData1.postValue("success");

                    } else {
                        try {
                            JSONObject mainmErrorBody = new JSONObject(response.errorBody().string());
                            String message = mainmErrorBody.getString("message");
                            liveData1.postValue(message);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }


                }

                @Override
                public void onFailure(Call<java.lang.Object> call, Throwable t) {

                }
            });



        } catch (Exception e) {

        }


        return liveData1;
    }


}
