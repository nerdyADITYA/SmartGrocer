package com.example.smartgrocer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.NavCategoryAdapter;
import com.example.smartgrocer.models.NavCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    ListView catList;
    List<NavCategoryModel> list;
    NavCategoryAdapter adapter;
    DatabaseReference dbref = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category,container,false);

        catList = root.findViewById(R.id.cat_listview);
        list = new ArrayList<>();
        adapter = new NavCategoryAdapter(getActivity(),list);
        catList.setAdapter(adapter);

        dbref = FirebaseDatabase.getInstance().getReference("nav_category");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    NavCategoryModel model = snap.getValue(NavCategoryModel.class);

                    if (model != null)
                    {
                        list.add(model);
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}