package com.dma.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dma.onlineproductsapp.R;
public class Productdetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        Intent intent = getIntent();
        String libelleProduct = intent.getStringExtra("libelleProduct");
        String initialPrice = intent.getStringExtra("initialPrice");
        String finalPrice = intent.getStringExtra("finalPrice");

        TextView libelle = findViewById(R.id.textView11);
        TextView initial = findViewById(R.id.textView12);
        TextView finalP = findViewById(R.id.textView13);

        libelle.setText(libelleProduct);
         initial.setText("$  " +initialPrice);
          finalP.setText("$  " +finalPrice);

    }
}
