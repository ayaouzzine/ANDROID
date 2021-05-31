package com.example.ecommerce;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class SignUp extends AppCompatActivity {
    EditText textInputEditTextproductname,textInputEditTextDesc,textInputEditTextPrice,textInputEditTextImage,textInputEditTextDate;
Button buttonAdd;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        textInputEditTextproductname = findViewById(R.id.productname);
        textInputEditTextDesc = findViewById(R.id.desc);
        textInputEditTextPrice = findViewById(R.id.price);
        textInputEditTextImage = findViewById(R.id.imgurl);
        textInputEditTextDate = findViewById(R.id.date);


        buttonAdd =findViewById(R.id.buttonAdd);

buttonAdd.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        String productname,productdesc,imageurl,enddate,price;
        productname = String.valueOf(textInputEditTextproductname.getText());
        productdesc = String.valueOf(textInputEditTextDesc.getText());
        imageurl = String.valueOf(textInputEditTextImage.getText());
        enddate = String.valueOf(textInputEditTextDate.getText());
        price = String.valueOf(textInputEditTextPrice.getText());


        if(!productname.equals("") && !productdesc.equals("") && !imageurl.equals("") && !enddate.equals("")&& !price.equals("")) {
           // progressBar.setVisibility(View.VISIBLE);


            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[5];
                    field[0] = "productname";
                    field[1] = "productdesc";
                    field[2] = "imageurl";
                    field[3] = "enddate";
                    field[4] = "price";

                    //Creating array for data
                    String[] data = new String[5];
                    data[0] = productname;
                    data[1] = productdesc;
                    data[2]=imageurl;
                    data[3]=enddate;
                    data[4]=price;
                    PutData putData = new PutData("https://enchereenligne1.000webhostapp.com/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                          //  progressBar.setVisibility(View.GONE);

                            String result = putData.getResult();

                            if(result.equals("Product added Successfully")){
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
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
        } else {
            Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
        }
    }
});



    }

}

