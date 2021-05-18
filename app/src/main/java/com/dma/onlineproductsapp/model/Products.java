package com.dma.onlineproductsapp.model;

import android.graphics.drawable.Drawable;

import org.joda.time.DateTime;

import java.time.LocalDateTime;

public class Products {

    Integer productid;
    String productName;
    String productQty;
    String productPrice;
    String imageUrl;
    String productDesc;
    LocalDateTime startDateAuction;
    DateTime endDateAuction;
    String libelleCategory;

    public Products(Integer productid, String productName, String productQty, String productPrice, String imageUrl,String productDesc,DateTime endDateAuction,String libelleCategory) {

        this.productid = productid;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
        this.productDesc = productDesc;
        this.endDateAuction = endDateAuction;
        this.libelleCategory = libelleCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setImageDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public LocalDateTime getStartDateAuction() {
        return startDateAuction;
    }

    public void setStartDateAuction(LocalDateTime startDateAuction) {
        this.startDateAuction = startDateAuction;
    }

    public DateTime getEndDateAuction() {
        return endDateAuction;
    }

    public void setEndDateAuction(DateTime endDateAuction) {
        this.endDateAuction = endDateAuction;
    }

    public String getLibelleCategory() {
        return libelleCategory;
    }

    public void setLibelleCategory(String libelleCategory) {
        this.libelleCategory = libelleCategory;
    }
}
