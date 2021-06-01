package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawer;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTextView);

        textView.setText("Enchere En ligne");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_addprod:
                startActivity(new Intent(MainActivity.this,AddProduct.class));
                break;
            case R.id.nav_delprod:
                startActivity(new Intent(MainActivity.this,DeleteProduct.class));
                break;
            case R.id.nav_addcat:
                startActivity(new Intent(MainActivity.this,AddCategory.class));
                break;
            case R.id.nav_delcat:
                startActivity(new Intent(MainActivity.this,DeleteCategory.class));
                break;

            case R.id.nav_update:
                startActivity(new Intent(MainActivity.this,Encherir.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(MainActivity.this,Login.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer;

        drawer = findViewById(R.id.drawer_layout);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}