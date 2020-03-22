package com.wimatt.ux.drinkanddare.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wimatt.ux.drinkanddare.room.daos.ProductDao
import com.wimatt.ux.drinkanddare.room.daos.ShopDao
import com.wimatt.ux.drinkanddare.room.models.Product
import com.wimatt.ux.drinkanddare.room.models.Shop


@Database(entities = arrayOf(
        Product::class, Shop::class
), version =
        4, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val shopDao: ShopDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
//                synchronized(AppDatabase::class.java) {
//                    val MIGRATION_3_4 = object : Migration(3, 4) {
//                        override fun migrate(database: SupportSQLiteDatabase) {
//                            database.execSQL("DROP TABLE tag")
//                            database.execSQL("DROP TABLE diarytags")
//                            database.execSQL("CREATE TABLE diarytags (`name` TEXT NOT NULL PRIMARY KEY, `last_use` INTEGER NOT NULL)")
//                        }
//                    }

                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase::class.java, "word_database")
                        // Wipes and rebuilds instead of migrating
                        // if no Migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
//                                .addMigrations(MIGRATION_3_4)
                        .build()
            }


            return INSTANCE!!
        }
    }

}
