package com.example.loginapiapplication.activity.View.Ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.loginapiapplication.activity.View.adapter.MyPostAdapter;
import com.example.loginapiapplication.activity.ViewModel.MyPostViewModel;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.CustomOnClickListener;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.example.loginapiapplication.databinding.ActivityMyPostBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPostActivity extends AppCompatActivity {
    private ActivityMyPostBinding bindingM;
    private MyPostAdapter userPostAdapter;
    private List<MyPost> list;
    private MutableLiveData liveData;
    private MyPostViewModel myPostViewModel;
    private MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initi();
        bindingM = ActivityMyPostBinding.inflate(getLayoutInflater());
        setContentView(bindingM.getRoot());

        myPostViewModel = new ViewModelProvider(this).get(MyPostViewModel.class);

    }


    private void requestUserPost() {
        bindingM.progressbar.setVisibility(View.VISIBLE);

        myPostViewModel.getSelfPost_v(getApplicationContext()).observe(MyPostActivity.this, new Observer<List<MyPost>>() {
            @Override
            public void onChanged(List<MyPost> myPosts) {
                showMypost(myPosts);
            }
        });


    }

    private void showMypost(List<MyPost> myPostList) {
        CustomOnClickListener listener = new CustomOnClickListener() {
            @Override
            public void customOnClickListener(int p, int code, String id) {
                if (code == 1) {
                    updateUsere(p);

                } else {
                    deleteUserPost(p, id);
                }
            }


        };
        if (myPostList != null)
            list = myPostList;

        bindingM.progressbar.setVisibility(View.GONE);
        bindingM.recycleryViewId.setLayoutManager(new LinearLayoutManager(MyPostActivity.this));
        userPostAdapter = new MyPostAdapter(list, MyPostActivity.this, listener);
        bindingM.recycleryViewId.setAdapter(userPostAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void  deleteUserPost(int p, String id1) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyPostActivity.this);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      myPostViewModel.deleteSelfPost_v(MyPostActivity.this,id1).observe(MyPostActivity.this, new Observer<String>() {
                          @Override
                          public void onChanged(String s) {
                              requestUserPost();
                              Toast.makeText(MyPostActivity.this, "", Toast.LENGTH_SHORT).show();
                          }
                      });


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void updateUsere(int p) {
        if (list != null) {
            MyPost post = list.get(p);
            String id = post.get_id();
            Intent i = new Intent(MyPostActivity.this, PostAddActivity.class);
            i.putExtra("code", "update");
            i.putExtra("id", "" + id);


            startActivity(i);


        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        requestUserPost();

    }


    private void initi() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        mySharedPreferences = new MySharedPreferences(MyPostActivity.this);
    }


}