package com.example.smartgrocer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.smartgrocer.R;
import com.google.api.ResourceProto;

import java.util.ArrayList;

public class CheckOutAdapter extends ArrayAdapter<String> {

    private final Context context;

    private final ArrayList<String> itemName;
    private final ArrayList<String> itemQty;
    private final ArrayList<String> itemPrice;
    Button btnFinalPrice;

    public CheckOutAdapter(@NonNull Context context, ArrayList<String> itemName, ArrayList<String> itemQty, ArrayList<String> itemPrice, Button btnFinalPrice) {
        super(context, R.layout.design4,itemName);
        this.context = context;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
        this.btnFinalPrice = btnFinalPrice;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.design4,parent,false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView itemNameTextView = rowView.findViewById(R.id.itemName);
        TextView itemQtyTextView = rowView.findViewById(R.id.itemQty);
        TextView itemPriceTextView = rowView.findViewById(R.id.itemPrice);
        TextView totalPriceTextView = rowView.findViewById(R.id.totalPrice);

        String itemName = this.itemName.get(position);
        String itemQty = this.itemQty.get(position);
        String itemPrice = this.itemPrice.get(position);

        itemNameTextView.setText(itemName);
        itemQtyTextView.setText("Qty: "+itemQty);
        itemPriceTextView.setText("Price: ₹"+ itemPrice);

        int qty = Integer.parseInt(itemQty);
        int price = Integer.parseInt(itemPrice);
        int totalPrice = qty * price;


        totalPriceTextView.setText("Total: ₹"+totalPrice);

        int total = calculateTotalPrice();
        btnFinalPrice.setText("Pay now: ₹"+total);

        return rowView;
    }

    private int calculateTotalPrice()
    {
        int total = 0;
        for (int i = 0; i<itemPrice.size(); i++)
        {
            int qty = Integer.parseInt(itemQty.get(i));
            int price = Integer.parseInt(itemPrice.get(i));
            total += qty*price;
        }
        return total;
    }

}
