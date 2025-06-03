package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShopDairyAdapter;
import com.example.smartgrocer.adapters.ShopGroceryAdapter;
import com.example.smartgrocer.models.DairyModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopDairy extends AppCompatActivity implements ShopDairyAdapter.OnQuantityChangeListener,QuantityChangeListener {

    ListView lv;
    int total = 0;
    TextView totalPrice;
    Button btCheckout;
    DatabaseReference dbRef;
    List<DairyModel> dList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_dairy);

        lv = findViewById(R.id.lv);
        totalPrice = findViewById(R.id.tvTotalPrice);
        btCheckout = findViewById(R.id.btCheckout);

        total = 0;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("Dairy");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    DairyModel da = snap.getValue(DairyModel.class);
                    dList.add(da);
                }

                ShopDairyAdapter cd = new ShopDairyAdapter(ShopDairy.this,R.layout.design3,dList,totalPrice,btCheckout);
                cd.setOnQuantityChangeListener((ShopDairyAdapter.OnQuantityChangeListener) ShopDairy.this);
                lv.setAdapter(cd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onQuantityChange(int position,int quantity)
    {
        DairyModel da = dList.get(position);

        int price = da.getPrice();
        int totalPriceOfItem = quantity * price;

        da.setQty(quantity);

        total += totalPriceOfItem;
        updateTotalPrice();
    }

    public void updateTotalPrice()
    {
        totalPrice.setText(String.valueOf(total));
    }

}