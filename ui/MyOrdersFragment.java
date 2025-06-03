package com.example.smartgrocer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;
import com.example.smartgrocer.adapters.MyOrderAdapter;
import com.example.smartgrocer.models.BoughtModel;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class MyOrdersFragment extends Fragment {

    ListView lv;
    List<BoughtModel> bList = new ArrayList<BoughtModel>();
    DatabaseReference dbRef = null;


    public MyOrdersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_orders,container,false);

        lv = view.findViewById(R.id.OrderLv);

        dbRef = FirebaseDatabase.getInstance().getReference("MyOrder");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    BoughtModel ba = snap.getValue(BoughtModel.class);
                    if (ba.getMail().equals(GlobalMail.mail))
                    {
                        bList.add(ba);
                    }
                }
                MyOrderAdapter oa = new MyOrderAdapter(getActivity(),R.layout.design7,bList,GlobalMail.mail);
                lv.setAdapter(oa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}