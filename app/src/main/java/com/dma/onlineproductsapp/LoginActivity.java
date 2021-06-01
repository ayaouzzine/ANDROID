package com.dma.onlineproductsapp;

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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public static final String URL_LOGIN = "http://androidauctions.000webhostapp.com/login.php";
    EditText ed_email, ed_password;
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String STATUS = "status";
    public static final String USERNAME = "username";
    public static String ed_role;
    public static String client = "h", admin = "h", nv = "v";
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView register = findViewById(R.id.register2);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ed_email = findViewById(R.id.email);
        ed_password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        //status = sharedPreferences.getBoolean(STATUS, false);

        if (status) {
            finish();
            //Intent intent = new Intent(LoginActivity.this, ClientActivity.class);
            //startActivity(intent);
        }
    }

    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        finish();
    }

    public void login(View view) {
        final String email = ed_email.getText().toString();
        final String password = ed_password.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);

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
                    params.put("email", email);
                    params.put("password", password);

                    //returing the response
                    return requestHandler.sendPostRequest(URL_LOGIN, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pdLoading.dismiss();

                    try {
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
                    }

                }
            }

            Login login = new Login();
            login.execute();
            //Toast.makeText(getApplicationContext(), admin, Toast.LENGTH_LONG).show();
            if (client.equals("true")) {
                Intent intent;
                if (nv.equals("nv")) {
                    intent = new Intent(LoginActivity.this, ValidationActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, ClientActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            } else if (admin.equals("true")) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }

        }
    }

    public void register(View view) {
        finish();

    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.toggle_view1) {

            if (ed_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.eye);

                //Show Password
                ed_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye_off);

                //Hide Password
                ed_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
    public void reset(View view) {
       AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(R.layout.reset);
                builder.setTitle("Reset Password");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText email = (EditText) ((AlertDialog) dialog).findViewById(R.id.email);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(EMAIL,email.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, ResetActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                final String regexEm="([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
                if (s.toString().matches(regexEm)) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                }
            }
        });




    }
    }






  /*  public void toggle(View v){
        if(!state){
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            eye.setImageResource(R.drawable.eye);
        }
        else{
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            eye.setImageResource(R.drawable.eye_off);
        }
        state = !state;
    }*/


