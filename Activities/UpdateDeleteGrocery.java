package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalList;
import com.example.smartgrocer.adapters.UpdateDeleteGroceryAdapter;
import com.example.smartgrocer.models.GroceryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteGrocery extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbRef = null;
    ArrayList<GroceryModel> gList = new ArrayList<GroceryModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_grocery_model);

        lv = findViewById(R.id.lv);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("Grocery");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel grocery = snap.getValue(GroceryModel.class);
                    gList.add(grocery);
                }

                UpdateDeleteGroceryAdapter cd = new UpdateDeleteGroceryAdapter(UpdateDeleteGrocery.this,R.layout.design2,gList);
                lv.setAdapter(cd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}