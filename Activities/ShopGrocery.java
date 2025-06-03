package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShopGroceryAdapter;
import com.example.smartgrocer.models.GroceryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopGrocery extends AppCompatActivity implements ShopGroceryAdapter.OnQuantityChangeListener,QuantityChangeListener {

    ListView lv;
    int total = 0;
    TextView totalPrice;
    Button btCheckout;
    DatabaseReference dbRef;
    List<GroceryModel> gList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_grocery);

        lv = findViewById(R.id.lv);
        totalPrice = findViewById(R.id.tvTotalPrice);
        btCheckout = findViewById(R.id.btCheckout);

        total = 0;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("Grocery");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel gr = snap.getValue(GroceryModel.class);
                    gList.add(gr);
                }

                ShopGroceryAdapter cd = new ShopGroceryAdapter(ShopGrocery.this,R.layout.design3,gList,totalPrice,btCheckout);
                cd.setOnQuantityChangeListener(ShopGrocery.this);
                lv.setAdapter(cd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onQuantityChange(int position, int quantity) {
        GroceryModel gr = gList.get(position);

        int price = gr.getPrice();

        int totalPriceOfItem = quantity * price;

        gr.setQty(quantity);

        total += totalPriceOfItem;

        updateTotalPrice();
    }

    public void updateTotalPrice()
    {
        totalPrice.setText(String.valueOf(total));
    }

}