package com.example.smartgrocer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartgrocer.R;

public class AdminModel extends AppCompatActivity {

    Button addGrocery,showGrocery,deleteGrocery,addDairy,showDairy,deleteDairy,addFruits,showFruits,deleteFruits,experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_model);

        addGrocery = findViewById(R.id.btAddGrocery);
        showGrocery = findViewById(R.id.btShowGrocery);
        deleteGrocery = findViewById(R.id.btDeleteGrocery);
        addDairy = findViewById(R.id.btAddDairy);
        showDairy = findViewById(R.id.btShowDairy);
        deleteDairy = findViewById(R.id.btDeleteDairy);
        addFruits = findViewById(R.id.btAddFruits);
        showFruits = findViewById(R.id.btShowFruits);
        deleteFruits = findViewById(R.id.btDeleteFruits);
        experiment = findViewById(R.id.btExperiment);

        addGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddGroceryActivity.class);
                startActivity(i);
            }
        });

        showGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowGroceryActivity.class);
                startActivity(i);
            }
        });

        deleteGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UpdateDeleteGrocery.class);
                startActivity(i);
            }
        });

        addDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddDairyActivity.class);
                startActivity(i);
            }
        });

        showDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowDairyActivity.class);
                startActivity(i);
            }
        });

        deleteDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UpdateDelDairy.class);
                startActivity(i);
            }
        });

        addFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddFruitsActivity.class);
                startActivity(i);
            }
        });

        showFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowFruitsActivity.class);
                startActivity(i);
            }
        });

        deleteFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UpdateDelFruits.class);
                startActivity(i);
            }
        });

        experiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Experiment.class);
                startActivity(i);
            }
        });

    }
}