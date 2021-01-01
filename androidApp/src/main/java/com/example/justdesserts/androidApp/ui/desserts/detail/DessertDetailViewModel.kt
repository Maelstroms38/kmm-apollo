package com.example.justdesserts.androidApp.ui.desserts.detail

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.DeleteDessertMutation
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.NewDessertMutation
import com.example.justdesserts.UpdateDessertMutation
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.shared.DessertRepository

@ApolloExperimental
class DessertDetailViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun getDessert(dessertId: String): GetDessertQuery.Dessert? {
        return repository.getDessert(dessertId)
    }
    suspend fun updateDessert(dessert: Dessert): UpdateDessertMutation.UpdateDessert? {
        return repository.updateDessert(dessert.dessertId, dessert.name, dessert.description, dessert.imageUrl)
    }
    suspend fun deleteDessert(dessertId: String): Boolean? {
        return repository.deleteDessert(dessertId)
    }
}