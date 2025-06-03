package com.example.smartgrocer.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartgrocer.Activities.UpdateFruits;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.FruitModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UpdateDelFruitsAdapter extends ArrayAdapter<FruitModel>
{

    Context cont;
    int source;
    ArrayList<FruitModel> list;

    public UpdateDelFruitsAdapter(@NonNull Context cont, int source, ArrayList<FruitModel> list) {
        super(cont, source,list);
        this.cont = cont;
        this.source = source;
        this.list = list;
    }

    public View getView(final int position, View ContenetViewView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(cont);

        View view = inflater.inflate(source,null,false);

        ImageView iv = view.findViewById(R.id.ImageView);
        TextView tvName = view.findViewById(R.id.Name);
        TextView tvPrice = view.findViewById(R.id.Price);
        TextView tvQty = view.findViewById(R.id.Qty);
        TextView tvUnit = view.findViewById(R.id.Unit);
        Button btDel = view.findViewById(R.id.Deletebtn);
        Button btUpd = view.findViewById(R.id.Updatebtn);

        FruitModel fa = list.get(position);

        tvName.setText(fa.getName());
        tvPrice.setText("Price: "+fa.getPrice());
        tvQty.setText("Qty: "+fa.getQty());
        tvUnit.setText("Unit: "+fa.getUnit());

        Glide.with(cont)
                .load(fa.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });

        btUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cont instanceof Activity) {
                    Intent i = new Intent(cont, UpdateFruits.class);
                    FruitModel selectedFruits = list.get(position);
                    i.putExtra("position",position);
                    cont.startActivity(i);
                }
                else
                {
                    Toast.makeText(cont, "Unable to start activity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    void showDialog(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(cont); // Use 'context' directly

        alert.setTitle("ARE YOU SURE YOU WANT TO DELETE ?");
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle NO button click
            }
        });

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletefromFirebaseStorage(position);
            }
        });

        // Create the AlertDialog instance
        AlertDialog alertDialog = alert.create();

        // Show the AlertDialog using the context from the adapter
        alertDialog.show();
    }





//    void showDialog(int position)
//    {
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//        alert.setTitle("ARE YOU SURE YOU WANT TO DELETE ?");
//
//        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                deletefromFirebaseStorage(position);
//            }
//        });
//
//        AlertDialog al = alert.create();
//        alert.show();
//
//    }




    public void deletefromFirebaseStorage(final int position)
    {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Fruits");
        FruitModel fruitItem = list.get(position);
        final String itemId = fruitItem.getId();
        final String imgUrl = fruitItem.getImgUrl();

        Query delQ = dbRef.child(itemId);

        delQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    snapshot.getRef().removeValue();

                    StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl);
                    storeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(cont, "SUCCESSFULLY DELETED !!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(cont, "ITEM NOT FOUND IN DATABASE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(cont, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
