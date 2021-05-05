package com.example.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText ed_username, ed_email, ed_password,conf_pass,phone;
    String gender="u";
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String STATUS = "status";
    public static final String USERNAME = "username";
    public static final String URL_REGISTER = "http://192.168.43.174/ANDROID/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View register = findViewById(R.id.register4);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ed_username = findViewById(R.id.username);
        ed_email = findViewById(R.id.email);
        ed_password = findViewById(R.id.password);
        conf_pass=findViewById(R.id.confirmpassword);
    phone=findViewById(R.id.phone);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        finish();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked){
                    gender="M";
                    break;}
            case R.id.female:
                if (checked){
                    gender="F";
                    break;}
        }
    }


    public void register(View view) {
        final String username = ed_username.getText().toString();
        final String email = ed_email.getText().toString();
        final String password = ed_password.getText().toString();
        final String conf_password = conf_pass.getText().toString();
        final String sexe = gender.toString();
        String regexStr = "^[0][5,6,7][0-9]{8}$";
        String number = phone.getText().toString();
        if ((!number.isEmpty()) && (number.matches(regexStr) == false)) {
            //Toast.makeText(this,"Please enter valid phone number",Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("\nPlease enter valid phone number")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        String regexEm="([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
        if ((!email.isEmpty())&&(email.matches(regexEm)==false)){
            //Toast.makeText(this,"Please enter valid email",Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("\nPlease enter valid email")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()|| conf_password.isEmpty()|| sexe.equals("u")|| number.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            //System.out.println(password);
        }

        else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(RegisterActivity.this);

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
                    params.put("password", password);
                    params.put("username", username);
                    params.put("conf_password", conf_password);
                    params.put("gender",sexe);
                    String regexStr = "^[0][5,6,7][0-9]{8}$";
                    String number=phone.getText().toString();
                    if( number.matches(regexStr)==true  ) {
                        params.put("phone",number);
                    }
                    if( email.matches(regexEm)==true  ) {
                        params.put("email", email);
                    }
                    //returing the response
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

                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            String email = obj.getString("email");

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(EMAIL, email);
                            editor.apply();
                            if(obj.getString("message").equals("User Registered Successfully")){
                                Intent intent = new Intent(getApplicationContext(), ValidationActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }

                        else if(obj.getBoolean("error")){
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage("\n"+obj.getString("message"))
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

            Login login = new Login();
            login.execute();

        }
    }

    public void login(View view){

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
        else if (view.getId() == R.id.toggle_view2) {

            if (conf_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.eye);

                //Show Password
                conf_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye_off);

                //Hide Password
                conf_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }
    }


