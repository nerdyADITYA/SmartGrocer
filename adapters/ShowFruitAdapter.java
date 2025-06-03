package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.FruitModel;

import java.util.List;

public class ShowFruitAdapter extends ArrayAdapter<FruitModel> {

    Context context;
    int source;
    List<FruitModel> list;

    public ShowFruitAdapter(@NonNull Context context, int source, List<FruitModel> list) {
        super(context, source,list);
        this.context = context;
        this.source = source;
        this.list = list;
    }

    public View getView(final int position, View ContenetViewView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(source, null, false);

        ImageView iv = view.findViewById(R.id.imageView);
        TextView name = view.findViewById(R.id.textViewName);
        TextView price = view.findViewById(R.id.textViewPrice);
        TextView unit = view.findViewById(R.id.textviewUnit);
        TextView qty = view.findViewById(R.id.textviewQty);

        FruitModel fa = list.get(position);

        name.setText(fa.getName());
        price.setText("Price: " + fa.getPrice());
        qty.setText("Qty: " + fa.getQty());
        unit.setText("Unit: " + fa.getUnit());

        Glide.with(context)
                .load(fa.getImgUrl())
                .override(800, 400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        return view;

    }

}
