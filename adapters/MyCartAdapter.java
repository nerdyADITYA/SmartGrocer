package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CartModel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyCartAdapter extends BaseAdapter {

    String mail;
    Context context;

    /*List<GroceryModel> list;*/
    List<CartModel> list;
//    SharedPreferences shad;


    public MyCartAdapter(Context context, int design5, List<CartModel> list, String mail) {
        this.context = context;
        this.list = list;
        this.mail = mail;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.design5,parent,false);
        }

        ImageView iv = convertView.findViewById(R.id.imageView);
        TextView tvName = convertView.findViewById(R.id.itemName);
        TextView tvPrice = convertView.findViewById(R.id.itemPrice);
        TextView tvQty = convertView.findViewById(R.id.itemQty);
        TextView tvTotalPrice = convertView.findViewById(R.id.itemtotalPrice);
        Button btRemove = convertView.findViewById(R.id.btRemove);

        CartModel item = list.get(position);

        if(item != null) {

            tvName.setText(list.get(position).getName());
            tvPrice.setText("Price: " + list.get(position).getPrice());
            tvQty.setText("Qty: " + list.get(position).getQty());
            Glide.with(context).load(item.getImgUrl()).into(iv);

            int price = Integer.parseInt("" + list.get(position).getPrice());
            int qty = Integer.parseInt("" + list.get(position).getQty());
            int total = price * qty;

            tvTotalPrice.setText("Total: " + total);

        }

        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromDataBase(position);
            }
        });

        return convertView;
    }

    private void removeFromDataBase(int position)
    {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Cart");

        String itemKey = list.get(position).getcId();

        dbRef.child(itemKey).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null)
                {
                    Toast.makeText(context, "ITEM REMOVED FROM THE CART", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
