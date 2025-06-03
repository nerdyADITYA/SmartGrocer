package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    TextView signIn;
    EditText etName,etEmail,etPass,etPhone,etAddress;
    Button SignUpbtn;
    DatabaseReference dbRef = null;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signIn = findViewById(R.id.sign_in);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        SignUpbtn = findViewById(R.id.signUp_btn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(RegistrationActivity.this, "PLEASE FILL ALL THE  VALID DETAILS", Toast.LENGTH_SHORT).show();
                }
                if (pass.length() < 6)
                {
                    Toast.makeText(RegistrationActivity.this, "Password lenght must be greater than 6 characters!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    saveUserData(email,pass,name,phone,address);
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);


//                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful())
//                            {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                saveUserData(email,pass,name,user.getUid());
//                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                                startActivity(i);
//                            }
//                            else
//                            {
//                                Toast.makeText(RegistrationActivity.this, "AUTHENTICATION FAILED !!!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }
        });
    }


//    public void saveUserData(String email,String pass,String name,String userId)
//    {
//        dbRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//        dbRef.child("email").setValue(email);
//        dbRef.child("password").setValue(pass);
//        dbRef.child("name").setValue(name);
//
//        Toast.makeText(this, "REGISTERED SUCCESSFULLY !!!", Toast.LENGTH_SHORT).show();
//    }


    public void saveUserData(String email, String pass,String name,String phone,String address)
    {
        SharedPreferences shad = getSharedPreferences("myMailPass", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shad.edit();


        edit.putString("uEmail",email);
        edit.putString("uPass",pass);
        edit.putString("uName",name);
        edit.putString("uPhone",phone);
        edit.putString("uAddress",address);
        edit.commit();


        Toast.makeText(this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

    }


}