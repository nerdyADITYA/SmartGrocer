package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShowFruitAdapter;
import com.example.smartgrocer.models.FruitModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowFruitsActivity extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbref = null;
    List<FruitModel> fList = new ArrayList<FruitModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fruits);

        lv = findViewById(R.id.lv);

        dbref = FirebaseDatabase.getInstance().getReference("Fruits");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    FruitModel fa = snap.getValue(FruitModel.class);
                    fList.add(fa);
                }

                ShowFruitAdapter fd = new ShowFruitAdapter(getApplicationContext(),R.layout.design,fList);
                lv.setAdapter(fd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}