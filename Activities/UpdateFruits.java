package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.smartgrocer.adapters.GlobalList3;
import com.example.smartgrocer.models.FruitModel;
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

public class UpdateFruits extends AppCompatActivity {

    EditText etUpdName, etUpdUnit, etUpdPrice, etUpdQuantity;
    Button btUpdImage, btUpdGrocery, btUpdCancel;
    ImageView imgView;
    Uri imgPath;
    DatabaseReference dbRef;
    StorageReference store;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fruits);


        etUpdName = findViewById(R.id.etAddName);
        etUpdPrice = findViewById(R.id.etAddPrice);
        etUpdUnit = findViewById(R.id.etAddUnit);
        etUpdQuantity = findViewById(R.id.etAddQuantity);
        btUpdGrocery = findViewById(R.id.btAddGrocery);
        btUpdCancel = findViewById(R.id.btAddCancel);
        btUpdImage = findViewById(R.id.btAddImage);
        imgView = findViewById(R.id.ImageAdd);

        position = getIntent().getIntExtra("position",0);

        etUpdName.setText(GlobalList3.flist.get(position).getName());
        etUpdUnit.setText(GlobalList3.flist.get(position).getUnit());
        etUpdPrice.setText("" + GlobalList3.flist.get(position).getPrice());
        etUpdQuantity.setText("" + GlobalList3.flist.get(position).getQty());

        Glide.with(UpdateFruits.this)
                .load(GlobalList3.flist.get(position).getImgUrl())
                .into(imgView);

        dbRef = FirebaseDatabase.getInstance().getReference("Fruits");
        store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Fruits");

        btUpdGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFruits();
            }
        });

        btUpdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, 2005);
            }
        });

    }

    protected void onActivityResult(int reqCode, int result, Intent Data) {
        super.onActivityResult(reqCode, reqCode, Data);

        if (reqCode == 2005 && result == RESULT_OK && Data != null) {
            imgPath = Data.getData();
            imgView.setImageURI(imgPath);
        }

    }


    public void updateFruits() {
        String updateName = etUpdName.getText().toString();
        String updateUnit = etUpdUnit.getText().toString();
        int updatePrice = Integer.parseInt(etUpdPrice.getText().toString());
        int updateQty = Integer.parseInt(etUpdQuantity.getText().toString());

        if (imgPath != null) {
            store.putFile(imgPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("imgUrl").setValue(uri.toString());

                                updateOtherFields(dbRef,updateName,updateUnit,updatePrice,updateQty);
                            }
                        });
                    } else {
                        Toast.makeText(UpdateFruits.this, "FAILED TO UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {
             updateOtherFields(dbRef,updateName,updateUnit,updatePrice,updateQty);
        }

    }

    public void updateOtherFields(DatabaseReference dbRef, String updateName, String updateUnit, int updatePrice, int updateQty) {
        dbRef = dbRef.child(GlobalList3.flist.get(position).getId());
        dbRef.child("name").setValue(updateName);
        dbRef.child("unit").setValue(updateUnit);
        dbRef.child("price").setValue(updatePrice);
        dbRef.child("qty").setValue(updateQty);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    FruitModel fa = snap.getValue(FruitModel.class);
                    GlobalList3.flist.add(fa);
                }

                Toast.makeText(UpdateFruits.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFruits.this, "FAILED TO UPDATE FRUIT", Toast.LENGTH_SHORT).show();
            }
        });

    }

}