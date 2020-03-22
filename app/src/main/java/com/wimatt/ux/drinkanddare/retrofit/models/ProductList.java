package com.wimatt.ux.drinkanddare.retrofit.models;

import java.util.List;

public class ProductList {

    List<Product> products;

    public ProductList(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
