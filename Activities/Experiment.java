package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartgrocer.R;
import com.example.smartgrocer.models.NavCategoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Experiment extends AppCompatActivity {

    EditText etName,etDescription,etDiscount;
    Button btAdd,btCancel,btAddImage;
    ImageView iv;
    Uri imgPath;
    DatabaseReference dbref = null;
    StorageReference store = null,eStore = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        etName = findViewById(R.id.etAddName);
        etDescription = findViewById(R.id.etAddUnit);
        etDiscount = findViewById(R.id.etAddPrice);
        btAdd = findViewById(R.id.btAddGrocery);
        btCancel = findViewById(R.id.btAddCancel);
        btAddImage = findViewById(R.id.btAddImage);
        iv = findViewById(R.id.ImageAdd);

        btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //  THIS IS TO OPEN GALLERY
                startActivityForResult(gallery,69);
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String discount = etDiscount.getText().toString();

                store = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smart-grocer-8e2c0.appspot.com/Nav Category");
                dbref = FirebaseDatabase.getInstance().getReference("nav_category");

                String id = dbref.push().getKey();
                eStore = store.child(name);

                eStore.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        eStore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                NavCategoryModel cat = new NavCategoryModel(id,name,description,discount,uri.toString());
                                dbref.child(id).setValue(cat);
                                Toast.makeText(Experiment.this, "Added successfully !!!", Toast.LENGTH_SHORT).show();

                                etName.setText("");
                                etDescription.setText("");
                                etDiscount.setText("");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Experiment.this, "FAILED TO UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

    }

    public void onActivityResult(int reqCode , int resCode, Intent Data) {
        super.onActivityResult(reqCode, resCode, Data);

        if (reqCode == 69 && resCode == RESULT_OK)
        {
            imgPath = Data.getData();
            iv.setImageURI(imgPath);
        }

    }

}