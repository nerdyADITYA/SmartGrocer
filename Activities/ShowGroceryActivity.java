package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShowGroceryAdapter;
import com.example.smartgrocer.models.GroceryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowGroceryActivity extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbref = null;
    List<GroceryModel> gList = new ArrayList<GroceryModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grocery);

        lv = findViewById(R.id.lv);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference("Grocery");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel gr = snap.getValue(GroceryModel.class);
                    gList.add(gr);
                }

                ShowGroceryAdapter ca = new ShowGroceryAdapter(getApplicationContext(),R.layout.design,gList);
                lv.setAdapter(ca);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}