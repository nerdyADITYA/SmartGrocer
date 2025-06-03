package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.Popularmodel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context;
    private List<Popularmodel> popularmodelList;

    public PopularAdapter(Context context, List<Popularmodel> popularmodelList) {
        this.context = context;
        this.popularmodelList = popularmodelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(popularmodelList.get(position).getImg_url()).into(holder.popImage);
        holder.name.setText(popularmodelList.get(position).getName());
        holder.discount.setText(popularmodelList.get(position).getDiscount());
        holder.rating.setText(popularmodelList.get(position).getRating());
        holder.description.setText(popularmodelList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return popularmodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImage;
        TextView name,description,rating,discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImage = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            rating = itemView.findViewById(R.id.pop_rating);
            discount = itemView.findViewById(R.id.pop_discount);



        }
    }
}
