package com.example.justdesserts.androidApp.ui.desserts.detail

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.AuthRepository
import com.example.justdesserts.shared.DessertRepository
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.DessertDetail
import com.example.justdesserts.shared.cache.UserState

@ApolloExperimental
class DessertDetailViewModel constructor(private val repository: DessertRepository,
                                         private val authRepository: AuthRepository): ViewModel() {
    suspend fun getDessert(dessertId: String): DessertDetail? {
        return repository.getDessert(dessertId)
    }

    fun isFavorite(dessertId: String): Boolean {
        return repository.isFavorite(dessertId)
    }

    fun saveFavorite(dessert: Dessert) {
        return repository.saveFavorite(dessert)
    }

    fun removeFavorite(dessertId: String) {
        return repository.removeFavorite(dessertId)
    }

    fun getUserState(): UserState? {
        return authRepository.getUserState()
    }
}