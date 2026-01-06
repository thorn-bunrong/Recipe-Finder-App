package com.example.lab2mad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab2mad.model.Product // <-- 1. Import the Product model

/**
 * The Room database for the entire application.
 * It now includes entities for both FavouriteMeal and the new Product model.
 */
// 2. Add Product::class to the entities array
@Database(entities = [FavouriteMeal::class, Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // You already have this for meals
    abstract fun favouriteMealDao(): FavouriteMealDao

    // 3. Add the abstract function for your new ProductDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_app_database" // The database name stays the same
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
