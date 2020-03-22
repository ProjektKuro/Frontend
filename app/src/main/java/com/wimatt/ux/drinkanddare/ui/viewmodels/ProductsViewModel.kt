package com.wimatt.ux.drinkanddare.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wimatt.ux.drinkanddare.retrofit.KuroService
import com.wimatt.ux.drinkanddare.retrofit.models.ProductList
import com.wimatt.ux.drinkanddare.room.AppDatabase
import com.wimatt.ux.drinkanddare.room.daos.ProductDao
import com.wimatt.ux.drinkanddare.room.models.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.concurrent.thread

public class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    var disposable: Disposable? = null

    private val productDao: ProductDao

    val kuroService by lazy {
        KuroService.create()
    }

    init {
        productDao = AppDatabase.getDatabase(application).productDao
//        thread {
//            productDao.deleteAll()
//            productDao.insertProducts(mutableListOf(
//                    Product(1, "Brot", "away"),
//                    Product(2, "Damenhygiene", "away"),
//                    Product(3, "Desinfektionsmittel", "away"),
//                    Product(4, "Eier", "available"), // TODO: UNDO
//                    Product(5, "Fertiggerichte", "away"),
//                    Product(6, "Gemüsekonserven", "away"),
//                    Product(7, "Hefe", "away"),
//                    Product(8, "Kartoffeln", "away"),
//                    Product(9, "Klopapier", "away"),
//                    Product(10, "Mehl", "away"),
//                    Product(11, "Milch", "away"),
//                    Product(12, "Nudeln", "away"),
//                    Product(13, "Öl", "away"),
//                    Product(14, "Reis", "away"),
//                    Product(15, "Säuglingsnahrung", "away"),
//                    Product(16, "Seife", "away"),
//                    Product(17, "Tomatenkonserven", "away"),
//                    Product(18, "Waschmittel", "away"),
//                    Product(19, "Windeln", "away")
//            ))
//        }

    }

    fun loadProducts() {
        kuroService.getAllProducts()
                .enqueue(object : Callback<ProductList>{
                    override fun onFailure(call: Call<ProductList>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                        thread {
                            productDao.deleteAll()
                            val list = response.body()!!.products
                            for (product in list){
                                val roomProduct = Product(product._id, product.name, product.quantity)
                                    productDao.insertSingleProduct(roomProduct)
                            }
                        }
                    }

                })
//        disposable =
////                kuroService.getAllProducts(srsearch)
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribe(
////                                { result -> productDao.insertProducts(result) },
////                                { error -> throw IOException("Error {}".format(error)) }
////                        )
    }

    fun insertProduct(product: Product): Long? {
        return productDao.insertSingleProduct(product)
    }

    fun insertProducts(products: List<Product>): Long? {
        return productDao.insertProducts(products)
    }

    fun getProducts(): LiveData<List<Product>> {
        return productDao.getAll()
    }

//    fun searchProducts(query: String): LiveData<List<Todo>> {
////        disposable = wikiApiServe.hitCountCheck("query", "json", "search", query)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(
////                        { result -> showResult(result.query.searchinfo.totalhits) },
////                        { error ->
////                            {
////                                Log.d("MYTag")
////                            }
////                        }
////                )
//        return todoDao.getAllByLevel(level, langCode)
//    }
//
//    fun getTodoByLevelFiltered(level: Int, langCode: String, filter: List<Long>): LiveData<List<Todo>> {
//        return todoDao.getAllByLevelFiltered(level, langCode, filter)
//    }
//
//    fun insertTodo(todo: Todo): Long? {
//        return todoDao.insertTodos(todo)
//    }
//
//    fun removeTodos() {
//        todoDao.deleteAll()
//    }
//    fun getTags(query: String, limit: Int): LiveData<List<TodoTag>> {
//        return todoDao.getTags(query, limit)
//    }
//
//    fun getHomeTodos(query: String, date: Date?): LiveData<List<Todo>> {
//        val today = DDateUtils.removeTime(Date())
//        var tempDate = date
//        if (date == null) {
//            tempDate = DDateUtils.removeTime(Date())
//        } else {
//            tempDate = DDateUtils.removeTime(date)
//        }
//        val showAll = date == null
//        if (query.isEmpty()) {
//            return todoDao.getHomeTodos(DDateUtils.removeTime(tempDate), showAll)
//        } else {
//            return todoDao.getByQuery(date, query)
//        }
////        return todoDao.getHomeTodos(query)
////        return todoDao.getAll()
//    }
//
//    fun getCheckedTodos(date: Date): LiveData<List<Todo>> {
//        return todoDao.getCheckedTodos(DDateUtils.removeTime(date))
//    }
//
//    fun getTodoByDate(date: Date): LiveData<List<Todo>>? {
//        return todoDao.getByDate(date)
//    }
//
//    fun getTodoByDateRange(from: Date, to: Date): LiveData<List<Todo>>? {
//        return todoDao.getByDateRange(from, to)
//    }
//
//    fun getTodoStats(query: String): LiveData<List<TodoStats>> {
//        return todoDao.getTodoStats(query)
//    }
//
//    fun getTodoById(id: Long): LiveData<Todo> {
//        return todoDao.getById(id)
//    }
//

//
//    fun checkTodo(id: Long, done: Boolean, date: Date?) {
//        var tempDate = date
//        if (date == null) {
//            tempDate = DDateUtils.removeTime(Date())
//        } else {
//            tempDate = DDateUtils.removeTime(date)
//        }
//        if (done) {
//            return todoDao.updateTodo(id, tempDate, done)
//        } else {
//            return todoDao.updateTodo(id, null, done)
//        }
//    }
//
//    fun deleteTodo(todo: Todo) {
//        thread {
//            todoDao.delete(todo)
//        }
//    }
}