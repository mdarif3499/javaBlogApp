package com.example.loginapiapplication.activity.service.Network;

import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Model.MyPost;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/auth/signup")
    Call<java.lang.Object> postData(@Body User user);

    @POST("/api/auth/login")
    Call<java.lang.Object> loginData(@Query("userName") String name, @Query("password") String password);


    @GET("/api/users")
    Call<java.lang.Object> GetUgersData(@Header("Authorization") String authToken);


    @PUT("/api/users/{id}")
    Call<java.lang.Object> updateData(@Path("id") String id, @Header("Authorization") String authToken, @Body User user);

    @PATCH("/api/users/{id}")
    Call<java.lang.Object> patchData(@Path("id") String id, User user);

    @GET("/api/postBlog")
    Call<java.lang.Object> getUserPost(@Header("Authorization") String authToken);

    @POST ("/api/postBlog")
    Call<Object>userPost(@Header("Authorization") String authToken, @Body MyPost user);

    @GET("/api/postBlog")
    Call<Object> getMyPost(@Header("Authorization") String authToken, @Query("userId") String userId);

    @PUT("/api/postBlog/{id}")
    Call<Object> updateUserPost(@Path("id") String id, @Header("Authorization") String authToken, @Body Map user);

    @GET("/api/category")
    Call<java.lang.Object> getCategory(@Header("Authorization") String authToken);


    @DELETE("/api/postBlog/{id}")
    Call<Object> deletePost(@Path("id") String id, @Header("Authorization") String authToken);


}
