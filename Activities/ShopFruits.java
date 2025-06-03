package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShopDairyAdapter;
import com.example.smartgrocer.adapters.ShopFruitsAdapter;
import com.example.smartgrocer.models.FruitModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFruits extends AppCompatActivity implements ShopFruitsAdapter.OnQuantityChange,QuantityChangeListener{

    ListView lv;
    int total = 0;
    TextView totalPrice;
    Button btCheckout;
    DatabaseReference dbRef;
    List<FruitModel> fList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_fruits);

        lv = findViewById(R.id.lvFruit);
        totalPrice = findViewById(R.id.tvTotalPriceFruit);
        btCheckout = findViewById(R.id.btCheckoutFruit);

        dbRef = FirebaseDatabase.getInstance().getReference("Fruits");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fList.clear();

                for (DataSnapshot snap: snapshot.getChildren())
                {
                    FruitModel fd = snap.getValue(FruitModel.class);
                    fList.add(fd);
                }

                ShopFruitsAdapter fa = new ShopFruitsAdapter(ShopFruits.this,R.layout.experiment2,fList,totalPrice,btCheckout);
                fa.setOnQuantityChange(ShopFruits.this::onQuantityChange);
                lv.setAdapter(fa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onQuantityChange(int position, int quantity) {
        FruitModel fd = fList.get(position);

        int price = fd.getPrice();
        int totalPriceOfItem = quantity * price;

        fd.setQty(quantity);

        total += totalPriceOfItem;

        totalPrice.setText(String.valueOf(total));
    }
}