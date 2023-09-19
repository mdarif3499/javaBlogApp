package com.example.loginapiapplication.activity.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapiapplication.R;
import com.example.loginapiapplication.activity.service.Model.MyCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<MyCategory> listItem;

    public CategoryAdapter(Context context, ArrayList<MyCategory> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {



            MyCategory item = listItem.get(position);
        holder.tv_name.setText(item.getName());
        if (item.isCHeck()){

            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCHeck()){
                    holder.checkBox.setChecked(false);
                    item.setCHeck(false);

                }else {
                    holder.checkBox.setChecked(true);
                    item.setCHeck(true);

                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        CheckBox checkBox;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_NameC);
            checkBox = itemView.findViewById(R.id.checkboxC);

        }
    }
}