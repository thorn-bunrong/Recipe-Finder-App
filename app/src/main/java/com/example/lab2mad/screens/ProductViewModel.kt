package com.example.lab2mad.screens

import android.app.Application
// 1. REMOVE the incorrect imports for 'insert' and 'delete'
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2mad.data.AppDatabase
import com.example.lab2mad.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map // 2. ADD this import for the .map operator
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = AppDatabase.getDatabase(application).productDao()

    /**
     * Checks if a product is a favorite by its ID.
     * Returns a Flow that emits 'true' if the product exists in the database, and 'false' otherwise.
     */
    fun isFavourite(productId: String): Flow<Boolean> {
        // 3. FIX the type mismatch by converting the Flow<Product?> to Flow<Boolean>
        // The .map operator transforms the emitted value.
        // It returns true if the product is not null (i.e., it exists).
        return productDao.getProductById(productId).map { product -> product != null }
    }

    /**
     * Adds a product to the favourites database table.
     * This is a suspend function called within a coroutine.
     */
    fun addToFavourites(product: Product) {
        viewModelScope.launch {
            // Call the insert method directly from the DAO instance
            productDao.insert(product)
        }
    }

    /**
     * Removes a product from the favourites database table.
     * This is a suspend function called within a coroutine.
     */
    fun removeFromFavourites(product: Product) {
        viewModelScope.launch {
            // Call the delete method directly from the DAO instance
            productDao.delete(product)
        }
    }
}
