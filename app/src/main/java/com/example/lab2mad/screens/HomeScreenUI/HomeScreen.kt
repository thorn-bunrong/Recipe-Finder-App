package com.example.lab2mad.screens.HomeScreenUI

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
// We no longer need to import AreaFromApi
import com.example.lab2mad.api.CategoryFromApi
import com.example.lab2mad.api.MealFromApi

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    // Collect data from the ViewModel's StateFlows
    val randomMeal by homeViewModel.randomMeal.collectAsState()
    val popularMeals by homeViewModel.popularMeals.collectAsState()
    val categories by homeViewModel.categories.collectAsState()
    // 1. REMOVE the areas state collection

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // --- Meal of the Day ---
        SectionTitle("Meal of the Day")
        randomMeal?.let { meal ->
            RandomMealCardApi(
                meal = meal,
                onClick = { navController.navigate("mealDetail/${meal.idMeal}") }
            )
        } ?: Box(modifier = Modifier.height(200.dp)) // Placeholder

        Spacer(modifier = Modifier.height(24.dp))

        // --- Popular Meals ---
        SectionTitle("Popular in Seafood")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(popularMeals) { meal ->
                PopularMealItemApi(
                    meal = meal,
                    onClick = { navController.navigate("mealDetail/${meal.idMeal}") }
                )
            }
        }

        // 2. REMOVE the entire "Browse by Area" section
        // Spacer(modifier = Modifier.height(24.dp))
        // SectionTitle("Browse by Area")
        // LazyRow(...) { ... }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Categories ---
        SectionTitle("Categories")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(200.dp), // Consider removing this for better scrolling
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryCardApi(category = category, onClick = { /* TODO: Navigate */ })
            }
        }
    }
}

// 3. REMOVE the AreaCard composable function
/*
@Composable
fun AreaCard(area: AreaFromApi, onClick: () -> Unit) {
    // ... entire function removed
}
*/

// --- Other composables remain unchanged ---

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun RandomMealCardApi(meal: MealFromApi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun PopularMealItemApi(meal: MealFromApi, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = meal.strMealThumb,
            contentDescription = meal.strMeal,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            text = meal.strMeal,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun CategoryCardApi(category: CategoryFromApi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = category.strCategory,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = category.strCategory, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
