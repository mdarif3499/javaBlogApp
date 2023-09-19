package com.example.loginapiapplication.activity.service.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository  {

    private static Context mcontext;
    private static LoginRepository instance;
    private static String Email;
    private static String Password;
    private static MySharedPreferences mySharedPreferences;
    private MutableLiveData mLiveData;



    public static LoginRepository getInstance(Context context) {
        if (instance == null) {
            mcontext = context;
            instance = new LoginRepository();
            mySharedPreferences=new MySharedPreferences(mcontext);




        }

        return instance;
    }
//    public LoginRepository(Context context) {
//            mcontext = context;
//    }
    public MutableLiveData userLogin() {

           mLiveData = new MutableLiveData();



        try {

            String password = mySharedPreferences.getString(MySharedPrefernceKey.LOGIN_PASSWORD);
            String userName = mySharedPreferences.getString(MySharedPrefernceKey.LOGIN_EMAIL);
            Retrofit retrofit = ApiClient1.getRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);

            Call<Object> call = apiService.loginData(userName, password);
            call.enqueue(new Callback<Object>() {

                @Override
                public void onResponse(Call<java.lang.Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        Log.e("check", "successfull");

                        try {
                            String str = new Gson().toJson(response.body());
                            JSONObject jsonObject = new JSONObject(str);
                            String message = jsonObject.getString("message");
                            Log.e("check", "" + message);
                            String token = jsonObject.getString("token");
                            JSONObject jsonObj2 = jsonObject.getJSONObject("data");
                            String id = jsonObj2.getString("_id");
                            String name = jsonObj2.getString("name");

                            String str2 = jsonObj2.toString();

                            Gson gson = new Gson();
                            mySharedPreferences.saveString(MySharedPrefernceKey.JSON_STRING, "" + str2);
                            String str1 = mySharedPreferences.getString(MySharedPrefernceKey.JSON_STRING);
                            JSONObject jsonObject4 = new JSONObject(str1);
                            mySharedPreferences.saveString(MySharedPrefernceKey.TOKEN, token);
                            mySharedPreferences.saveString(MySharedPrefernceKey.ID, id);
                            mySharedPreferences.saveString(MySharedPrefernceKey.NAME, name);

                            User data = gson.fromJson(jsonObject4.toString(), User.class);



                            mySharedPreferences.setboolean(MySharedPrefernceKey.ISCHECK, true);


                            mLiveData.postValue("" + message);

                            Log.e("response", "......" + data.getUserName());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }


                    } else {


                        try {

                            JSONObject mainmErrorBody = new JSONObject(response.errorBody().string());

                            Log.e("check", "" + mainmErrorBody.getString("message"));
                            String error = mainmErrorBody.getString("message");
                            mLiveData.postValue("" + error);

                        } catch (Exception e) {
                            Log.e("check", ".....errorBody.Exception...." + e.getMessage());
                        }


                    }

                }


                @Override
                public void onFailure(Call<java.lang.Object> call, Throwable t) {

                }
            });


        } catch (Exception e) {

        }


        return mLiveData;
    }






}
