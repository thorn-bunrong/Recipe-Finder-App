package com.example.lab2mad.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// The @Entity annotation tells Room to create a database table for this object.
@Entity(tableName = "favourite_products")
data class Product(
    // @PrimaryKey ensures each product in the database has a unique ID.
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageUrl: String, // URL for the product image
    val price: Double
)
