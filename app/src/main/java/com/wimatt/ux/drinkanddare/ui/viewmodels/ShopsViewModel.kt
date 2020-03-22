package com.wimatt.ux.drinkanddare.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wimatt.ux.drinkanddare.retrofit.KuroService
import com.wimatt.ux.drinkanddare.retrofit.models.ShopList
import com.wimatt.ux.drinkanddare.room.AppDatabase
import com.wimatt.ux.drinkanddare.room.daos.ShopDao
import com.wimatt.ux.drinkanddare.room.models.Shop
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

public class ShopsViewModel(application: Application) : AndroidViewModel(application) {

    var disposable: Disposable? = null

    private val shopDao: ShopDao

    val kuroService by lazy {
        KuroService.create()
    }

    init {
        shopDao = AppDatabase.getDatabase(application).shopDao
    }

    fun loadShops(latitude: Double, longitude: Double) {
        kuroService.getNearbyShops(latitude, longitude)
                .enqueue(object : Callback<ShopList> {

                    override fun onResponse(call: Call<ShopList>, response: Response<ShopList>) {
                        thread {
                            shopDao.deleteAll()
                            val list = response.body()!!.shops
                            for (shop in list) {
                                val roomShop = Shop(shop._id, shop.name, "", shop.address.address)
                                    shopDao.insertSingleShop(roomShop)
                            }
                        }

                    }

                    override fun onFailure(call: Call<ShopList>, t: Throwable) {
                        val a = 2
                    }
                })
    }

    fun insertShop(shop: Shop): Long? {
        return shopDao.insertSingleShop(shop)
    }

    fun insertProducts(shops: List<Shop>): Long? {
        return shopDao.insertShops(shops)
    }

    fun getShops(): LiveData<List<Shop>> {
        return shopDao.getAll()
    }

    fun deleteAll() {
        shopDao.deleteAll()
    }
}