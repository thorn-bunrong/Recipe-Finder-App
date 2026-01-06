package com.example.lab2mad.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lab2mad.model.Product

// --- This is a sample list of TV products to display ---
// In a real app, you would fetch this list from a network API.
val sampleProducts = listOf(
    Product(
        id = "tv-001",
        name = "Samsung 65\" QLED 4K TV",
        description = "A stunning 4K TV with vibrant colors.",
        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/my/qa65q60bakxxm/gallery/my-qled-q60b-qa65q60bakxxm-532168341?$720_576_PNG$",
        price = 1299.99
    ),
    Product(
        id = "tv-002",
        name = "LG 55\" OLED Smart TV",
        description = "Perfect blacks and infinite contrast.",
        imageUrl = "https://www.lg.com/in/images/tvs/md07554889/gallery/OLED55A2PSA-DZ-01.jpg",
        price = 1499.99
    ),
    Product(
        id = "tv-003",
        name = "Sony 75\" BRAVIA 8K TV",
        description = "Experience the future of television.",
        imageUrl = "https://www.sony.co.th/image/4d99b2f67a213550570a2c7a52a3a5f3?fmt=pjpeg&wid=1200&ret=product",
        price = 2999.99
    )
)

/**
 * The main screen that displays a list of products.
 * It uses the ProductViewModel to manage favorite state.
 */
@Composable
fun ProductScreen(productViewModel: ProductViewModel = viewModel()) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sampleProducts) { product ->
            ProductCard(
                product = product,
                viewModel = productViewModel
            )
        }
    }
}

/**
 * A card that displays a single product and its favorite button.
 */
@Composable
fun ProductCard(product: Product, viewModel: ProductViewModel) {
    // 1. OBSERVE FAVORITE STATUS
    // This `collectAsState` will automatically update the UI when the favorite status
    // changes in the database for this specific product. `initial = false` is a default value.
    val isFavourite by viewModel.isFavourite(product.id).collectAsState(initial = false)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = product.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }

                // 2. THE FAVORITE BUTTON
                IconButton(
                    onClick = {
                        // 3. TOGGLE THE FAVORITE STATUS
                        // When the button is clicked, check if it's already a favorite.
                        // If it is, remove it. If not, add it.
                        // The ViewModel handles the database logic.
                        if (isFavourite) {
                            viewModel.removeFromFavourites(product)
                        } else {
                            viewModel.addToFavourites(product)
                        }
                    }
                ) {
                    Icon(
                        // 4. DYNAMIC ICON AND COLOR
                        // The icon and its color change based on the `isFavourite` state.
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favourites",
                        tint = if (isFavourite) Color.Red else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
