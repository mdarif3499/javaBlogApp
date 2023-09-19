package com.example.loginapiapplication.activity.service.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.loginapiapplication.activity.service.Model.MyPost;
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

public class MyPostRepository {
    private static Context mcontext;
    private static MyPostRepository instance;
    private static String Email;
    private static String Password;
    private static MySharedPreferences mySharedPreferences;

    private List<MyPost> list;

    private MyPostRepository() {

    }

    public static MyPostRepository getInstanceMyPost(Context context) {
        instance = new MyPostRepository();

        mcontext = context;
        mySharedPreferences = new MySharedPreferences(context);
        return instance;
    }

    public MutableLiveData getSelfPost() {

        MutableLiveData mLiveData = new MutableLiveData();
        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);


        String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);
        String userId = mySharedPreferences.getString(MySharedPrefernceKey.ID);
        Log.e("token", "userid" + userId + "....token........" + token);

        Call<Object> call = apiService.getMyPost("Bearer " + token, "" + userId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    try {


                        Log.e("token", "......list........." + response.body().toString());
                        String str = new Gson().toJson(response.body());
                        JSONObject jsonObject = new JSONObject(str);
                        String message = jsonObject.getString("message");
                        JSONArray jsonObj2 = jsonObject.getJSONArray("data");


                        Gson gson = new Gson();
                        Type typeOfList = new TypeToken<List<MyPost>>() {
                        }.getType();
                        list = gson.fromJson(jsonObj2.toString(), typeOfList);

                        mLiveData.postValue(list);
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


        return mLiveData;
    }

    public MutableLiveData deleteMyPost(String id1) {

        MutableLiveData mutableLiveDataD = new MutableLiveData();
        String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);
        Log.e("delete", "......id....." + id1 + "........" + token);
        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Object> call = apiService.deletePost(id1, "Bearer " + token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    Log.e("delete", "......................." + response.body().toString());

                    mutableLiveDataD.postValue("success");

                } else {
                    try {


                    } catch (Exception e) {


                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


   return mutableLiveDataD; }


}
