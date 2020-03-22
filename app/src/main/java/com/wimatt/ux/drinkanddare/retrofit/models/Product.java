package com.wimatt.ux.drinkanddare.retrofit.models;

public class Product {

    String _id;
    String name;
    int quantity;

    public Product(String _id, String name, int quantity) {
        this._id = _id;
        this.name = name;
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
