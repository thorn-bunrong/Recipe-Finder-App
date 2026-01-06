package com.example.lab2mad.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: FavouriteMeal)

    @Delete
    suspend fun delete(meal: FavouriteMeal)

    @Query("SELECT * FROM favourite_meals ORDER BY name ASC")
    fun getAllFavourites(): Flow<List<FavouriteMeal>>

    // --- ADD THIS FUNCTION ---
    /**
     * Retrieves a single favorite meal by its ID.
     * Returns a Flow so the UI can observe its status in real-time.
     * If the meal is not in the database, the flow will emit null.
     */
    @Query("SELECT * FROM favourite_meals WHERE id = :mealId")
    fun getFavouriteMealById(mealId: String): Flow<FavouriteMeal?> // <-- ADD THIS
}
