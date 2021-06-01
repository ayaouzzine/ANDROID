package com.dma.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Productdetails extends AppCompatActivity {
    public static final String URL_LOGIN = "http://androidauctions.000webhostapp.com/price.php";
    Context context;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String PRICE = "price";
    public static final String ID = "id";
    public static Integer id;
    public Integer suggested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        Button higherprice = findViewById(R.id.button2);
        Intent intent = getIntent();
        final  Integer productId = intent.getIntExtra("productId", 0);
        id=productId;
        String libelleProduct = intent.getStringExtra("libelleProduct");
        String initialPrice = intent.getStringExtra("initialPrice");
        final String finalPrice = intent.getStringExtra("finalPrice");
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
        finalP.setText("Actual Price : " + "$  " + finalPrice);
        initial.setText("Initial Price : " + "$  " + initialPrice + "\nDescription : " + productDesc);
        endDate.setText("Auction ends at : " + EndDate);
        cat.setText(categorie);
        higherprice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //startActivity(intent);
                //finish();
                AlertDialog.Builder builder = new AlertDialog.Builder(Productdetails.this);
                builder.setView(R.layout.price);
                builder.setTitle("Participate to this auction");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText price = (EditText) ((AlertDialog) dialog).findViewById(R.id.email);
                        //SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putString(EMAIL,price.getText().toString());
                        //editor.apply();
                        suggested = Integer.parseInt(price.getText().toString());
                        Integer existing = Integer.parseInt(finalPrice);
                        if (suggested <= existing) {
                            new AlertDialog.Builder(Productdetails.this)
                                    .setTitle("Auction denied")
                                    .setMessage("\n A higher price is already suggested !")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        } else {
                            Intent intent = new Intent(Productdetails.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                //((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                EditText email = (EditText) ((AlertDialog) dialog).findViewById(R.id.email);
                email.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s.length() > 0) {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        }
                    }
                });
            }
        });
    }
        public void auction (View view){
            final String prix = suggested.toString();
            class Auction extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(Productdetails.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("price", prix);
                    params.put("id", id.toString());
                    //returing the response
                    return requestHandler.sendPostRequest(URL_LOGIN, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pdLoading.dismiss();

                   /* try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            ed_role = obj.getString("role");
                            if (ed_role.equals("C")) {
                                admin = "false";
                                client = "true";
                                if (obj.has("nv")) nv = obj.getString("nv");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //editor.putString(USERNAME, username);
                                editor.putString(EMAIL, obj.getString("email"));
                                //editor.putBoolean(STATUS, true);
                                editor.apply();
                                //finish();
                                //Toast.makeText(getApplicationContext(), obj.getString("role"), Toast.LENGTH_LONG).show();

                            } else if (ed_role.equals("A")) {
                                client = "false";
                                admin = "true";
                                //Toast.makeText(getApplicationContext(), admin, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Error")
                                    .setMessage("\n" + obj.getString("message"))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(LoginActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }*/

                }
            }

            Auction auction = new Auction();
            auction.execute();
            //Toast.makeText(getApplicationContext(), admin, Toast.LENGTH_LONG).show();

        }

    }




