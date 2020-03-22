package com.wimatt.ux.drinkanddare.room.daos;

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wimatt.ux.drinkanddare.room.models.Shop

@Dao
abstract class ShopDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertSingleShop(todo: Shop): Long?

    @Query("SELECT * FROM Shop")
    abstract fun getAll(): LiveData<List<Shop>>

    @Query("SELECT * FROM Shop WHERE :name = name ")
    abstract fun searchByName(name: String): LiveData<List<Shop>>

    @Transaction
    open fun insertShops(products: List<Shop>): Long? {
        products.forEach {
            insertSingleShop(it)
        }
        return products.count().toLong()
    }

    @Update
    abstract fun update(todo: Shop)

    @Delete
    abstract fun delete(todo: Shop)

    @Query("DELETE FROM Shop")
    abstract fun deleteAll()

}