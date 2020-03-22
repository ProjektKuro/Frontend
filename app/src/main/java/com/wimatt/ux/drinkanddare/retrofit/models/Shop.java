package com.wimatt.ux.drinkanddare.retrofit.models;

public class Shop {

    private String _id;
    private String name;
    private Address address;

    public Shop(String _id, String name, Address address) {
        this._id = _id;
        this.name = name;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
