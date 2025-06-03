package com.example.smartgrocer.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartgrocer.Activities.ShopDairy;
import com.example.smartgrocer.Activities.ShopFruits;
import com.example.smartgrocer.Activities.ShopGrocery;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.CategoryAdapter;
import com.example.smartgrocer.adapters.PopularAdapter;
import com.example.smartgrocer.adapters.RecommendedAdapter;
import com.example.smartgrocer.models.CategoryModel;
import com.example.smartgrocer.models.Popularmodel;
import com.example.smartgrocer.models.RecommendedModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView viewAllPopular;
    TextView viewAllExplore;
    TextView viewAllRecommended;

    // Popular Items
    RecyclerView popularRec,homeCatRec,homeRec;
    List<Popularmodel> popularmodelList;
    PopularAdapter popularAdapter;
    DatabaseReference dbref = null,catRef = null,recRef = null;

    //Home Category Items
    List<CategoryModel> categoryModelList;
    CategoryAdapter categoryAdapter;

    //Home Recommended items

    List<RecommendedModel> recList;
    RecommendedAdapter recAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        popularRec = view.findViewById(R.id.pop_rec);
        homeCatRec = view.findViewById(R.id.explore_rec);
        homeRec = view.findViewById(R.id.recommended_rec);

        viewAllPopular = view.findViewById(R.id.view_all_popular);
        viewAllExplore = view.findViewById(R.id.view_all_explore);
        viewAllRecommended = view.findViewById(R.id.view_all_recommended);

        viewAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ShopGrocery.class);
                startActivity(i);
            }
        });

        viewAllRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShopFruits.class);
                startActivity(i);
            }
        });

        viewAllExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShopDairy.class);
                startActivity(i);
            }
        });





        //HOME POPULAR VIEW

        dbref = FirebaseDatabase.getInstance().getReference("Popular Products");

        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularmodelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(),popularmodelList);
        popularRec.setAdapter(popularAdapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularmodelList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    Popularmodel model = snap.getValue(Popularmodel.class);
                    if (model != null)
                    {
                        popularmodelList.add(model);
                    }
                }
                popularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //HOME RECOMMENDED VIEW

        recRef = FirebaseDatabase.getInstance().getReference("Home Recommended");

        homeRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recList = new ArrayList<>();
        recAdapter = new RecommendedAdapter(getActivity(),recList);
        homeRec.setAdapter(recAdapter);

        recRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    RecommendedModel model = snap.getValue(RecommendedModel.class);
                    if (model != null)
                    {
                        recList.add(model);
                    }
                }
                recAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        // HOME CATEGORY VIEW

        catRef = FirebaseDatabase.getInstance().getReference("Home Category");

        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(),categoryModelList);
        homeCatRec.setAdapter(categoryAdapter);

        catRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryModelList.clear();

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    CategoryModel model = snap.getValue(CategoryModel.class);
                    if (model != null)
                    {
                        categoryModelList.add(model);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return view;
    }
}