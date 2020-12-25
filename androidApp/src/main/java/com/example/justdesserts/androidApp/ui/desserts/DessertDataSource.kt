package com.example.justdesserts.androidApp.ui.desserts

import androidx.paging.PagingSource
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertsQuery
import com.example.justdesserts.GetDessertsQuery.Data
import com.example.justdesserts.shared.DessertRepository

class DessertDataSource @ApolloExperimental constructor(private val repository: DessertRepository): PagingSource<Int, GetDessertsQuery.Dessert>() {

  @ApolloExperimental
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetDessertsQuery.Dessert> {
    val pageNumber = params.key ?: 0

    // TODO: add pagination
    val desserts = repository.getDesserts()

    val prevKey = if (pageNumber > 0) pageNumber - 1 else null
    val nextKey = null //dessertResponse?.info?.next
    return LoadResult.Page(data = desserts ?: emptyList(), prevKey = null, nextKey = null)
  }
}