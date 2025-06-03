package com.example.smartgrocer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.Activities.CheckOutModel;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CartModel;
import com.example.smartgrocer.models.DairyModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShopDairyAdapter extends ArrayAdapter<DairyModel>
{

    public interface OnQuantityChangeListener
    {
        void onQuantityChange (int position,int quantity);
    }

    int source;
    Context cont;
    List<DairyModel> list;
    TextView tvTotalPrice;
    OnQuantityChangeListener quantityChangeListener;
    int sum = 0;
    DatabaseReference dbref;
    List<String> itemName = new ArrayList<>();
    List<String> itemQty = new ArrayList<>();
    List<String> itemPrice = new ArrayList<>();
    Button checkoutButton;
    int qty=0;

    public ShopDairyAdapter(Context cont,int source, List<DairyModel> list, TextView tvTotalPrice, Button checkoutButton)
    {
        super(cont,source,list);
        this.source = source;
        this.cont = cont;
        this.list = list;
        this.tvTotalPrice = tvTotalPrice;
        this.checkoutButton = checkoutButton;
        for (DairyModel dairy : list)
        {
            dairy.setQty(0);
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
        LayoutInflater inflater = LayoutInflater.from(cont);

        View view = inflater.inflate(source,null,false);

        ImageView iv = view.findViewById(R.id.imageView);
        TextView tvName = view.findViewById(R.id.textViewName);
        TextView tvPrice = view.findViewById(R.id.textViewPrice);
        TextView tvQuantity = view.findViewById(R.id.tvqty);
        Button btAdd = view.findViewById(R.id.btAdd);
        Button btSub = view.findViewById(R.id.btSub);
        Button btCart = view.findViewById(R.id.btCart);

        DairyModel da = list.get(position);

        tvName.setText(da.getName());
        tvPrice.setText(""+da.getPrice());
        tvQuantity.setText(""+itemQty.get(position));

        Glide.with(cont)
                .load(da.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference("Cart");

                String cId = dbref.push().getKey();

                String imgUrl = list.get(position).getImgUrl();
                String name = list.get(position).getName();
                int price = Integer.parseInt("" + list.get(position).getPrice());
                int qty = Integer.parseInt("" + itemQty.get(position));
//                String unit = list.get(position).getUnit();

                CartModel cm = new CartModel(cId, GlobalMail.mail, imgUrl, name, price, qty);

                dbref.child(cId).setValue(cm);

                Toast.makeText(cont, "" + name + " HAS BEEN ADDED TO CART", Toast.LENGTH_SHORT).show();
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
                    sum = sum - da.getPrice();
                    tvTotalPrice.setText(""+sum);
                    itemName.set(position,da.getName());
                    itemPrice.set(position,""+da.getPrice());
                    itemQty.set(position,""+qty);

                    DairyModel newDa = new DairyModel(da.getId(),da.getImgUrl(),da.getName(),da.getPrice(),da.getQty(),da.getUnit());

                    list.set(position,newDa);
                }
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(itemQty.get(position));
                qty++;
                tvQuantity.setText(""+qty);
                sum = sum + da.getPrice();
                tvTotalPrice.setText(""+sum);
                itemName.set(position,da.getName());
                itemPrice.set(position,""+da.getPrice());
                itemQty.set(position,""+qty);

                DairyModel newDa = new DairyModel(da.getId(),da.getImgUrl(),da.getName(),da.getPrice(),da.getQty(),da.getUnit());

                list.set(position,newDa);
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cont, CheckOutModel.class);
                intent.putStringArrayListExtra("itemName", (ArrayList<String>) itemName);
                intent.putStringArrayListExtra("itemQty", (ArrayList<String>) itemQty);
                intent.putStringArrayListExtra("itemPrice", (ArrayList<String>) itemPrice);
                cont.startActivity(intent);
            }
        });

        return view;

    }

    public void setOnQuantityChangeListener(ShopDairyAdapter.OnQuantityChangeListener listener) {
        this.quantityChangeListener = (OnQuantityChangeListener) listener;
    }

}
