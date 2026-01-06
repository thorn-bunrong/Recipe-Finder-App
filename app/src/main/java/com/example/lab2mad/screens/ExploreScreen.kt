package com.example.lab2mad.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lab2mad.api.CategoryFromApi
import com.example.lab2mad.screens.HomeScreenUI.PopularMealItemApi
import com.example.lab2mad.screens.HomeScreenUI.SectionTitle
import com.example.lab2mad.screens.explore.ExploreViewModel
import com.example.lab2mad.ui.theme.Lab2MADTheme

@Composable
fun ExploreScreen(
    navController: NavHostController,
    exploreViewModel: ExploreViewModel = viewModel() // 1. Get the ViewModel
) {
    // 2. Observe the data from the ViewModel
    val categories by exploreViewModel.categories.collectAsState()
    val meals by exploreViewModel.meals.collectAsState()
    var selectedCategoryName by remember { mutableStateOf("Seafood") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Section for Category Filter Chips ---
        SectionTitle("Filter by Category")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                CategoryChip(
                    name = category.strCategory,
                    isSelected = selectedCategoryName == category.strCategory,
                    onClick = {
                        selectedCategoryName = category.strCategory
                        // 3. Tell the ViewModel to fetch new data
                        exploreViewModel.fetchMealsByCategory(category.strCategory)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section for the Filtered Meal List ---
        SectionTitle("Results for '$selectedCategoryName'")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(meals) { meal ->
                // 4. Use the API-compatible composable
                PopularMealItemApi(
                    meal = meal,
                    onClick = { navController.navigate("mealDetail/${meal.idMeal}") }
                )
            }
        }
    }
}

// A custom composable for the filter chips
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(name) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Selected",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}

// A preview for the Explore Screen
@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    Lab2MADTheme {
        ExploreScreen(navController = rememberNavController())
    }
}
