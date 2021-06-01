package com.dma.onlineproductsapp.model;

import android.graphics.drawable.Drawable;

import org.joda.time.DateTime;

import java.time.LocalDateTime;

public class Products {

    Integer productid;
    String productName;
    String productInitialPrice;
    String productFinalPrice;
    String imageUrl;
    String productDesc;
    LocalDateTime startDateAuction;
    DateTime endDateAuction;
    String libelleCategory;

    public Products(Integer productid, String productName, String productQty, String productPrice, String imageUrl,String productDesc,DateTime endDateAuction,String libelleCategory) {

        this.productid = productid;
        this.productName = productName;
        this.productInitialPrice = productQty;
        this.productFinalPrice = productPrice;
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

    public String getProductInitialPrice() {
        return productInitialPrice;
    }

    public void setProductInitailPrice(String productQty) {
        this.productInitialPrice = productQty;
    }

    public String getProductFinalPrice() {
        return productFinalPrice;
    }

    public void setProductFinalPrice(String productPrice) {
        this.productFinalPrice = productPrice;
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
