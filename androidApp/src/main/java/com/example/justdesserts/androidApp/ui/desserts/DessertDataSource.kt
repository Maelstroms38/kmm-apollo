package com.example.justdesserts.androidApp.ui.desserts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertsQuery
import com.example.justdesserts.shared.DessertRepository

class DessertDataSource @ApolloExperimental constructor(private val repository: DessertRepository): PagingSource<Int, GetDessertsQuery.Result>() {

  @ApolloExperimental
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetDessertsQuery.Result> {
    val pageNumber = params.key ?: 0

    return try {
      val dessertResponse = repository.getDesserts(page = pageNumber, size = 10)
      val desserts = dessertResponse?.resultsFilterNotNull()

      val prevKey = if (pageNumber > 0) pageNumber - 1 else null
      val nextKey = dessertResponse?.info?.next
      LoadResult.Page(data = desserts ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    } catch (err: Exception) {
      LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
    }
  }
}