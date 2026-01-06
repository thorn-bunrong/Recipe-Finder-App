package com.example.lab2mad.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lab2mad.api.MealFromApi
import com.example.lab2mad.screens.detail.MealDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    onBackPress: () -> Unit,
    // The ViewModel is now injected automatically by the navigation library
    mealDetailViewModel: MealDetailViewModel = viewModel()
) {
    // Observe the meal details and favorite status from the ViewModel
    val meal by mealDetailViewModel.mealDetails.collectAsState()
    val isFavourite by mealDetailViewModel.isFavourite.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meal Details") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        // THE FLOATING ACTION BUTTON FOR FAVORITES
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mealDetailViewModel.toggleFavourite() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    // Change the icon based on the favorite status
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favourite",
                    tint = if (isFavourite) Color.Red else Color.White
                )
            }
        }
    ) { paddingValues ->
        // Show a loading indicator while meal details are being fetched
        if (meal == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Once loaded, display the meal details
            MealDetailContent(meal = meal!!, modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun MealDetailContent(meal: MealFromApi, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Meal Image
        AsyncImage(
            model = meal.strMealThumb,
            contentDescription = meal.strMeal,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Meal Title
        Text(
            text = meal.strMeal,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category and Area
        Row {
            Text(text = "Category: ${meal.strCategory}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Area: ${meal.strArea}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instructions
        Text(text = "Instructions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = meal.strInstructions ?: "No instructions available.", style = MaterialTheme.typography.bodyMedium)
    }
}
