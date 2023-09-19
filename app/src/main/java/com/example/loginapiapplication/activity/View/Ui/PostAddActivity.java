package com.example.loginapiapplication.activity.View.Ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.View.adapter.CategoryAdapter;
import com.example.loginapiapplication.activity.ViewModel.MyPostViewModel;
import com.example.loginapiapplication.activity.ViewModel.PostAddViewModel;
import com.example.loginapiapplication.activity.service.Model.MyCategory;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.service.Network.ApiClient1;
import com.example.loginapiapplication.activity.service.Network.ApiService;
import com.example.loginapiapplication.activity.utils.MySharedPreferences;
import com.example.loginapiapplication.activity.utils.MySharedPrefernceKey;
import com.example.loginapiapplication.databinding.ActivityPostAddBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

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

public class PostAddActivity extends AppCompatActivity {
    private static int IMAGE_REQUEST = 1;
    private Uri imgUri;
    private String photo;
    private ArrayList<String> categprYS;
    private ArrayList<MyCategory> listC;
    private ArrayList<MyCategory> listCM;
    private ActivityPostAddBinding binding;

    private MySharedPreferences sharedPreferences;
    private String code, id;
    private RecyclerView recylerView;
    private CategoryAdapter categoryAdapter;

    private PostAddViewModel postAddViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initi();
        postAddViewModel = new ViewModelProvider(this).get(PostAddViewModel.class);

        binding.categoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory();
            }


        });


        if (code.equals("update")) {
            binding.btnPost.setText("update");
        }
        binding.imgCamra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        Log.e("idid", "" + id);


        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressbar.setVisibility(View.VISIBLE);
                if (code.equals("update")) {
                    updatePost();
                } else {
                    Post();
                }

            }


        });

    }


    private void Post() {

        String title = binding.etTitle.getText().toString().trim();
        String body = binding.etBody.getText().toString().trim();

        String userId = sharedPreferences.getString(MySharedPrefernceKey.ID);

        if (!title.isEmpty() && !title.equals("") && !body.isEmpty() && !body.equals("")) {


            MyPost myPost = new MyPost("" + title, "" + body, "https://pixabay.com/photos/tree-sunset" +
                    "-clouds-sky-silhouette-736885/", "" + userId, categprYS);
            postAddViewModel.mYPostAdd(getApplicationContext(), myPost).observe(PostAddActivity.this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Toast.makeText(PostAddActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostAddActivity.this, UserPostActivity.class);
                    startActivity(intent);

                }
            });


        }


    }


    private void openFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(binding.imgCamra);

        }
    }


    public String getFileExtension(Uri image) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(image));
    }


    private void updatePost() {
        binding.progressbar.setVisibility(View.VISIBLE);
        Log.e("idiii", "ddd" + id);
        String title = binding.etTitle.getText().toString().trim();
        String body = binding.etBody.getText().toString().trim();

        String userId = sharedPreferences.getString(MySharedPrefernceKey.ID);
        postAddViewModel.updatePost(title, body, id).observe(PostAddActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.progressbar.setVisibility(View.GONE);

                Intent intent = new Intent(PostAddActivity.this, MyPostActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initi() {
        categprYS = new ArrayList<>();
        listC = new ArrayList<>();
        listCM = new ArrayList<>();
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        id = intent.getStringExtra("id");
        Log.e("idiii", "" + id);
        sharedPreferences = new MySharedPreferences(PostAddActivity.this);
    }


    private void selectCategory() {


        Dialog dialog = new Dialog(PostAddActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.frame_help);
        recylerView = dialog.findViewById(R.id.recycleIdcc);
        dialog.findViewById(R.id.progressbar11).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.tv_cencel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listC.clear();
                dialog.dismiss();
            }
        });
        postAddViewModel.getCategory().observe(PostAddActivity.this, new Observer<ArrayList<MyCategory>>() {
            @Override
            public void onChanged(ArrayList<MyCategory> myCategories) {
                dialog.findViewById(R.id.progressbar11).setVisibility(View.GONE);
                listCM.clear();
                listCM = myCategories;
                recylerView.setLayoutManager(new LinearLayoutManager(PostAddActivity.this));
                categoryAdapter = new CategoryAdapter(PostAddActivity.this, listCM);
                recylerView.setAdapter(categoryAdapter);
            }
        });


        dialog.show();


        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categprYS.clear();
                for (int i = 0; i < listCM.size(); i++) {
                    MyCategory mYCategory = listCM.get(i);
                    if (mYCategory.isCHeck()) {
                        categprYS.add(mYCategory.getName());
                        Log.e("check1", "..................................." + mYCategory.getName());
                    }
                }
                dialog.dismiss();

            }
        });

    }

}