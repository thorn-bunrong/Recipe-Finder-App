package com.example.lab2mad.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lab2mad.api.MealFromApi
import com.example.lab2mad.api.RetrofitInstance
import com.example.lab2mad.data.AppDatabase
import com.example.lab2mad.data.FavouriteMeal
import kotlinx.coroutines.Dispatchers // <-- 1. IMPORT Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull // Import for the flow operator
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext // <-- 2. IMPORT withContext

class MealDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _mealDetails = MutableStateFlow<MealFromApi?>(null)
    val mealDetails: StateFlow<MealFromApi?> = _mealDetails

    private val favouriteMealDao = AppDatabase.getDatabase(application).favouriteMealDao()
    private val mealId: String = checkNotNull(savedStateHandle["mealId"])

    val isFavourite: Flow<Boolean> = favouriteMealDao.getFavouriteMealById(mealId)
        .map { it != null }

    init {
        fetchMealDetails(mealId)
    }

    fun toggleFavourite() {
        // Launch on the main thread is fine for starting the process
        viewModelScope.launch {
            val meal = _mealDetails.value ?: return@launch

            val favourite = FavouriteMeal(
                id = meal.idMeal,
                name = meal.strMeal,
                imageUrl = meal.strMealThumb,
                category = meal.strCategory ?: "",
                area = meal.strArea ?: ""
            )

            // 3. SWITCH to a background thread for database operations
            withContext(Dispatchers.IO) {
                val currentFavourite = favouriteMealDao.getFavouriteMealById(meal.idMeal).firstOrNull()

                if (currentFavourite != null) {
                    favouriteMealDao.delete(favourite)
                } else {
                    favouriteMealDao.insert(favourite)
                }
            }
        }
    }

    private fun fetchMealDetails(id: String) {
        // It's good practice to also run network calls on the IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMealDetails(id)
                // Switch back to the Main thread to update the UI StateFlow
                withContext(Dispatchers.Main) {
                    _mealDetails.value = response.meals.firstOrNull()
                }
            } catch (e: Exception) {
                println("API Error fetching meal details: ${e.message}")
            }
        }
    }
}
