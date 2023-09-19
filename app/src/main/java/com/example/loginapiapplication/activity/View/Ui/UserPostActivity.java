package com.example.loginapiapplication.activity.View.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.View.adapter.UserPostAdapter;
import com.example.loginapiapplication.activity.ViewModel.LoginViewModel;
import com.example.loginapiapplication.activity.ViewModel.MyPostViewModel;
import com.example.loginapiapplication.activity.ViewModel.UserPostViewModel;
import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;

import com.example.loginapiapplication.databinding.ActivityUsersPostBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserPostActivity extends AppCompatActivity {
    ActivityUsersPostBinding bindingP;
    private ActionBarDrawerToggle toggle;
    private UserPostAdapter userPostAdapter;
    private List<MyPost> list;
    private List<User> list1;
    private LoginViewModel loginViewModel;
    private MySharedPreferences mySharedPreferences;
    private UserPostViewModel userPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingP = ActivityUsersPostBinding.inflate(getLayoutInflater());

        setContentView(bindingP.getRoot());
        initi();
        userPostViewModel = new ViewModelProvider(UserPostActivity.this).get(UserPostViewModel.class);

        showUserPost();

        drawer();


        bindingP.btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPostActivity.this, PostAddActivity.class);
                i.putExtra("code", "post");
                startActivity(i);
            }
        });

    }


    private void drawer() {


        toggle = new ActionBarDrawerToggle(UserPostActivity.this, bindingP.drawerLayout, R.string.open, R.string.close);
        bindingP.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Gson gson = new Gson();

        String userDeteles = mySharedPreferences.getString(MySharedPrefernceKey.JSON_STRING);
        JSONObject jsonObject4 = null;
        try {
            jsonObject4 = new JSONObject(userDeteles);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        User data = gson.fromJson(jsonObject4.toString(), User.class);


        Menu menu = bindingP.navView.getMenu();
        MenuItem home = menu.findItem(R.id.mHome);
        View header = bindingP.navView.getHeaderView(0);
        TextView tv_name, tv_email = ((TextView) header.findViewById(R.id.arif1));
        tv_name = header.findViewById(R.id.tv_name_h);
        tv_name.setText("" + data.getName());

        tv_email.setText("" + data.getEmail());


        bindingP.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.mHome:
                        Intent intent = new Intent(UserPostActivity.this, UserDetailsActivity.class);
                        startActivity(intent);

                        bindingP.drawerLayout.closeDrawers();
                        break;

                    case R.id.myPost:
                        Intent i = new Intent(UserPostActivity.this, MyPostActivity.class);
                        startActivity(i);
                        bindingP.drawerLayout.closeDrawers();
                        break;

                    case R.id.logout:
                        mySharedPreferences.setboolean(MySharedPrefernceKey.ISCHECK, false);

                        Intent ii = new Intent(UserPostActivity.this, LoginActivity.class);
                        startActivity(ii);

                        finish();


                        bindingP.drawerLayout.closeDrawers();
                        break;

                }


                return false;
            }
        });


        bindingP.imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                bindingP.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void showUserPost() {
        list=new ArrayList<>();

        bindingP.progressbar.setVisibility(View.VISIBLE);

        userPostViewModel.getUserPost().observe(this, new Observer<List<MyPost>>() {
            @Override
            public void onChanged(List<MyPost> myPostList) {


                bindingP.recycleryViewId.setLayoutManager(new LinearLayoutManager(UserPostActivity.this));
                userPostAdapter = new UserPostAdapter(myPostList, list1, UserPostActivity.this);
                bindingP.recycleryViewId.setAdapter(userPostAdapter);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        showUserPost();

    }


    private void initi() {

        mySharedPreferences = new MySharedPreferences(UserPostActivity.this);


    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}