package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.GroceryModel;

import java.util.List;

public class ShowGroceryAdapter extends ArrayAdapter<GroceryModel> {

    Context context;
    int source;
    List<GroceryModel> list;

    public ShowGroceryAdapter(@NonNull Context context, int source, List<GroceryModel> list) {
        super(context, source,list);
        this.context = context;
        this.source = source;
        this.list = list;
    }

    public View getView(final int position, View ContentViewView, ViewGroup parent)
    {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(source,null,false);

        ImageView iv = view.findViewById(R.id.imageView);
        TextView name = view.findViewById(R.id.textViewName);
        TextView price = view.findViewById(R.id.textViewPrice);
        TextView qty = view.findViewById(R.id.textviewQty);
        TextView unit = view.findViewById(R.id.textviewUnit);

        GroceryModel gr = list.get(position);

        name.setText(gr.getName());
        price.setText("Price: "+gr.getPrice());
        qty.setText("Qty: "+gr.getQty());
        unit.setText("Unit: "+gr.getUnit());

        Glide.with(context)
                .load(gr.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        return view;

    }

}
