package com.example.loginapiapplication.activity.service.Repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.View.Ui.MyPostActivity;
import com.example.loginapiapplication.activity.View.Ui.PostAddActivity;
import com.example.loginapiapplication.activity.View.Ui.UserPostActivity;
import com.example.loginapiapplication.activity.View.adapter.CategoryAdapter;
import com.example.loginapiapplication.activity.service.Model.MyCategory;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostAddRepository {
    private static Context mcontext;
    private static PostAddRepository instance;
    private static String Email;
    private static String Password;
    private ArrayList<String> categprYS;
    private ArrayList<MyCategory> listC;
    private ArrayList<MyCategory> listCM;
    private static MySharedPreferences mySharedPreferences;

    private PostAddRepository() {
    }

    public static PostAddRepository getInstancePostAddRepository(Context context) {
        mcontext = context;
        instance = new PostAddRepository();
        mySharedPreferences = new MySharedPreferences(mcontext);


        return instance;
    }


    public MutableLiveData AddPost(MyPost myPost1) {

        MutableLiveData mLiveData = new MutableLiveData();

        String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);
        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Object> call = apiService.userPost("Bearer " + token, myPost1);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.e("1111","=========");
                    mLiveData.postValue("success");

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


        return mLiveData;
    }

    public MutableLiveData getCategory() {
        init();
        MutableLiveData liveData = new MutableLiveData();

        Retrofit retrofit = ApiClient1.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);

        String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);

        Call<java.lang.Object> call = apiService.getCategory("Bearer " + token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    try {

                        Log.e("category", "cccccccc" + response.body().toString());
                        String str = new Gson().toJson(response.body());
                        JSONObject jsonObject = new JSONObject(str);
                        String message = jsonObject.getString("message");
                        JSONArray jsonObj2 = jsonObject.getJSONArray("data");
                        Log.e("list", jsonObj2.toString());

                        Gson gson = new Gson();
                        Type typeOfList = new TypeToken<List<MyCategory>>() {
                        }.getType();
                        listC = gson.fromJson(jsonObj2.toString(), typeOfList);

                        Log.e("list", "" + listC.size());
                        listCM.clear();
                        for (int i = 0; i < listC.size(); i++) {
                            MyCategory myCategory = listC.get(i);
                            myCategory.setCHeck(false);
                            listCM.add(myCategory);
                            liveData.postValue(listCM);

                        }


                    } catch (Exception e) {

                    }


                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
        return liveData;
    }


    public MutableLiveData updatePost(String title, String body, String id) {
        MutableLiveData mutableLiveData = new MutableLiveData();

        if (!title.isEmpty() && !title.equals("") && !body.isEmpty() && !body.equals("")) {


            String token = mySharedPreferences.getString(MySharedPrefernceKey.TOKEN);
            Retrofit retrofit = ApiClient1.getRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Map<String, Object> map = new HashMap();
            map.put("title", title);
            map.put("body", body);
            map.put("category", new ArrayList());
            map.put("photo", "");
            Call<java.lang.Object> call = apiService.updateUserPost("" + id, "Bearer " + token, map);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        mutableLiveData.postValue("success");
                        Log.e("response", "" + response.body().toString());


                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });


        }


        return mutableLiveData;
    }


    private void init() {
        categprYS = new ArrayList<>();
        listC = new ArrayList<>();
        listCM = new ArrayList<>();


    }


}
