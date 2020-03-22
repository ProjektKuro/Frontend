package com.wimatt.ux.drinkanddare.retrofit;

import com.wimatt.ux.drinkanddare.retrofit.models.Product;
import com.wimatt.ux.drinkanddare.retrofit.models.Shop;
import com.wimatt.ux.drinkanddare.retrofit.models.ShopList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KuroApiService {

    @GET("/shops?distance=5000")
    Call<ShopList> getNearbyShops(@Query("lat") double latitude, @Query("long") double longitude);

    @GET("/products")
    Call<Product> getAllProducts();

    @GET("/products/{productId}/shops")
    Call<Shop> getShopsByProductId(@Path("productId") String id);

    @GET("/shops/{shopId}/products")
    Call<Product> getProductByShopId(@Path("shopId") String id);

}
