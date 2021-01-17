package com.example.justdesserts.androidApp.ui.desserts.detail

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.shared.DessertRepository
import kotlinx.coroutines.flow.Flow

@ApolloExperimental
class DessertDetailViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun getDessert(dessertId: String): GetDessertQuery.Dessert? {
        return repository.getDessert(dessertId)
    }

    fun isFavorite(dessertId: String): Boolean {
        return repository.isFavorite(dessertId)
    }

    suspend fun saveFavorite(dessertId: String) {
        return repository.saveFavorite(dessertId)
    }

    fun removeFavorite(dessertId: String) {
        return repository.removeFavorite(dessertId)
    }
}