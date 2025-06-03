package com.example.smartgrocer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.Activities.ShopDairy;
import com.example.smartgrocer.Activities.ShopFruits;
import com.example.smartgrocer.Activities.ShopGrocery;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(categoryModelList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(categoryModelList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView catImg;
        TextView name;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg = itemView.findViewById(R.id.cat_img);
            name = itemView.findViewById(R.id.cat_name);

            name.setOnClickListener(this);

        }

//        public void bindData(CategoryModel category,int position)
//        {
//            Glide.with(context).load(category.getImg_url()).into(catImg);
//            name.setText(category.getName());
//            this.position = position;
//        }

        public void onClick(View v)
        {
            TextView tv = (TextView) v;

            if (tv.getText().equals("Milk")) {
                Intent intent = new Intent(context, ShopDairy.class);
                context.startActivity(intent);
            }
            else if (tv.getText().equals("Grocery"))
            {
                Intent i = new Intent(context, ShopGrocery.class);
                context.startActivity(i);
            }
            else if (tv.getText().equals("Fruits"))
            {
                Intent i = new Intent(context, ShopFruits.class);
                context.startActivity(i);
            }

        }
    }
}
