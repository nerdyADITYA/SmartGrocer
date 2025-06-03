package com.example.smartgrocer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartgrocer.MainActivity;
import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    EditText etEmail,etPass;
    Button signIn;
    CheckBox checkBox;
    SharedPreferences shad = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.sign_up);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        signIn = findViewById(R.id.signIn_btn);
        checkBox = findViewById(R.id.checkBox);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });

        shad = getSharedPreferences("myMailPass", Context.MODE_PRIVATE);

        boolean keepLoggedIn = shad.getBoolean("keepLoggedIn",false);
        if (keepLoggedIn==true)
        {
            String storedEmail = shad.getString("uEmail","");
            String storedPass = shad.getString("uPass","");
            String storedPhone = shad.getString("uPhone","");
            String storedName = shad.getString("uname","");
            String storedAddress = shad.getString("uAddress","");

            if (!storedEmail.isEmpty() && !storedPass.isEmpty())
            {
                GlobalMail.mail = storedEmail;
                GlobalMail.phone = storedPhone;
                GlobalMail.name = storedName;
                GlobalMail.address = storedAddress;
                Toast.makeText(getApplicationContext(), "You are logged in as " + storedEmail + " Your password is " + storedPass, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    signInUser();
                }
                else
                {
                    signInAdmin();
                }
            }
        });

    }


    public void signInAdmin()
    {
        String adminEmail = "admin@gmail.com";
        String adminPass = "admin123";

        if (adminEmail.isEmpty() || adminPass.isEmpty())
        {
            Toast.makeText(this, "Please enter the valid details !!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), AdminModel.class);
            startActivity(i);
            Toast.makeText(this, "You have logged in as Admin", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInUser()
    {
        String storedEmail = shad.getString("uEmail","");
        String storedPass = shad.getString("uPass","");
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        if (email.equals(storedEmail) && password.equals(storedPass))
        {
            if (checkBox.isChecked())
            {
                SharedPreferences.Editor editor = shad.edit();
                editor.putBoolean("keepLoggedIn",true);
                editor.commit();
            }

            Toast.makeText(getApplicationContext(), "You are Logged in as " + storedEmail + "Your Password is " + storedPass, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "INVALID CREDENTIALS", Toast.LENGTH_LONG).show();
        }

    }
}