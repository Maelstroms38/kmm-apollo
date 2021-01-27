package com.example.justdesserts.androidApp.ui.auth.login

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.AuthRepository

@ApolloExperimental
class LoginViewModel constructor(private val repository: AuthRepository): ViewModel() {
    suspend fun signIn(email: String, password: String): String {
        return repository.signIn(email, password)
    }
    suspend fun signUp(email: String, password: String): String {
        return repository.signUp(email, password)
    }
    fun getAuthToken(): String {
        return repository.getUserState()?.token ?: ""
    }
    fun deleteAuthToken() {
        repository.deleteUserState()
    }
}