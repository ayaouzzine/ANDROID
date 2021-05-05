package com.example.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String USERNAME = "username";
    TextView username;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        //username = findViewById(R.id.username);
        //username.setText("Welcome, " + sharedPreferences.getString(USERNAME,""));*/
//System.out.println(sharedPreferences.getString(USERNAME,""));
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ClientActivity.this, sharedPreferences.getString(USERNAME,""), Toast.LENGTH_SHORT).show();

                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.clear();
                //editor.apply();
                //finish();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();

                startActivity(intent);

            }
        });
    }
}
