package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartgrocer.R;
import com.example.smartgrocer.models.FruitModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFruitsActivity extends AppCompatActivity {

    EditText etAddName,etAddUnit,etAddPrice,etAddQuantity;
    Button btAddImage,btAddProduct,btAddCancel;
    ImageView iv;
    Uri imgPath;
    DatabaseReference dbref = null;
    StorageReference store = null,gStore = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruits);

        etAddName = findViewById(R.id.etAddName);
        etAddUnit = findViewById(R.id.etAddUnit);
        etAddPrice = findViewById(R.id.etAddPrice);
        etAddQuantity = findViewById(R.id.etAddQuantity);
        btAddImage = findViewById(R.id.btAddImageFruit);
        btAddProduct = findViewById(R.id.btAddGrocery);
        btAddCancel = findViewById(R.id.btAddCancel);
        iv = findViewById(R.id.ImageAdd);

        btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,2005);
            }
        });

        btAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etAddName.getText().toString();
                String unit = etAddUnit.getText().toString();
                int price = Integer.parseInt(etAddPrice.getText().toString());
                int qty = Integer.parseInt(etAddQuantity.getText().toString());

                store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Fruits");
                dbref = FirebaseDatabase.getInstance().getReference("Fruits");

                String id = dbref.push().getKey();
                gStore = store.child(name);

                gStore.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        gStore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FruitModel fa = new FruitModel(id,uri.toString(),name,price,qty,unit);
                                dbref.child(id).setValue(fa);

                                Toast.makeText(AddFruitsActivity.this, "sUCCESSFULLY ADDED", Toast.LENGTH_SHORT).show();

                                etAddName.setText("");
                                etAddPrice.setText("");
                                etAddUnit.setText("");
                                etAddQuantity.setText("");

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFruitsActivity.this, "FAILED TO UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onActivityResult(int reqCode,int resCode,Intent Data)
    {
        super.onActivityResult(reqCode,reqCode,Data);

        if (reqCode == 2005 && resCode == RESULT_OK)
        {
            imgPath = Data.getData();
            iv.setImageURI(imgPath);
        }

    }

}