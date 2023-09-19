package com.example.loginapiapplication.activity.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.service.Model.MyPost;
import com.example.loginapiapplication.activity.utils.CustomOnClickListener;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {
    List<MyPost> item;
    private Context context;
    private CustomOnClickListener listener;

    public MyPostAdapter(List<MyPost> item, Context context) {
        this.item = item;
        this.context = context;
    }

    public MyPostAdapter(List<MyPost> item, Context context, CustomOnClickListener listener) {
        this.item = item;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyPostAdapter.MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostAdapter.MyPostViewHolder(LayoutInflater.from(context).inflate(R.layout.mypostm, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.MyPostViewHolder holder, int position) {
        MyPost post = item.get(position);
        holder.body.setText(post.getBody());
        int po=position;
        holder.titlem.setText(post.getTitle());
        holder.name.setText(post.getUser().getName());
        Glide.with(context)
                .load("https://gweb-research-imagen.web.app/compositional/An%20oil%20painting%20of%20a%20British%20Shorthair%20cat%20wearing%20a%20cowboy%20hat%20and%20red%20shirt%20skateboarding%20on%20a%20beach./1_.jpeg")
                .into(holder.imgProfile);

        Glide.with(context)
                .load("https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D&w=1000&q=80")
                .into(holder.imageViewm);

        holder.itemView.findViewById(R.id.dots).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, holder.itemView.findViewById(R.id.dots));
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.updatep:
                                post.get_id();
                               listener.customOnClickListener(po,1,post.get_id());
                                break;

                            case R.id.deletepo:
                                listener.customOnClickListener(po,2,post.get_id());
                                break;

                            case R.id.cancel:
                                popupMenu.dismiss();
                                break;

                        }

                        return true;
                    }
                });
                popupMenu.show();


            }
        });


    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder {
        TextView name, body, titlem;
        ImageView imageViewm, imgProfile, imgDots;

        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);
            titlem = itemView.findViewById(R.id.tv_titleM);
            body = itemView.findViewById(R.id.tv_bodyM);
            imageViewm = itemView.findViewById(R.id.img_PostM);
            imgProfile = itemView.findViewById(R.id.profileM);
            imgDots = itemView.findViewById(R.id.dots);
            name = itemView.findViewById(R.id.tv_nameM);


        }
    }


}
