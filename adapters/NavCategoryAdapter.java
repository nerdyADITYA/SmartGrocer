package com.example.smartgrocer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.Activities.ShopDairy;
import com.example.smartgrocer.Activities.ShopFruits;
import com.example.smartgrocer.Activities.ShopGrocery;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.NavCategoryModel;

import java.security.PublicKey;
import java.util.List;

public class NavCategoryAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    List<NavCategoryModel> list;

    public NavCategoryAdapter(Context context,List<NavCategoryModel> list)
    {
        this.context = context;
        this.list = list;
    }

    public int getCount()
    {
        return list.size();
    }

    public Object getItem(int position)
    {
        return list.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position,View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.nav_cat_item,parent,false);
        }

        ImageView iv = convertView.findViewById(R.id.cat_nav_img);
        TextView name = convertView.findViewById(R.id.nav_cat_name);
        TextView description = convertView.findViewById(R.id.nav_cat_description);
        TextView discount = convertView.findViewById(R.id.nav_cat_discount);

        NavCategoryModel item = list.get(position);

        Glide.with(context).load(item.getImgPath()).into(iv);
        name.setText(item.getName());
        description.setText(item.getDescription());
        discount.setText(item.getDiscount());

        name.setOnClickListener(this);

        return convertView;

    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;

        if(tv.getText().equals("Grocery"))
        {
            Intent i = new Intent(context, ShopGrocery.class);
            context.startActivity(i);
        }
        else if (tv.getText().equals("Dairy"))
        {
            Intent i = new Intent(context, ShopDairy.class);
            context.startActivity(i);
        }
        else if (tv.getText().equals("Fruits"))
        {
            Intent i = new Intent(context, ShopFruits.class);
            context.startActivity(i);
        }

    }
}
