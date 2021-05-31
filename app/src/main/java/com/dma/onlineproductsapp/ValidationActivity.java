package com.dma.onlineproductsapp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ValidationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText ed_code;
    String ed_email;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String URL_REGISTER = "http://192.168.43.174/android/validate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        TextView email = findViewById(R.id.email);
        ed_email = sharedPreferences.getString(EMAIL, "");
        email.setText("Please check " + sharedPreferences.getString(EMAIL, ""));
//System.out.println(sharedPreferences.getString(USERNAME,""));
        ed_code = findViewById(R.id.code);
    }

    public void validate(View view) {
        final String code = ed_code.getText().toString();
        if(code.isEmpty()){
            Toast.makeText(this, "Please fill the field", Toast.LENGTH_SHORT).show();
        }

        else {
            class Validation extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(ValidationActivity.this);

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
                    params.put("code", code);
                    params.put("email", ed_email);

                    return requestHandler.sendPostRequest(URL_REGISTER, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pdLoading.dismiss();

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response
                        if(obj.getString("message").equals("Success")) {
                            Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                            startActivity(intent);
                            finish();
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(ValidationActivity.this, "My Notification");
                            builder.setSmallIcon(R.drawable.logo);
                            builder.setContentTitle("Account validated successfully");
                            builder.setContentText("Congratulations ! Your account is ready for use");
                            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            builder.setAutoCancel(true);
                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ValidationActivity.this);
                            managerCompat.notify(1,builder.build());
                        }
                        else if(obj.getString("message").equals("Fail")) {
                            new AlertDialog.Builder(ValidationActivity.this)
                                    .setTitle("Error")
                                    .setMessage("\nWrong validation code!\nTry again")
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
                        //Toast.makeText(RegisterActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }

            Validation val = new Validation();
            val.execute();

        }
    }

    public void validation(View view){

        finish();

    }
    }

