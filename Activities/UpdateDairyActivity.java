package com.example.smartgrocer.Activities;

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

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalList2;
import com.example.smartgrocer.models.DairyModel;
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

public class UpdateDairyActivity extends Activity {

    EditText etUpdName,etUpdUnit,etUpdPrice,etUpdQuantity;
    Button btUpdImage,btUpdGrocery,btUpdCancel;
    ImageView imgView;
    Uri imgPath;
    DatabaseReference dbRef;
    StorageReference store;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dairy);

        etUpdName = findViewById(R.id.etAddName);
        etUpdPrice = findViewById(R.id.etAddPrice);
        etUpdUnit = findViewById(R.id.etAddUnit);
        etUpdQuantity = findViewById(R.id.etAddQuantity);
        btUpdGrocery = findViewById(R.id.btAddGrocery);
        btUpdCancel = findViewById(R.id.btAddCancel);
        btUpdImage = findViewById(R.id.btAddImage);
        imgView = findViewById(R.id.ImageAdd);

        position = getIntent().getIntExtra("position",0);

        etUpdName.setText(GlobalList2.dlist.get(position).getName());
        etUpdPrice.setText(""+GlobalList2.dlist.get(position).getPrice());
        etUpdQuantity.setText(""+GlobalList2.dlist.get(position).getQty());
        etUpdUnit.setText(GlobalList2.dlist.get(position).getUnit());

            Glide.with(UpdateDairyActivity.this)
                    .load(GlobalList2.dlist.get(position).getImgUrl())
                    .into(imgView);

        dbRef = FirebaseDatabase.getInstance().getReference("Dairy");
        store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Dairy");

        btUpdGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGrocery();
            }
        });

        btUpdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
                        Toast.makeText(UpdateDairyActivity.this, "Failed to upload Image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            updateOtherFields(dbRef,updateName,updatePrice,updateQty,updateUnit);
        }

    }

    private void updateOtherFields(DatabaseReference dbRef,String updateName,int updatePrice,int updateQty,String updateUnit)
    {
        dbRef = dbRef.child(GlobalList2.dlist.get(position).getId());
        dbRef.child("name").setValue(updateName);
        dbRef.child("price").setValue(updatePrice);
        dbRef.child("qty").setValue(updateQty);
        dbRef.child("unit").setValue(updateUnit);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    DairyModel da = snap.getValue(DairyModel.class);
                    GlobalList2.dlist.add(da);
                }

                Toast.makeText(UpdateDairyActivity.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateDairyActivity.this, "FAILED TO UPDATE DAIRY", Toast.LENGTH_SHORT).show();
            }
        });

    }

}