package com.example.justdesserts.androidApp.ui.auth.profile

import androidx.paging.PagingSource
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.repository.AuthRepository
import com.example.justdesserts.shared.cache.Dessert

class ProfileDataSource @ApolloExperimental constructor(private val repository: AuthRepository): PagingSource<Int, Dessert>() {
    @ApolloExperimental
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dessert> {
        return try {
            val favorites = repository.getProfileDesserts()
            LoadResult.Page(data = favorites, prevKey = null, nextKey = null)
        } catch(err: Exception) {
            LoadResult.Error(err)
        }
    }
}