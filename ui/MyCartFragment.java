package com.example.smartgrocer.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartgrocer.Activities.PlaceOrder;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;
import com.example.smartgrocer.adapters.MyCartAdapter;
import com.example.smartgrocer.models.CartModel;
import com.example.smartgrocer.models.GroceryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCartFragment extends Fragment {

    ListView lv;
//    List<GroceryModel> cartlist = new ArrayList<GroceryModel>();

    List<CartModel> catList = new ArrayList<CartModel>();
    Button btClear,btOrder;
    DatabaseReference dbRef = null;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        String json = mPrefs.getString("MyObject", "");
//        MyObject obj = gson.fromJson(json, MyObject.class);


        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        lv = view.findViewById(R.id.lv);
        btClear =view.findViewById(R.id.btClear);
        btOrder = view.findViewById(R.id.btOrder);

        dbRef = FirebaseDatabase.getInstance().getReference("Cart");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    CartModel ca = snap.getValue(CartModel.class);
                    if(ca.getMail().equals(GlobalMail.mail)) {
                        catList.add(ca);
                    }
                }

                MyCartAdapter cd = new MyCartAdapter(getActivity(),R.layout.design5,catList, GlobalMail.mail);
                lv.setAdapter(cd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("Cart");

                catRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error != null)
                        {
                            Toast.makeText(getContext(), "FAILED TO CLEAR CART: "+error, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "CART CLEARED !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlaceOrder.class);
                startActivity(i);
            }
        });



















//        SharedPreferences shadcount = getContext().getSharedPreferences("Count",Context.MODE_PRIVATE);
//        int count = shadcount.getInt("countme",0);
//
//        SharedPreferences shad = getContext().getSharedPreferences("Cart", Context.MODE_PRIVATE);
//        Map<String, String> map = (Map<String, String>) shad.getAll();
//
//
//        List<String> jlist = new ArrayList<String>(map.values());
//
////        Toast.makeText(getContext(),""+ jlist.size(),Toast.LENGTH_LONG).show();
//
//        Gson gson = new Gson();
//        String names="";
//        int c=1;
//
//        for(int i=0;i<jlist.size();i++)
//        {
//            String json = shad.getString(""+c,"");
//            c++;
//            GroceryModel gm = gson.fromJson(json,GroceryModel.class);
//            cartlist.add(gm);
//
// //           names = names+gm.getName()+"\n";
//        }
// //       Toast.makeText(getContext()," SIZE = "+cartlist.size(),Toast.LENGTH_LONG).show();
//
//        adapter = new MyCartAdapter(getActivity(),cartlist,shad);
//        lv.setAdapter(adapter);
//
//        btClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences shad = getContext().getSharedPreferences("Cart",Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = shad.edit();
//                edit.clear();
//
//                SharedPreferences shadcount = getContext().getSharedPreferences("Count",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editt = shadcount.edit();
//                editt.putInt("countme",0);
//                editt.commit();
//
//                edit.commit();
//
//
//                adapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "CART CLEARED", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

}