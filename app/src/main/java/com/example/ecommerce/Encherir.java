package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Encherir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encherir);
        Button button=(Button)findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "test";



                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = "test";

                        PutData putData = new PutData("https://enchereenligne1.000webhostapp.com/update.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                //  progressBar.setVisibility(View.GONE);

                                String result = putData.getResult();

                                if(result.equals("Auctions Updated")){
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                                else
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();


                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                        //End Write and Read data with URL
                    }
                });
            }


        });

    }
}