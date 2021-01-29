package com.example.justdesserts.androidApp.ui.desserts.favorites

import androidx.paging.PagingSource
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.repository.DessertRepository
import com.example.justdesserts.shared.cache.Dessert

class FavoriteDataSource @ApolloExperimental constructor(private val repository: DessertRepository): PagingSource<Int, Dessert>() {

    @ApolloExperimental
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dessert> {
        val favorites = repository.getFavoriteDesserts()
        return LoadResult.Page(data = favorites, prevKey = null, nextKey = null)
    }
}