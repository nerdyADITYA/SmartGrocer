package com.example.smartgrocer.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalList;
import com.example.smartgrocer.adapters.ShowGroceryAdapter;
import com.example.smartgrocer.models.GroceryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class UpdateGrocery extends Activity {

    EditText etUpdName,etUpdUnit,etUpdPrice,etUpdQuantity;
    Button btUpdImage,btUpdGrocery,btUpdCancel;
    ImageView imgView;
    Uri imgPath;
    DatabaseReference dbRef;
    StorageReference store;
    ArrayList<GroceryModel> gList;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grocery_model);

        etUpdName = findViewById(R.id.etAddName);
        etUpdPrice = findViewById(R.id.etAddPrice);
        etUpdUnit = findViewById(R.id.etAddUnit);
        etUpdQuantity = findViewById(R.id.etAddQuantity);
        btUpdGrocery = findViewById(R.id.btAddGrocery);
        btUpdCancel = findViewById(R.id.btAddCancel);
        btUpdImage = findViewById(R.id.btAddImage);
        imgView = findViewById(R.id.ImageAdd);

        Intent i = getIntent();
        position = Integer.parseInt(i.getStringExtra("position"));
        gList = (ArrayList<GroceryModel>) i.getSerializableExtra("grocerylist");

        etUpdName.setText(GlobalList.glist.get(position).getName());
        etUpdPrice.setText(""+GlobalList.glist.get(position).getPrice());
        etUpdQuantity.setText(""+GlobalList.glist.get(position).getQty());
        etUpdUnit.setText(GlobalList.glist.get(position).getUnit());

                    Glide.with(UpdateGrocery.this)
                            .load(GlobalList.glist.get(position).getImgUrl())
                            .into(imgView);

        dbRef = FirebaseDatabase.getInstance().getReference("Grocery");
        store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Grocery");

        btUpdGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGrocery();
            }
        });

        btUpdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i,6969);
            }
        });

    }

    protected void onActivityResult(int requestCode,int result,Intent data)
    {
        super.onActivityResult(requestCode,result,data);

        if (requestCode == 6969 && result == RESULT_OK && data != null)
        {
            imgPath = data.getData();
            imgView.setImageURI(imgPath);
        }
    }

    public void updateGrocery()
    {
        String updateName = etUpdName.getText().toString();
        int updatePrice = Integer.parseInt(etUpdPrice.getText().toString());
        int updateQty = Integer.parseInt(etUpdQuantity.getText().toString());
        String updateUnit = etUpdUnit.getText().toString();

        if (imgPath != null)
        {
            store.putFile(imgPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("imgUrl").setValue(uri.toString());

                                updateOtherFields(dbRef,updateName,updatePrice,updateQty,updateUnit);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(UpdateGrocery.this, "Failed to upload Image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            updateOtherFields(dbRef,updateName,updateQty,updatePrice,updateUnit);
        }

    }

    private void updateOtherFields(DatabaseReference dbRef,String updateName,int updatePrice,int updateQty,String updateUnit)
    {
        dbRef = dbRef.child(GlobalList.glist.get(position).getgId());
        dbRef.child("name").setValue(updateName);
        dbRef.child("price").setValue(updatePrice);
        dbRef.child("qty").setValue(updateQty);
        dbRef.child("unit").setValue(updateUnit);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel gr = snap.getValue(GroceryModel.class);
                    GlobalList.glist.add(gr);
                }

                Toast.makeText(UpdateGrocery.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateGrocery.this, "FAILED TO UPDATE GROCERY", Toast.LENGTH_SHORT).show();
            }
        });

    }

}