package com.example.justdesserts.androidApp.ui.auth.login

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.repository.AuthRepository
import com.example.justdesserts.type.UserInput

@ApolloExperimental
class LoginViewModel constructor(private val repository: AuthRepository): ViewModel() {
    suspend fun signIn(email: String, password: String): String {
        return repository.signIn(UserInput(email = email, password = password))
    }
    suspend fun signUp(email: String, password: String): String {
        return repository.signUp(UserInput(email = email, password = password))
    }
    fun getAuthToken(): String {
        return repository.getUserState()?.token ?: ""
    }
    fun deleteAuthToken() {
        repository.deleteUserState()
    }
}