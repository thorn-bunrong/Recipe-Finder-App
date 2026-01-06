package com.example.lab2mad.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

// --- FIX: Correctly import all required screens from their packages ---
import com.example.lab2mad.favourites.FavouriteScreen
import com.example.lab2mad.screens.HomeScreenUI.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == "Home",
                    onClick = { selectedTab = "Home" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Explore, contentDescription = "Explore") },
                    label = { Text("Explore") },
                    selected = selectedTab == "Explore",
                    onClick = { selectedTab = "Explore" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favourite") },
                    label = { Text("Favourite") },
                    selected = selectedTab == "Favourite",
                    onClick = { selectedTab = "Favourite" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // This is where the content for each tab will go
            when (selectedTab) {
                "Home" -> HomeScreen(navController = navController)
                // ExploreScreen is in the same 'screens' package, so no import is needed.
                "Explore" -> ExploreScreen(navController = navController)
                "Favourite" -> FavouriteScreen(
                    onMealClick = { mealId ->
                        // When a favourite meal is clicked, navigate to its detail screen
                        navController.navigate("mealDetail/$mealId")
                    }
                )
            }
        }
    }
}
