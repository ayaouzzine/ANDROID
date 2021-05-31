package com.example.ecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProduct extends Activity implements AdapterView.OnItemSelectedListener {
    EditText textInputEditTextproductname,textInputEditTextDesc,textInputEditTextPrice,textInputEditTextImage,textInputEditTextDate;

    Button buttonAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        textInputEditTextproductname = findViewById(R.id.productname);
        textInputEditTextDesc = findViewById(R.id.desc);
        textInputEditTextPrice = findViewById(R.id.price);
        textInputEditTextImage = findViewById(R.id.imgurl);
        textInputEditTextDate = findViewById(R.id.date);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        buttonAdd =findViewById(R.id.buttonAdd);


        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String productname,productdesc,imageurl,enddate,price,cat;
                productname = String.valueOf(textInputEditTextproductname.getText());
                productdesc = String.valueOf(textInputEditTextDesc.getText());
                imageurl = String.valueOf(textInputEditTextImage.getText());
                enddate = String.valueOf(textInputEditTextDate.getText());
                price = String.valueOf(textInputEditTextPrice.getText());
                cat = spinner.getSelectedItem().toString();

                if(!productname.equals("") && !productdesc.equals("") && !imageurl.equals("") && !enddate.equals("")&& !price.equals("")) {
                    // progressBar.setVisibility(View.VISIBLE);


                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "productname";
                            field[1] = "productdesc";
                            field[2] = "imageurl";
                            field[3] = "enddate";
                            field[4] = "price";
                            field[5] = "cat";


                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = productname;
                            data[1] = productdesc;
                            data[2]=imageurl;
                            data[3]=enddate;
                            data[4]=price;
                            data[5]=cat;
                            PutData putData = new PutData("https://enchereenligne1.000webhostapp.com/addproduct.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //  progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();

                                    if(result.equals("Product added Successfully")){
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


                EditText date;
        date = (EditText)findViewById(R.id.date);
        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();





            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Spinner element
        //Button button=(Button)findViewById(R.id.button);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Cars");
        categories.add("Electronics");
        categories.add("Beauty");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}

