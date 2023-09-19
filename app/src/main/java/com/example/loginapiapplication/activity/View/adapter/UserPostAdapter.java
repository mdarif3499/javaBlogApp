package com.example.loginapiapplication.activity.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.service.Model.User;
import com.example.loginapiapplication.activity.service.Model.MyPost;

import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.PostViewHolder> {

    List<MyPost> item;
    List<User> item1;
    private Context context;

    public UserPostAdapter(List<MyPost> item, List<User> item1, Context context) {
        this.item = item;
        this.item1 = item1;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.postlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


        MyPost post = item.get(position);
        holder.body.setText(post.getBody());
        holder.title.setText(post.getTitle());
        holder.nameP.setText(post.getUser().getName());

        Glide.with(context)
                .load("https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D&w=1000&q=80")
                .into(holder.imageView);


        Glide.with(context)
                .load("https://gweb-research-imagen.web.app/compositional/An%20oil%20painting%20of%20a%20British%20Shorthair%20cat%20wearing%20a%20cowboy%20hat%20and%20red%20shirt%20skateboarding%20on%20a%20beach./1_.jpeg")
                .into(holder.imgProfile);


    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title,body,nameP;
        ImageView imageView,imgProfile;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            nameP=itemView.findViewById(R.id.tv_nameM);
            body=itemView.findViewById(R.id.tv_bodyM);
            title=itemView.findViewById(R.id.tv_titleM);

            imgProfile=itemView.findViewById(R.id.profileId);


            imageView=itemView.findViewById(R.id.img_PostM);


        }
    }
}
