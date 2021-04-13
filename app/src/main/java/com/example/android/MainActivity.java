package com.example.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
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
        public void onCreate(Bundle icicle)
        {
            super.onCreate(icicle);

            //Appel de la page de Login
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), RESULT_Main);

            setContentView(R.layout.activity_main);
            name = findViewById(R.id.username);
            pass = findViewById(R.id.password);
            eye = findViewById(R.id.toggle_view1);
        }

        private void startup(Intent i)
        {
            // Récupère l'identifiant
            int user = i.getIntExtra("userid",-1);

            //Affiche les identifiants de l'utilisateur
            tv.setText("UserID: "+String.valueOf(user)+" logged in");
        }


        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_Main && resultCode == RESULT_CANCELED) {
                progressDialog.dismiss();
                createDialog("Error", "Invalid Username and Password");

            }
            else
                startup(data);
        }

    private void createDialog(String title, String text)
    {
        // Création d'une popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();

    }

    // For toggling visibility of password.
    public void toggle(View v){
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
    }
    }