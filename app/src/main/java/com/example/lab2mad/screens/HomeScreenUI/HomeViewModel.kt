package com.example.lab2mad.screens.HomeScreenUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Remove the import for AreaFromApi
import com.example.lab2mad.api.CategoryFromApi
import com.example.lab2mad.api.MealFromApi
import com.example.lab2mad.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _randomMeal = MutableStateFlow<MealFromApi?>(null)
    val randomMeal: StateFlow<MealFromApi?> = _randomMeal

    private val _popularMeals = MutableStateFlow<List<MealFromApi>>(emptyList())
    val popularMeals: StateFlow<List<MealFromApi>> = _popularMeals

    private val _categories = MutableStateFlow<List<CategoryFromApi>>(emptyList())
    val categories: StateFlow<List<CategoryFromApi>> = _categories

    // 1. REMOVE the StateFlow for areas
    // private val _areas = MutableStateFlow<List<AreaFromApi>>(emptyList())
    // val areas: StateFlow<List<AreaFromApi>> = _areas

    init {
        fetchRandomMeal()
        fetchMealsByCategory("Seafood")
        fetchAllCategories()
        // 2. REMOVE the call to fetch areas
        // fetchAllAreas()
    }

    private fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomMeal()
                _randomMeal.value = response.meals.firstOrNull()
            } catch (e: Exception) { /* Handle error */ }
        }
    }

    private fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealsByCategory(category)
                _popularMeals.value = response.meals
            } catch (e: Exception) { /* Handle error */ }
        }
    }

    private fun fetchAllCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                _categories.value = response.categories
            } catch (e: Exception) { /* Handle error */ }
        }
    }

    // 3. REMOVE the entire function for fetching areas
    /*
    private fun fetchAllAreas() {
        viewModelScope.launch {
            // ... entire function removed
        }
    }
    */
}
