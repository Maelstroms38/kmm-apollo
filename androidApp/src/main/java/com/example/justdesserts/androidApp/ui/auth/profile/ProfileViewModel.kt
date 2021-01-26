package com.example.justdesserts.androidApp.ui.auth.profile

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.AuthRepository
import com.example.justdesserts.shared.cache.Dessert
import kotlinx.coroutines.flow.Flow

@ApolloExperimental
class ProfileViewModel constructor(private val repository: AuthRepository): ViewModel() {
    val desserts: Flow<PagingData<Dessert>> = Pager(PagingConfig(pageSize = 100)) {
        ProfileDataSource(repository)
    }.flow
}