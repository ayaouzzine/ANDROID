package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddCategory extends AppCompatActivity {
    EditText textInputEditTextcategory;
    Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        textInputEditTextcategory = findViewById(R.id.categoryname);
        buttonAdd =findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String category;
                category = String.valueOf(textInputEditTextcategory.getText());


                if(!category.equals("")) {
                    // progressBar.setVisibility(View.VISIBLE);


                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "category";



                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = category;

                            PutData putData = new PutData("https://enchereenligne1.000webhostapp.com/addcategory.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //  progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();

                                    if(result.equals("Category added Successfully")){
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
                } else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}