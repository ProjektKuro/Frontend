package com.wimatt.ux.drinkanddare.retrofit.models;

import java.util.List;

public class ShopList {

    private List<Shop> shops;

    public ShopList(List<Shop> shops) {
        this.shops = shops;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
