package com.example.smartgrocer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.Activities.CheckOutModel;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CartModel;
import com.example.smartgrocer.models.FruitModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopFruitsAdapter extends ArrayAdapter<FruitModel> {

    public interface OnQuantityChange
    {
        void onQuantityChange(int position,int quantity);
    }

    int source;
    Context context;
    List<FruitModel> list;
    TextView tvTotalPrice;
    Button checkOutButton;
    OnQuantityChange quantityChange;
    DatabaseReference dbRef;
    int sum = 0;
    List<String> itemName = new ArrayList<>();
    List<String> itemQty = new ArrayList<>();
    List<String> itemPrice = new ArrayList<>();
    int qty = 0;

    public ShopFruitsAdapter(@NonNull Context context, int source, List<FruitModel> list, TextView tvTotalPrice,Button checkOutButton) {
        super(context, source,list);
        this.source = source;
        this.context = context;
        this.list = list;
        this.tvTotalPrice = tvTotalPrice;
        this.checkOutButton = checkOutButton;

        for (FruitModel fruit : list)
        {
            fruit.setQty(0);
        }
        for (int i=0;i<list.size();i++)
        {
            itemName.add("");
            itemQty.add(""+0);
            itemPrice.add(""+0);
        }
    }

    public View getView(final int position, View ContentViewView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(source,null,false);

        ImageView iv = view.findViewById(R.id.imageViewFruit);
        TextView tvName = view.findViewById(R.id.tvFruitName);
        TextView tvPrice = view.findViewById(R.id.tvFruitPrice);
        TextView tvQuantity = view.findViewById(R.id.tvFruitqty);
        Button btAdd = view.findViewById(R.id.btAddFruit);
        Button btSub = view.findViewById(R.id.btSubFruit);
        Button btCart = view.findViewById(R.id.btCartFruit);

        FruitModel fa = list.get(position);

        tvName.setText(fa.getName());
        tvPrice.setText(""+fa.getPrice());
        tvQuantity.setText(""+itemQty.get(position));

        Glide.with(context)
                .load(fa.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(itemQty.get(position));
                qty++;
                tvQuantity.setText(""+qty);
                sum = sum + fa.getPrice();
                tvTotalPrice.setText(""+sum);
                itemName.set(position, fa.getName());
                itemPrice.set(position,""+fa.getPrice());
                itemQty.set(position,""+qty);

                FruitModel newfa = new FruitModel(fa.getId(),fa.getImgUrl(),fa.getName(),fa.getPrice(), fa.getQty(), fa.getUnit());

                list.set(position,newfa);

            }
        });

        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(itemQty.get(position));
                if (qty>0)
                {
                    qty--;
                    tvQuantity.setText(""+qty);
                    sum = sum - fa.getPrice();
                    tvTotalPrice.setText(""+sum);
                    itemName.set(position, fa.getName());
                    itemPrice.set(position,""+fa.getPrice());
                    itemQty.set(position,""+qty);

                    FruitModel newfa = new FruitModel(fa.getId(),fa.getImgUrl(),fa.getName(),fa.getPrice(), fa.getQty(), fa.getUnit());

                    list.set(position,newfa);

                }
            }
        });

        btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference("Cart");
                
                String cId = dbRef.push().getKey();
                
                String imgUrl = list.get(position).getImgUrl();
                String name = list.get(position).getName();
                int price = Integer.parseInt(""+list.get(position).getPrice());
                int qty = Integer.parseInt(""+itemQty.get(position));

                CartModel cm = new CartModel(cId,GlobalMail.mail,imgUrl,name,price,qty);
                
                dbRef.child(cId).setValue(cm);

                Toast.makeText(context, ""+name+" HAS BEEN ADDED TO CART", Toast.LENGTH_SHORT).show();
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckOutModel.class);
                intent.putStringArrayListExtra("itemName", (ArrayList<String>) itemName);
                intent.putStringArrayListExtra("itemQty", (ArrayList<String>) itemQty);
                intent.putStringArrayListExtra("itemPrice", (ArrayList<String>) itemPrice);
                context.startActivity(intent);
            }
        });

        return view;

    }

    public void setOnQuantityChange(ShopFruitsAdapter.OnQuantityChange listener)
    {
        this.quantityChange = (OnQuantityChange) listener;
    }

}
