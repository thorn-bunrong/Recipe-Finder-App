package com.example.lab2mad.data

import androidx.room.*
import com.example.lab2mad.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Insert a product into the database. If it already exists, replace it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    // Delete a product from the database.
    @Delete
    suspend fun delete(product: Product)

    // Get a single product by its ID. Returns a Flow, so the UI can observe changes.
    @Query("SELECT * FROM favourite_products WHERE id = :productId")
    fun getProductById(productId: String): Flow<Product?>

    // Get all favourite products, ordered by name.
    @Query("SELECT * FROM favourite_products ORDER BY name ASC")
    fun getAllFavourites(): Flow<List<Product>>
}
