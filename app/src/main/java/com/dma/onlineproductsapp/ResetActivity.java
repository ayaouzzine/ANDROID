package com.dma.onlineproductsapp;

import android.app.AlertDialog;
import android.app.Notification;
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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ResetActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
   public static final String EMAIL = "email";
   TextView email;
    String ed_email;
    EditText ed_code,ed_pwd,ed_confpwd;
    public static final String URL_REGISTER = "http://androidauctions.000webhostapp.com/reset.php";
    public static final String URL_REGISTER2 = "http://androidauctions.000webhostapp.com/newpwd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        email = findViewById(R.id.email1);
        email.setText(sharedPreferences.getString(EMAIL,""));
        ed_email = sharedPreferences.getString(EMAIL, "");
        ed_code = findViewById(R.id.resetcode);
        ed_pwd = findViewById(R.id.password2);
        ed_confpwd = findViewById(R.id.confirmpassword2);


            class Try extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(ResetActivity.this);

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
                    params.put("email", ed_email);

                    return requestHandler.sendPostRequest(URL_REGISTER, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    //super.onPostExecute(s);
                    pdLoading.dismiss();

                    try {

                        //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response

                         if(obj.getString("message").equals("Fail")) {
                            new AlertDialog.Builder(ResetActivity.this)
                                    .setTitle("No user found")
                                    .setMessage("\n Try again with another email !")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        }
                         else
                             //if(obj.getString("message").equals("Success"))
                             {
                             //Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                             //startActivity(intent);
                             //finish();
                             new AlertDialog.Builder(ResetActivity.this)
                                     .setTitle("Validation Code Sent")
                                     .setMessage("\nPlease check your emails\n")
                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int id) {
                                             dialog.cancel();
                                         }
                                     })
                                     .setIcon(android.R.drawable.checkbox_on_background)
                                     .show();
                         }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(ResetActivity.this, "Check your emails for validation code" , Toast.LENGTH_LONG).show();
                    }                }
            }

            Try val = new Try();
            val.execute();


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        ResetActivity.this.finish();
    }
    public void reset(View view) {
        final String code = ed_code.getText().toString();
        final String password = ed_pwd.getText().toString();
        final String confirmpassword = ed_confpwd.getText().toString();
        if(code.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
            Toast.makeText(this, "Please fill the field", Toast.LENGTH_SHORT).show();
        }

        else {
            class Reset extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(ResetActivity.this);

                @Override
                protected void onPreExecute() {
                    //super.onPreExecute();

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
                    params.put("password", password);
                    params.put("confirmpassword", confirmpassword);
                    return requestHandler.sendPostRequest(URL_REGISTER2, params);
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
                            Toast.makeText(ResetActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(ResetActivity.this, "My Notification");
                                    builder.setSmallIcon(R.drawable.logo);
                                    builder.setContentTitle("Password changed successfully");
                                    builder.setContentText("Try to use your new password !");
                                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                    builder.setAutoCancel(true);
                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ResetActivity.this);
                            managerCompat.notify(1,builder.build());
                        }
                        else if(obj.getString("message").equals("Fail")) {
                            new AlertDialog.Builder(ResetActivity.this)
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

                            else if(obj.getString("message").equals("Passwords")) {
                            new AlertDialog.Builder(ResetActivity.this)
                                    .setTitle("Error")
                                    .setMessage("\nPasswords not matching! \nTry again")
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

            Reset val = new Reset();
            val.execute();

        }
    }
    public void ShowHidePass(View view) {

        if (view.getId() == R.id.toggle_view3) {

            if (ed_pwd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.eye);

                //Show Password
                ed_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye_off);

                //Hide Password
                ed_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
        else if (view.getId() == R.id.toggle_view) {

            if (ed_confpwd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.eye);

                //Show Password
                ed_confpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye_off);

                //Hide Password
                ed_confpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }
}
