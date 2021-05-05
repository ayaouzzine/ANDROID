package com.example.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public ProgressDialog progressDialog;

    private TextView tv;
    public static final int RESULT_Main = 1;
    EditText name, pass;
    ImageView eye;
    boolean state = false;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //Appel de la page de Login

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}