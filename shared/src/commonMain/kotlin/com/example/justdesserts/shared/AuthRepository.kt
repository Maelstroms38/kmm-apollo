package com.example.justdesserts.shared

import com.example.justdesserts.GetProfileQuery
import com.example.justdesserts.SignInMutation
import com.example.justdesserts.SignUpMutation
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.UserState
import com.example.justdesserts.shared.cache.toDessert
import com.example.justdesserts.type.UserInput
import kotlinx.coroutines.flow.single

class AuthRepository(apolloProvider: ApolloProvider) {
    private val apolloClient = apolloProvider.apolloClient
    private val database = apolloProvider.database

    @Throws(Exception::class) suspend fun signIn(email: String, password: String): String {
        val response = apolloClient.mutate(SignInMutation(UserInput(email = email, password = password))).execute().single()
        response.data?.signIn.let { data ->
            if (data?.user?.id != null) {
                database.saveUserState(data.user.id, data.token)
                return data.token
            }
        }
        throw Exception("Could not sign in")
    }

    @Throws(Exception::class) suspend fun signUp(email: String, password: String): String {
        val response = apolloClient.mutate(SignUpMutation(UserInput(email = email, password = password))).execute().single()
        response.data?.signUp.let { data ->
            if (data?.user?.id != null) {
                database.saveUserState(data.user.id, data.token)
                return data.token
            }
        }
        throw Exception("Could not sign up")
    }

    @Throws(Exception::class) suspend fun getProfileDesserts(): List<Dessert> {
        val response = apolloClient.query(GetProfileQuery()).execute().single()
        return response.data?.getProfile?.desserts?.map { it.toDessert() } ?: emptyList()
    }

    fun getUserState(): UserState? {
        return database.getUserState()
    }

    fun deleteUserState() {
        return database.deleteUserState()
    }
}