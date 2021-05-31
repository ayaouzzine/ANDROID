package com.dma.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dma.onlineproductsapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Productdetails extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        Button higherprice = findViewById(R.id.button2);
        higherprice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        Intent intent = getIntent();
        String libelleProduct = intent.getStringExtra("libelleProduct");
        String initialPrice = intent.getStringExtra("initialPrice");
        String finalPrice = intent.getStringExtra("finalPrice");
        String imageUrl = intent.getStringExtra("imageUrl");
        String productDesc = intent.getStringExtra("productDesc");
        String EndDate = intent.getStringExtra("EndDate");
  String categorie = intent.getStringExtra("libelleCategorie");

        TextView libelle = findViewById(R.id.textView11);
        TextView finalP = findViewById(R.id.textView12);
        TextView endDate = findViewById(R.id.textView14);
        TextView initial = findViewById(R.id.textView13);
        TextView cat = findViewById(R.id.textView7);
        ImageView i = findViewById(R.id.imageView7);
        Glide.with(this).load(imageUrl).into(i);


        libelle.setText(libelleProduct);
         finalP.setText("Actual Price : "+"$  " +initialPrice);
         initial.setText("Initial Price : "+"$  " +finalPrice+ "\nDescription : "+productDesc);
        endDate.setText("Auction ends at : " + EndDate);
        cat.setText(categorie);
    }
}
