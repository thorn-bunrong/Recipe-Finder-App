package com.example.lab2mad.data

// Represents a single meal item
data class Meal(
    val id: String,
    val name: String,
    val imageUrl: String, // URL for the meal image <-- COMMA ADDED
    val category: String, // <-- COMMA ADDED
    val area: String, // <-- COMMA ADDED
    val instructions: String, // <-- COMMA ADDED
    val ingredients: List<String>
)

// Represents a category or area
data class MealCategory(
    val name: String,
    val imageUrl: String
)

// --- MOCK DATA (for demonstration) ---

// This object has all the details needed for the detail screen
val mockMealDetail = Meal(
    id = "52772",
    name = "Spicy Arrabiata Penne",
    imageUrl = "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
    category = "Vegetarian",
    area = "Italian",
    instructions = "Bring a large pot of lightly salted water to a boil. Cook penne pasta in the boiling water, stirring occasionally, until cooked through but firm to the bite, about 11 minutes. Drain and return to the pot. Heat olive oil in a large skillet over medium heat. Sauté garlic and red pepper flakes until fragrant, about 1 minute. Add tomatoes and basil; cook, stirring occasionally, until tomatoes are soft, about 5 minutes.",
    ingredients = listOf("Penne Rigate", "Olive Oil", "Garlic", "Red Chilli Flakes", "Tomato", "Basil")
)

// UPDATED to include default values for the new fields
val mockRandomMeal = Meal(
    id = "52772", // Using the detailed one as the random meal
    name = "Spicy Arrabiata Penne",
    imageUrl = "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
    category = "Vegetarian",
    area = "Italian",
    instructions = "A delicious and quick vegetarian pasta dish.",
    ingredients = listOf("Penne", "Tomatoes", "Garlic")
)

// UPDATED to include default values for the new fields
val mockPopularMeals = listOf(
    Meal("52977", "Corba", "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg", "Side", "Turkish", "A traditional Turkish soup.", listOf("Lentils", "Onion", "Mint")),
    Meal("53060", "Burek", "https://www.themealdb.com/images/media/meals/tkxquw1628771028.jpg", "Side", "Croatian", "A savory pastry.", listOf("Phyllo dough", "Ground meat", "Onion")),
    Meal("52978", "Kumpir", "https://www.themealdb.com/images/media/meals/mlchx21564916997.jpg", "Side", "Turkish", "A baked potato with fillings.", listOf("Potato", "Cheese", "Corn")),
    Meal("52785", "Dal Fry", "https://www.themealdb.com/images/media/meals/wuxrtu1483564410.jpg", "Vegetarian", "Indian", "A popular Indian lentil dish.", listOf("Lentils", "Tomato", "Cumin"))
)

val mockCategories = listOf(
    MealCategory("Seafood", "https://www.themealdb.com/images/category/seafood.png"),
    MealCategory("Dessert", "https://www.themealdb.com/images/category/dessert.png"),
    MealCategory("Pasta", "https://www.themealdb.com/images/category/pasta.png"),
    MealCategory("Vegan", "https://www.themealdb.com/images/category/vegan.png")
)

val mockAreas = listOf(
    MealCategory("Italian", "https://www.themealdb.com/images/icons/flags/big/64/it.png"),
    MealCategory("Japanese", "https://www.themealdb.com/images/icons/flags/big/64/jp.png"),
    MealCategory("Mexican", "https://www.themealdb.com/images/icons/flags/big/64/mx.png"),
    MealCategory("Indian", "https://www.themealdb.com/images/icons/flags/big/64/in.png")
)
