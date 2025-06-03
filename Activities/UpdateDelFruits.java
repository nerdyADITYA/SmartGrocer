package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalList3;
import com.example.smartgrocer.adapters.UpdateDelFruitsAdapter;
import com.example.smartgrocer.models.FruitModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateDelFruits extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_del_fruits);

        lv = findViewById(R.id.lvfruit);

        dbRef = FirebaseDatabase.getInstance().getReference("Fruits");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GlobalList3.flist.clear();

                for (DataSnapshot snap: snapshot.getChildren())
                {
                    FruitModel fruit = snap.getValue(FruitModel.class);
                    GlobalList3.flist.add(fruit);
                }

                UpdateDelFruitsAdapter ud = new UpdateDelFruitsAdapter(UpdateDelFruits.this,R.layout.experiment,GlobalList3.flist);
                lv.setAdapter(ud);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}