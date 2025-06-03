package com.example.smartgrocer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.smartgrocer.Activities.UpdateDairyActivity;
import com.example.smartgrocer.R;
import com.example.smartgrocer.models.DairyModel;
import com.example.smartgrocer.models.GroceryModel;
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

public class UpdateDelDairyAdapter extends ArrayAdapter<DairyModel> {

    Context context;
    int source;
    ArrayList<DairyModel> list;

    public UpdateDelDairyAdapter(@NonNull Context context, int source, ArrayList<DairyModel> list) {
        super(context, source,list);
        this.context = context;
        this.source = source;
        this.list = list;
    }

    public View getView(final int position, View contentViewView, ViewGroup parent)
    {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(source,null,false);

        ImageView iv = view.findViewById(R.id.imageView);
        TextView tvName = view.findViewById(R.id.textViewName);
        TextView tvPrice = view.findViewById(R.id.textViewPrice);
        TextView tvQty = view.findViewById(R.id.textviewQty);
        TextView tvUnit = view.findViewById(R.id.textviewUnit);
        Button btDel = view.findViewById(R.id.deletebtn2);
        Button btUpd = view.findViewById(R.id.updatebtn2);

        DairyModel da = list.get(position);

        tvName.setText(da.getName());
        tvPrice.setText("Price: "+da.getPrice());
        tvQty.setText("Qty: "+da.getQty());
        tvUnit.setText("Unit: "+da.getUnit());

        Glide.with(context)
                .load(da.getImgUrl())
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
                Intent i = new Intent(context, UpdateDairyActivity.class);
                DairyModel selectedDairy = list.get(position);
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });


        return view;
    }


    void showDialog(int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("ARE YOU SURE YOU WANT TO DELETE ?");

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFromFirebaseStorage(position);
            }
        });

        AlertDialog al = alert.create();
        alert.show();

    }


    public void deleteFromFirebaseStorage(final int position)
    {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Dairy");
        DairyModel dairyItem = list.get(position);
        final String itemId = dairyItem.getId();
        final String imageUrl = dairyItem.getImgUrl();

        Query delQ = dbRef.child(itemId);

        delQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    snapshot.getRef().removeValue();

                    StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                    storeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "SUCCESSFULLY DELETED !!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(context, "ITEM NOT FOUND IN DATABASE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
