package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalList2;
import com.example.smartgrocer.adapters.UpdateDelDairyAdapter;
import com.example.smartgrocer.models.DairyModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataOutput;
import java.util.ArrayList;

public class UpdateDelDairy extends AppCompatActivity {

    ListView lv;
    DatabaseReference dbref = null;
    ArrayList<DairyModel> dList = new ArrayList<DairyModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_del_dairy);

        lv = findViewById(R.id.lv);

        dbref = FirebaseDatabase.getInstance().getReference("Dairy");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GlobalList2.dlist.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    DairyModel dairy = snap.getValue(DairyModel.class);
                    GlobalList2.dlist.add(dairy);
                }

                UpdateDelDairyAdapter da = new UpdateDelDairyAdapter(UpdateDelDairy.this,R.layout.design2,GlobalList2.dlist);
                lv.setAdapter(da);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}