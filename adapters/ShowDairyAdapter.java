package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CartModel;
import com.example.smartgrocer.models.DairyModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShowDairyAdapter extends ArrayAdapter<DairyModel> {

    Context context;
    int source;
    List<DairyModel> list;


    public ShowDairyAdapter(@NonNull Context context, int source, List<DairyModel> list) {
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
        Button btCart = view.findViewById(R.id.btCart);

        DairyModel da = list.get(position);

        name.setText(da.getName());
        price.setText("Price: "+da.getPrice()+"₹");
        qty.setText("Qty: "+da.getQty());
        unit.setText("Unit: "+da.getUnit());

        Glide.with(context)
                .load(da.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);



        return view;

    }

}
