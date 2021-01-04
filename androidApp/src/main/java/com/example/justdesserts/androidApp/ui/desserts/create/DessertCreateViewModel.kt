package com.example.justdesserts.androidApp.ui.desserts.create

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.NewDessertMutation
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.shared.DessertRepository

@ApolloExperimental
class DessertCreateViewModel constructor(private val repository: DessertRepository): ViewModel() {
    suspend fun createDessert(dessert: Dessert): NewDessertMutation.NewDessert? {
        return repository.newDessert(dessert.name, dessert.description, dessert.imageUrl)
    }
}