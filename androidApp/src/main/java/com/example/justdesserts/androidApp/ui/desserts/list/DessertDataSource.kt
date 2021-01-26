package com.example.justdesserts.androidApp.ui.desserts.list

import androidx.paging.PagingSource
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.DessertRepository
import com.example.justdesserts.shared.cache.Dessert

class DessertDataSource @ApolloExperimental constructor(private val repository: DessertRepository): PagingSource<Int, Dessert>() {

  @ApolloExperimental
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dessert> {
    val pageNumber = params.key ?: 0

    return try {
      val response = repository.getDesserts(page = pageNumber, size = 10)
      val desserts = response?.results
      val prevKey = response?.info?.prev
      val nextKey = response?.info?.next
      LoadResult.Page(data = desserts ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    } catch (err: Exception) {
      LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
    }
  }
}