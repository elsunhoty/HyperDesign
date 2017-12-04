package com.tmoo7.hyperdesign.Models;

/**
 * Created by othello on 12/3/2017.
 */

public class ProductModel {

    private int id;
    private String productDescription,url;
    private float price ;

    public ProductModel() {
    }

    public ProductModel(int id, String productDescription, String url, float price) {
        this.id = id;
        this.productDescription = productDescription;
        this.url = url;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
