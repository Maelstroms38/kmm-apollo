package com.example.justdesserts.androidApp.ui.desserts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.justdesserts.GetDessertsQuery
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertListView(bottomBar: @Composable () -> Unit, dessertSelected: (dessert: GetDessertsQuery.Dessert) -> Unit) {
  val dessertListViewModel = getViewModel<DessertListViewModel>()
  val lazyDessertList = dessertListViewModel.desserts.collectAsLazyPagingItems()

  Scaffold(
    topBar = { TopAppBar(title = { Text( "Desserts") }) },
    bottomBar = bottomBar)
  {
    LazyColumn(contentPadding = it) {
      items(lazyDessertList) { dessert ->
        dessert?.let {
          DessertListRowView(dessert, dessertSelected)
        }
      }
    }
  }
}