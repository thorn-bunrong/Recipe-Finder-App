package com.example.lab2mad.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab2mad.api.MealFromApi
import com.example.lab2mad.screens.HomeScreenUI.PopularMealItemApi
import com.example.lab2mad.screens.HomeScreenUI.SectionTitle

@Composable
fun FavouriteScreen(
    // Get an instance of the FavouriteViewModel
    favouriteViewModel: FavouriteViewModel = viewModel(),
    // Receive a lambda to handle clicks, which will navigate to the detail screen
    onMealClick: (String) -> Unit
) {
    // Observe the list of favourite meals from the ViewModel
    val favouriteMeals by favouriteViewModel.allFavourites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SectionTitle("My Favourite Meals")

        Spacer(modifier = Modifier.height(16.dp))

        if (favouriteMeals.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You haven't saved any favourite meals yet.",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favouriteMeals) { meal ->
                    // Convert the FavouriteMeal (from DB) to a MealFromApi object
                    // that the UI component (PopularMealItemApi) expects.
                    //
                    // FIX: All 20 ingredients and measures must be provided.
                    val mealForUI = MealFromApi(
                        idMeal = meal.id,
                        strMeal = meal.name,
                        strMealThumb = meal.imageUrl,
                        strCategory = meal.category,
                        strArea = meal.area,
                        strInstructions = null, // Instructions are not stored in favourites
                        // --- Provide null for all ingredients ---
                        strIngredient1 = null, strIngredient2 = null, strIngredient3 = null,
                        strIngredient4 = null, strIngredient5 = null, strIngredient6 = null,
                        strIngredient7 = null, strIngredient8 = null, strIngredient9 = null,
                        strIngredient10 = null, strIngredient11 = null, strIngredient12 = null,
                        strIngredient13 = null, strIngredient14 = null, strIngredient15 = null,
                        strIngredient16 = null, strIngredient17 = null, strIngredient18 = null,
                        strIngredient19 = null, strIngredient20 = null,
                        // --- Provide null for all measures ---
                        strMeasure1 = null, strMeasure2 = null, strMeasure3 = null,
                        strMeasure4 = null, // <-- FIX: Corrected the typo here
                        strMeasure5 = null, strMeasure6 = null,
                        strMeasure7 = null, strMeasure8 = null, strMeasure9 = null,
                        strMeasure10 = null, strMeasure11 = null, strMeasure12 = null,
                        strMeasure13 = null, strMeasure14 = null, strMeasure15 = null,
                        strMeasure16 = null, strMeasure17 = null, strMeasure18 = null,
                        strMeasure19 = null, strMeasure20 = null
                    )


                    // Use the existing UI component to display the meal
                    PopularMealItemApi(
                        meal = mealForUI,
                        onClick = { onMealClick(meal.id) }
                    )
                }
            }
        }
    }
}
