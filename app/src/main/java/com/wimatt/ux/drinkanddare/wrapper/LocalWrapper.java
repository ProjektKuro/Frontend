package com.wimatt.ux.drinkanddare.wrapper;

import com.wimatt.ux.drinkanddare.retrofit.KuroApiService;
import com.wimatt.ux.drinkanddare.retrofit.models.Address;
import com.wimatt.ux.drinkanddare.retrofit.models.Product;
import com.wimatt.ux.drinkanddare.retrofit.models.Shop;
import com.wimatt.ux.drinkanddare.retrofit.models.ShopList;
import com.wimatt.ux.drinkanddare.room.daos.ShopDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalWrapper {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://kuro.tlahmann.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static void queryAllNearByShops(double latitude, double longitude) {
        KuroApiService kuroApiService = retrofit.create(KuroApiService.class);

        kuroApiService.getNearbyShops(latitude, longitude)
                .enqueue(new Callback<ShopList>() {
                    @Override
                    public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                        List<Shop> shops = response.body().getShops();
                        // dao access

                    }

                    @Override
                    public void onFailure(Call<ShopList> call, Throwable t) {

                    }
                });
    }

    static void getShopsByProducId(String id) {
        KuroApiService kuroApiService = retrofit.create(KuroApiService.class);

        kuroApiService.getShopsByProductId(id)
                .enqueue(new Callback<Shop>() {
                    @Override
                    public void onResponse(Call<Shop> call, Response<Shop> response) {
                        String id = response.body().get_id();
                        Address address = response.body().getAddress();
                        String name = response.body().getName();
                    }

                    @Override
                    public void onFailure(Call<Shop> call, Throwable t) {

                    }
                });
    }

    static void getProductByShopId(String id) {
        KuroApiService kuroApiService = retrofit.create(KuroApiService.class);

        kuroApiService.getProductByShopId(id)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        String name = response.body().getName();
//                        Long productId = response.body().get_id();
//                        String state = response.body().getState();
                        // TODO die models habe ich geändert, fehler zw entity und domains
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
    }

}
