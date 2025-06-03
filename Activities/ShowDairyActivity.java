package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.ShowDairyAdapter;
import com.example.smartgrocer.models.DairyModel;
import com.example.smartgrocer.models.GroceryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDairyActivity extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbref = null;
    List<DairyModel> dList = new ArrayList  <DairyModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dairy);

        lv = findViewById(R.id.lv);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference("Dairy");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    DairyModel da = snap.getValue(DairyModel.class);
                    dList.add(da);
                }

                ShowDairyAdapter cd = new ShowDairyAdapter(getApplicationContext(),R.layout.design,dList);
                lv.setAdapter(cd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}