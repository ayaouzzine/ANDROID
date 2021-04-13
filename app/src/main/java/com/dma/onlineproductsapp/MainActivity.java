package com.rajendra.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rajendra.onlineproductsapp.adapter.ProductAdapter;
import com.rajendra.onlineproductsapp.adapter.ProductCategoryAdapter;
import com.rajendra.onlineproductsapp.model.ProductCategory;
import com.rajendra.onlineproductsapp.model.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Trending"));
        productCategoryList.add(new ProductCategory(2, "Most Popular"));
        productCategoryList.add(new ProductCategory(3, "Watches"));
        productCategoryList.add(new ProductCategory(4, "Properties"));
        productCategoryList.add(new ProductCategory(5, "Cars"));
        productCategoryList.add(new ProductCategory(6, "Paintings"));
        productCategoryList.add(new ProductCategory(7, "Other Services"));

        setProductRecycler(productCategoryList);

        List<Products> productsList = new ArrayList<>();
        productsList.add(new Products(1, "1933 Wilys 2-Dr Coupe", "$ 400 000", "$ 420 000", R.drawable.car1));
        productsList.add(new Products(2, "African Mango Shower Gel", "$ 2000", "$ 2500", R.drawable.paiting1));
        productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 10 000", "$ 10 000", R.drawable.meuble1));
      //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 20.00", "$ 25.00", R.drawable.prod1));
      //  productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 7.00", "$ 17.00", R.drawable.prod2));
      //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 14.00", "$ 25.00", R.drawable.prod1));

        setProdItemRecycler(productsList);

    }

    private void setProductRecycler(List<ProductCategory> productCategoryList){

        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new ProductCategoryAdapter(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);

    }

    private void setProdItemRecycler(List<Products> productsList){

        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);

    }





}
