package com.wimatt.ux.drinkanddare.room.daos;

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wimatt.ux.drinkanddare.room.models.Product

@Dao
abstract class ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertSingleProduct(todo: Product): Long?

    @Query("SELECT * FROM Product")
    abstract fun getAll(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE :name = name ")
    abstract fun searchByName(name: String): LiveData<List<Product>>

    @Transaction
    open fun insertProducts(products: List<Product>): Long? {
        products.forEach {
            insertSingleProduct(it)
        }
        return products.count().toLong()
    }

    @Update
    abstract fun update(todo: Product)

    @Delete
    abstract fun delete(todo: Product)

    @Query("DELETE FROM Product")
    abstract fun deleteAll()

}