package com.example.smartgrocer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartgrocer.R;
import com.example.smartgrocer.adapters.GlobalMail;
import com.example.smartgrocer.adapters.PlaceOrderAdapter;
import com.example.smartgrocer.models.BoughtModel;
import com.example.smartgrocer.models.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlaceOrder extends AppCompatActivity {

    Button btCheckout;
    TextView tvProduct,tvPrice,tvQty,tvAmount,tvTotalPrice;
    ListView lv;
    DatabaseReference dbRef;
    List<CartModel> cList = new ArrayList<CartModel>();
    List<BoughtModel> bList = new ArrayList<BoughtModel>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        tvProduct = findViewById(R.id.textviewProduct);
        tvAmount = findViewById(R.id.textViewAmount);
        tvQty = findViewById(R.id.textviewQty);
        tvPrice = findViewById(R.id.textviewPrice);
        btCheckout = findViewById(R.id.buttonCheckout);
        tvTotalPrice = findViewById(R.id.textViewTotalPrice);
        lv = findViewById(R.id.LV);

        dbRef = FirebaseDatabase.getInstance().getReference("Cart");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cList.clear();

                for(DataSnapshot snap : snapshot.getChildren())
                {
                    CartModel cm = snap.getValue(CartModel.class);
                    cList.add(cm);

                }

                PlaceOrderAdapter po = new PlaceOrderAdapter(getApplicationContext(),R.layout.design6,cList,tvTotalPrice);
                lv.setAdapter(po);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CreatePdf();
//                sendEmail();
                MyOrder();

            }
        });

    }

    public void CreatePdf()
    {
        //Inflate the layout
        View view = LayoutInflater.from(this).inflate(R.layout.activity_place_order,null);

        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            this.getDisplay().getRealMetrics(displayMetrics);
        }
        else this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels,View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels,View.MeasureSpec.EXACTLY));

        view.layout(0,0,displayMetrics.widthPixels,displayMetrics.heightPixels);
        PdfDocument document = new PdfDocument();

        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,1960,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        //CANVAS
        Canvas canvas = page.getCanvas();
        view.draw(canvas);

        //Finish the page
        document.finishPage(page);




//        View view = LayoutInflater.from(this).inflate(R.layout.activity_place_order,null);
//        ListView listView = view.findViewById(R.id.LV);
//
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels,View.MeasureSpec.EXACTLY);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//
//        view.measure(widthMeasureSpec,heightMeasureSpec);
//        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
//
//        PdfDocument document = new PdfDocument();
//
//        int pageCount = listView.getCount();
//        int pageNumber = 1;
//        int pageHeight = 0;
//
//        Canvas canvas = null;
//        PdfDocument.Page page = null;
//
//        for (int i = 0; i<pageCount;i++)
//        {
//            View listItem = listView.getChildAt(i);
//            listItem.measure(widthMeasureSpec,heightMeasureSpec);
//            listItem.layout(0,0,listItem.getMeasuredWidth(),listItem.getMeasuredHeight());
//
//            if (pageHeight + listItem.getMeasuredHeight() > 1960)
//            {
//
//                if(page != null)
//                {
//                    document.finishPage(page);
//                }
//
//                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1960, pageNumber).create();
//                page = document.startPage(pageInfo);
//                pageNumber++;
//                canvas = page.getCanvas();
//                pageHeight = 0;
//            }
//
//            listItem.draw(canvas);
//            pageHeight += listItem.getMeasuredHeight();
//
//        }
//
//        if (page != null)
//        {
//            document.finishPage(page);
//        }

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "Example.pdf";
        File file = new File(downloadsDir,fileName);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "PFD created successfully", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e)
        {
            Log.d("myLog","Error: "+ e.toString());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }


    public void sendEmail()
    {
        String[] TO = {"adikadia05@gmail.com"};
        String[] CC = {"xyz@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mail to: "));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"YOU ORDER HAS BEEN SUCCESSFULLY PLACED !!!");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"EMAIL MESSAGE GOES HERE ->");

        try
        {
            startActivity(Intent.createChooser(emailIntent,"Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(PlaceOrder.this, "There is no email client installed", Toast.LENGTH_SHORT).show();
        }
    }


    public void MyOrder()
    {
        dbRef = FirebaseDatabase.getInstance().getReference("MyOrder");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String currentDate = dateFormat.format(calendar.getTime());
        String currentTime = timeFormat.format(calendar.getTime());

        for (CartModel cartItem : cList)
        {
            String orderId = dbRef.push().getKey();

            if (orderId != null)
            {
                BoughtModel boughtModel = new BoughtModel();
                boughtModel.setName(cartItem.getName());
                boughtModel.setQty(cartItem.getQty());
                boughtModel.setAmount(cartItem.getPrice());
                boughtModel.setMail(cartItem.getMail());
                boughtModel.setDate(currentDate);
                boughtModel.setTime(currentTime);

                dbRef.child(orderId).setValue(boughtModel);

            }
        }

    }


}