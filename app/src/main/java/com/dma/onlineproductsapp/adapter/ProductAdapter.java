package com.dma.onlineproductsapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.Call;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dma.onlineproductsapp.Productdetails;
import com.dma.onlineproductsapp.R;
import com.dma.onlineproductsapp.model.Products;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Products> productsList;


    public ProductAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.products_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {

        //holder.prodImage.setImageResource(productsList.get(position).getImageUrl());
        holder.prodName.setText(productsList.get(position).getProductName());
        holder.prodQty.setText(productsList.get(position).getProductInitialPrice());
        holder.prodPrice.setText(productsList.get(position).getProductFinalPrice());
        Glide.with(this.context).load(productsList.get(position). getImageUrl()).into(holder.prodImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Productdetails.class);
                i.putExtra("productId",productsList.get(position).getProductid());
                i.putExtra("libelleProduct",productsList.get(position).getProductName());
                i.putExtra("initialPrice",productsList.get(position).getProductInitialPrice());
                i.putExtra("finalPrice",productsList.get(position).getProductFinalPrice());
                i.putExtra("imageUrl",productsList.get(position). getImageUrl());
                i.putExtra("productDesc",productsList.get(position).getProductDesc());
                i.putExtra("libelleCategorie",productsList.get(position).getLibelleCategory());
                DateTime dt = productsList.get(position).getEndDateAuction();
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                String str = fmt.print(dt);
                i.putExtra("EndDate",str);


                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(holder.prodImage, "image");
                Glide.with(context).load(productsList.get(position). getImageUrl()).into(holder.prodImage);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                context.startActivity(i, activityOptions.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{
         ImageView prodImage;
        TextView prodName, prodQty, prodPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prodImage = itemView.findViewById(R.id.prod_image);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.prod_price);
            prodQty = itemView.findViewById(R.id.prod_qty);


        }
    }

}
