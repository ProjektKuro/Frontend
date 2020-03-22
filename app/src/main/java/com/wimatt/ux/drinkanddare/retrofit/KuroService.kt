package com.wimatt.ux.drinkanddare.retrofit

import com.wimatt.ux.drinkanddare.retrofit.models.ProductList
import com.wimatt.ux.drinkanddare.retrofit.models.ShopList
import com.wimatt.ux.drinkanddare.room.models.Product
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface KuroService {

    @GET("shops?distance=5000")
    fun getNearbyShops(@Query("lat") latitude: Double, @Query("long") longitude: Double): Call<ShopList>

    @GET("products")
    fun getAllProducts():
            Call<ProductList>



    companion object {
        fun create(): KuroService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://kuro.tlahmann.com/api/")
                    .build()
            return retrofit.create(KuroService::class.java)
        }
    }
}