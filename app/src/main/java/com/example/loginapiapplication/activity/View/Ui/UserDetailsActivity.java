package com.example.loginapiapplication.activity.View.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.example.loginapiapplication.databinding.ActivityUserDetailsBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {
ActivityUserDetailsBinding bindingU;
    MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingU = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(bindingU.getRoot());
        init();



        try {
            showUserDetails();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



    private void showUserDetails() throws JSONException {
        Gson gson = new Gson();

        String str=mySharedPreferences.getString(MySharedPrefernceKey.JSON_STRING);
        JSONObject jsonObject4 = new JSONObject(str);
        User data = gson.fromJson(jsonObject4.toString(), User.class);


        bindingU.tvName.setText(data.getName());
        bindingU.emailDtls.setText(data.getEmail());
        bindingU.nameDtls.setText(data.getUserName());
        bindingU.imgDetail.setImageURI(Uri.parse(data.getProfile()));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.Search_viewId);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MIN_VALUE);
        searchView.setQueryHint("Search Hear");


        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.update:
                Intent intent=new Intent(UserDetailsActivity.this, SignUpActivity.class);
                intent.putExtra("update","update");
                startActivity(intent);
                break;
            case R.id.delete:
                break;

        }

        return super.onOptionsItemSelected(item);
    }





private void init(){
  mySharedPreferences   =new MySharedPreferences(UserDetailsActivity.this);
}





}