package com.example.justdesserts.androidApp.ui.desserts

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetDessertsQuery
import com.example.justdesserts.shared.DessertRepository
import kotlinx.coroutines.flow.Flow

class DessertListViewModel @ApolloExperimental constructor(private val repository: DessertRepository): ViewModel() {
  @ApolloExperimental
  val desserts: Flow<PagingData<GetDessertsQuery.Result>> = Pager(PagingConfig(pageSize = 10)) {
    DessertDataSource(repository)
  }.flow

  suspend fun getDessert(dessertId: String): GetDessertQuery.Dessert? {
    return repository.getDessert(dessertId)
  }

}