package com.example.lab2mad.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2mad.data.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    // Get a reference to the DAO from the AppDatabase
    private val dao = AppDatabase.getDatabase(application).favouriteMealDao()

    // Expose the Flow of favourite meals from the DAO as a StateFlow.
    // The UI will collect this flow, and it will automatically update
    // whenever the data in the database changes.
    val allFavourites = dao.getAllFavourites()
        .stateIn(
            scope = viewModelScope,
            // Keep the data alive for 5 seconds after the last subscriber is gone
            started = SharingStarted.WhileSubscribed(5000),
            // The initial value before the first database read is an empty list
            initialValue = emptyList()
        )
}
