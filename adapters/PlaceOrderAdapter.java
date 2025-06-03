package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.smartgrocer.R;
import com.example.smartgrocer.models.CartModel;

import org.w3c.dom.Text;

import java.util.List;

public class PlaceOrderAdapter extends ArrayAdapter<CartModel>
{

    Context context;
    List<CartModel> list;
    int source;
    TextView tvTotalPrice;
    int sum = 0;

    public PlaceOrderAdapter(@NonNull Context context, int source, List<CartModel> list,TextView tvTotalPrice) {
        super(context, source,list);
        this.context = context;
        this.list = list;
        this.source = source;
        this.tvTotalPrice = tvTotalPrice;
    }


    public View getView(final int position, View ContentViewView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(source,null,false);

        TextView product = view.findViewById(R.id.TVProduct);
        TextView price = view.findViewById(R.id.TVPrice);
        TextView qty = view.findViewById(R.id.TVQty);
        TextView amount = view.findViewById(R.id.TVAmount);

        CartModel cm = list.get(position);

        product.setText(list.get(position).getName());
        price.setText(""+list.get(position).getPrice());
        qty.setText(""+list.get(position).getQty());

        int Price = Integer.parseInt(""+list.get(position).getPrice());
        int Qty = Integer.parseInt(""+list.get(position).getQty());
        int total = Price * Qty;

        amount.setText(""+total);

        sum = sum + total;
        tvTotalPrice.setText("Total: ₹"+sum);

        return view;

    }
}
