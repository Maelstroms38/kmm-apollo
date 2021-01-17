package com.example.justdesserts.androidApp.ui.desserts.favorites

import androidx.paging.PagingSource
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.shared.DessertRepository

class FavoriteDataSource @ApolloExperimental constructor(private val repository: DessertRepository): PagingSource<Int, GetDessertQuery.Dessert>() {

    @ApolloExperimental
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetDessertQuery.Dessert> {
        val favorites = repository.getFavoriteDesserts()
        return LoadResult.Page(data = favorites, prevKey = null, nextKey = null)
    }
}