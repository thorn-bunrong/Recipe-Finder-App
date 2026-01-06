package com.example.lab2mad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab2mad.screens.AppShell
import com.example.lab2mad.screens.MealDetailScreen
import com.example.lab2mad.screens.OnboardingScreen
import com.example.lab2mad.screens.ProductScreen
import com.example.lab2mad.ui.theme.Lab2MADTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2MADTheme {
                AppNavigation()
            }
        }
    }
}

/**
 * Root navigation graph of the app
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        // Onboarding Screen
        composable("onboarding") {
            OnboardingScreen(
                onFinish = {
                    navController.navigate("main") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        // Main App (Bottom Navigation)
        composable("main") {
            MainAppScreen(navController = navController)
        }

        // Meal Detail Screen (FIXED)
        composable(
            route = "mealDetail/{mealId}",
            arguments = listOf(
                navArgument("mealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""

            MealDetailScreen(
                mealId = mealId,
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }

        // Product Screen
        composable("products") {
            ProductScreen()
        }
    }
}

/**
 * Wrapper for main app UI
 */
@Composable
fun MainAppScreen(navController: NavHostController) {
    AppShell(navController = navController)
}
