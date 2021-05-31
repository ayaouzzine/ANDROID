package com.dma.onlineproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.dma.onlineproductsapp.adapter.ProductAdapter;
import com.dma.onlineproductsapp.adapter.ProductCategoryAdapter;
import com.dma.onlineproductsapp.model.ProductCategory;
import com.dma.onlineproductsapp.model.Products;

public class ProductActivity extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ProductAdapter productAdapter;

    String address = "http://192.168.43.174/android/products.php";
    ArrayAdapter<String> adapter;
    InputStream is = null;
    String line = null;
    String result = null;
    String[] data;

    List<Products> products = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        System.out.println("staaaaaarting");
        getData();

        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);


        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Trending"));
        productCategoryList.add(new ProductCategory(2, "Most Popular"));
        productCategoryList.add(new ProductCategory(3, "Watches"));
        productCategoryList.add(new ProductCategory(4, "Properties"));
        productCategoryList.add(new ProductCategory(5, "Cars"));
        productCategoryList.add(new ProductCategory(6, "Paintings"));
        productCategoryList.add(new ProductCategory(7, "Other Services"));

        //setProductRecycler(products);

        //productsList.add(new Products(1, "1933 Wilys 2-Dr Coupe", "$ 400 000", "$ 420 000", R.drawable.car1));
        //productsList.add(new Products(2, "African Mango Shower Gel", "$ 2000", "$ 2500", R.drawable.paiting1));
        //productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 10 000", "$ 10 000", R.drawable.meuble1));
        //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 20.00", "$ 25.00", R.drawable.prod1));
        //  productsList.add(new Products(1, "Japanese Cherry Blossom", "$ 7.00", "$ 17.00", R.drawable.prod2));
        //  productsList.add(new Products(2, "African Mango Shower Gel", "$ 14.00", "$ 25.00", R.drawable.prod1));

        setProdItemRecycler(products);
        setProductRecycler(productCategoryList);


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        ProductActivity.this.finish();
    }



    private void getData(){
        DateTimeFormatter formatter  = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        try{
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
            System.out.println(" no prob ");
        }  catch (Exception e) {
            System.out.println("hnaaa");
            e.printStackTrace();
        }

        //Read content into a string
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println(" no prob ici " + br);
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null ){
                System.out.println("first");
                sb.append(line +"\n");
            }
            is.close();
            result = sb.toString();
            System.out.println(" heeeeell iis" + result);

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
                String endDate = jo.getString("EndDate");
                DateTime dt = formatter.parseDateTime(endDate);
                System.out.println("date is" + dt);
                //date = LocalDateTime.parse(endDate);

                products.add(new Products((int)Math.random(),jo.getString("libelleProduct"),jo.getString("initialPrice"),jo.getString("finalPrice"),jo.getString("imageUrl"),jo.getString("productDesc"), dt,jo.getString("libelleCategorie")));
            }

            // products = productsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Drawable LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
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
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);

    }





}
