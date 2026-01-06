package com.example.lab2mad.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2mad.api.CategoryFromApi
import com.example.lab2mad.api.MealFromApi
import com.example.lab2mad.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    // Private state flows to hold the data within the ViewModel
    private val _categories = MutableStateFlow<List<CategoryFromApi>>(emptyList())
    val categories: StateFlow<List<CategoryFromApi>> = _categories

    private val _meals = MutableStateFlow<List<MealFromApi>>(emptyList())
    val meals: StateFlow<List<MealFromApi>> = _meals

    init {
        // When the screen first loads, fetch all categories...
        fetchAllCategories()
        // ...and show meals from the "Seafood" category by default.
        fetchMealsByCategory("Seafood")
    }

    private fun fetchAllCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                _categories.value = response.categories
            } catch (e: Exception) {
                println("API Error fetching categories: ${e.message}")
            }
        }
    }

    // This function can be called by the UI to change the displayed meal list.
    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealsByCategory(category)
                _meals.value = response.meals
            } catch (e: Exception) {
                println("API Error fetching meals for category '$category': ${e.message}")
            }
        }
    }
}
