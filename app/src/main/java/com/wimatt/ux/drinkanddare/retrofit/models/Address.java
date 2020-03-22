package com.wimatt.ux.drinkanddare.retrofit.models;

public class Address {

    private int postralCode;
    private String city;
    private String address;

    public Address(int postralCode, String city, String adress) {
        this.postralCode = postralCode;
        this.city = city;
        this.address = adress;
    }

    public int getPostralCode() {
        return postralCode;
    }

    public void setPostralCode(int postralCode) {
        this.postralCode = postralCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
