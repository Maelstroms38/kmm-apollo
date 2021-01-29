package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.repository.DessertRepository
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.DessertDetail
import com.example.justdesserts.type.DessertInput

@ApolloExperimental
class DessertFormViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun getDessert(dessertId: String): DessertDetail? {
        return repository.getDessert(dessertId)
    }
    suspend fun createDessert(dessert: Dessert): Dessert? {
        return repository.newDessert(DessertInput(name = dessert.name, description = dessert.description, imageUrl = dessert.imageUrl))
    }
    suspend fun updateDessert(dessert: Dessert): Dessert? {
        return repository.updateDessert(dessert.id, DessertInput(name = dessert.name, description = dessert.description, imageUrl = dessert.imageUrl))
    }
    suspend fun deleteDessert(dessertId: String): Boolean? {
        return repository.deleteDessert(dessertId)
    }
}