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

import com.example.smartgrocer.R;
import com.example.smartgrocer.models.DairyModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddDairyActivity extends AppCompatActivity {

    EditText etAddName,etAddUnit,etAddPrice,etAddQuantity;
    Button btAddImage,btAddProduct,btAddCancel;
    ImageView iv;
    Uri imgPath;
    DatabaseReference dbRef = null;
    StorageReference store = null, gStore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dairy_model);

        etAddName = findViewById(R.id.etAddName);
        etAddUnit = findViewById(R.id.etAddUnit);
        etAddPrice = findViewById(R.id.etAddPrice);
        etAddQuantity = findViewById(R.id.etAddQuantity);
        btAddImage = findViewById(R.id.btAddImage);
        btAddCancel = findViewById(R.id.btAddCancel);
        btAddProduct = findViewById(R.id.btAddGrocery);
        iv = findViewById(R.id.ImageAdd);

        btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //  THIS IS TO OPEN GALLERY
                startActivityForResult(gallery,69);
            }
        });

        btAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etAddName.getText().toString();
                String unit = etAddUnit.getText().toString();
                int price = Integer.parseInt(etAddPrice.getText().toString());
                int qty = Integer.parseInt(etAddQuantity.getText().toString());

                store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Dairy");
                dbRef = FirebaseDatabase.getInstance().getReference("Dairy");

                String Id = dbRef.push().getKey();
                gStore = store.child(name);

                gStore.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        gStore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DairyModel da = new DairyModel(Id,uri.toString(),name,price,qty,unit);
                                dbRef.child(Id).setValue(da);

                                Toast.makeText(AddDairyActivity.this, "SUCCESSFULLY ADDED !!!", Toast.LENGTH_SHORT).show();

                                etAddName.setText("");
                                etAddUnit.setText("");
                                etAddPrice.setText("");
                                etAddQuantity.setText("");

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddDairyActivity.this, "FAILED TO UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void onActivityResult(int reqCode,int resCode,Intent Data)
    {
        super.onActivityResult(reqCode,resCode,Data);

        if (reqCode == 69 && resCode == RESULT_OK)
        {
            imgPath = Data.getData();
            iv.setImageURI(imgPath);
        }

    }

}