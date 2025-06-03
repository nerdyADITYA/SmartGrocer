package com.example.smartgrocer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.CheckOutAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckOutModel extends AppCompatActivity {

    ListView lv;
    Button btnCheckout;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_model);


        lv = findViewById(R.id.lv);
        btnCheckout = findViewById(R.id.btnCheckout);

        Intent intent = getIntent();
        ArrayList<String> itemName = intent.getStringArrayListExtra("itemName");
        ArrayList<String> itemQty = intent.getStringArrayListExtra("itemQty");
        ArrayList<String> itemPrice = intent.getStringArrayListExtra("itemPrice");

        ArrayList<String> tName = new ArrayList<String>();
        ArrayList<String> tQty = new ArrayList<String>();
        ArrayList<String> tPrice = new ArrayList<String>();

        for(int i=0;i<itemName.size();i++)
        {
            if(Integer.parseInt(itemQty.get(i)) > 0)
            {
                tName.add(itemName.get(i));
                tQty.add(itemQty.get(i));
                tPrice.add(itemPrice.get(i));

            }
        }


        List<String> itemList = new ArrayList<>();

        String item = itemName + " - Quantity: " + itemQty + ", Price: " + itemPrice;
        itemList.add(item);

        CheckOutAdapter adapter = new CheckOutAdapter(this, tName,tQty,tPrice,btnCheckout);
        lv.setAdapter(adapter);

    }
}