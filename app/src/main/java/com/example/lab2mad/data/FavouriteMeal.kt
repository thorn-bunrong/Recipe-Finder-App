package com.example.lab2mad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// This class defines the "favourite_meals" table in the database.
@Entity(tableName = "favourite_meals")
data class FavouriteMeal(
    // The meal ID will be the unique primary key for the table.
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val area: String
)
