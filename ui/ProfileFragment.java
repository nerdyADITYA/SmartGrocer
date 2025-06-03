package com.example.smartgrocer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartgrocer.Activities.LoginActivity;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    EditText etName,etEmail,etPhone,etAddress;
    ImageView iv;
    Button btUpdate,btLogout;


    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        etName = view.findViewById(R.id.profileName);
        etEmail = view.findViewById(R.id.profileEmail);
        etPhone = view.findViewById(R.id.profilePhoneNumber);
        etAddress = view.findViewById(R.id.profileAddress);
        iv = view.findViewById(R.id.profile_img);
        btUpdate = view.findViewById(R.id.update);
        btLogout = view.findViewById(R.id.logoutBtn);

        SharedPreferences shad = getContext().getSharedPreferences("myMailPass", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shad.edit();

        String email = shad.getString("uEmail","");
        String name = shad.getString("uName","");
        String address = shad.getString("uAddress","");
        String phone = shad.getString("uPhone","");

        etName.setText(name);
        etEmail.setText(email);
        etAddress.setText(address);
        etPhone.setText(phone);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("uPhone",etPhone.getText().toString());
                edit.putString("uAddress",etAddress.getText().toString());
                edit.apply();
//
//                GlobalMail.address = address;
//                GlobalMail.phone = phone;
//                GlobalMail.name = name;
//                GlobalMail.mail = email;

                Toast.makeText(getContext(), "Your address is "+address+"phone "+ phone, Toast.LENGTH_SHORT).show();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i,6969);
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getContext().getSharedPreferences("myMailPass", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("keepLoggedIn",false);

                editor.commit();

                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6969 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}