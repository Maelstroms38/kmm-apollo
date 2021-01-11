package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.NewDessertMutation
import com.example.justdesserts.UpdateDessertMutation
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.shared.DessertRepository

@ApolloExperimental
class DessertFormViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun createDessert(dessert: Dessert): NewDessertMutation.NewDessert? {
        return repository.newDessert(dessert.name, dessert.description, dessert.imageUrl)
    }
    suspend fun updateDessert(dessert: Dessert): UpdateDessertMutation.UpdateDessert? {
        return repository.updateDessert(dessert.dessertId, dessert.name, dessert.description, dessert.imageUrl)
    }
    suspend fun deleteDessert(dessertId: String): Boolean? {
        return repository.deleteDessert(dessertId)
    }
}