package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.DessertRepository
import com.example.justdesserts.shared.cache.Dessert

@ApolloExperimental
class DessertFormViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun createDessert(dessert: Dessert): Dessert? {
        return repository.newDessert(dessert.name, dessert.description, dessert.imageUrl)
    }
    suspend fun updateDessert(dessert: Dessert): Dessert? {
        return repository.updateDessert(dessert.id, dessert.name, dessert.description, dessert.imageUrl)
    }
    suspend fun deleteDessert(dessertId: String): Boolean? {
        return repository.deleteDessert(dessertId)
    }
}