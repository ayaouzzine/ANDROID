package com.dma.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dma.onlineproductsapp.R;
import com.dma.onlineproductsapp.adapter.ProductAdapter;
import com.rajendra.onlineproductsapp.adapter.ProductCategoryAdapter;
import com.rajendra.onlineproductsapp.model.ProductCategory;
import com.rajendra.onlineproductsapp.model.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ProductAdapter productAdapter;

    String address = "http://192.168.1.14/android/products.php";
    ArrayAdapter<String> adapter;
    InputStream is = null;
    String line = null;
    String result = null;
    String[] data;

    List<Products> products = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        System.out.println("staaaaaarting");
        getData();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);


        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Trending"));
        productCategoryList.add(new ProductCategory(2, "Most Popular"));
        productCategoryList.add(new ProductCategory(3, "Watches"));
        productCategoryList.add(new ProductCategory(4, "Properties"));
        productCategoryList.add(new ProductCategory(5, "Cars"));
        productCategoryList.add(new ProductCategory(6, "Paintings"));
        productCategoryList.add(new ProductCategory(7, "Other Services"));

        setProductRecycler(productCategoryList);

       //productsList.add(new Products(1, "1933 Wilys 2-Dr Coupe", "$ 400 000", "$ 420 000", R.drawable.car1));
        //productsList.add(new Products(2, "African Mango Shower Gel", "$ 2000", "$ 2500", R.drawable.paiting1));
        //productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 10 000", "$ 10 000", R.drawable.meuble1));
      //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 20.00", "$ 25.00", R.drawable.prod1));
      //  productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 7.00", "$ 17.00", R.drawable.prod2));
      //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 14.00", "$ 25.00", R.drawable.prod1));

        setProdItemRecycler(products);


    }

    private void getData(){
        try{
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        }  catch (Exception e) {
            e.printStackTrace();
        }

        //Read content into a string
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null ){
                sb.append(line +"\n");
            }
            is.close();
            result = sb.toString();
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
         // parse json
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
           // List<Products> productsList = new ArrayList<>();
            data = new String[ja.length()];

            for(int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);
                System.out.println(jo.getString("libelleProduct"));
                data[i] = jo.getString("libelleProduct");
                products.add(new Products((int)Math.random(),jo.getString("libelleProduct"),jo.getString("initialPrice"),jo.getString("finalPrice"),R.drawable.car1));
            }

           // products = productsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
