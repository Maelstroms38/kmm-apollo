package com.example.justdesserts.androidApp.ui.desserts.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomDrawerLayout
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.androidApp.models.DessertAction
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormView
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertListView(bottomBar: @Composable () -> Unit, newDessertSelected: () -> Unit, dessertSelected: (dessert: Dessert) -> Unit) {
  val dessertListViewModel = getViewModel<DessertListViewModel>()
  val lazyDessertList = dessertListViewModel.desserts.collectAsLazyPagingItems()

  Scaffold(
    topBar = { TopAppBar(title = { Text( "Desserts") }) },
    bottomBar = bottomBar,
    floatingActionButton = {
      FloatingActionButton(onClick = {
        newDessertSelected()
      }, backgroundColor = MaterialTheme.colors.primary) {
        Icon(Icons.Outlined.Add)
      }
    },
    bodyContent = {
      LazyColumn(contentPadding = it) {
        items(lazyDessertList) { dessert ->
          val readDessert = Dessert(
            dessertId = dessert?.id ?: "",
            name = dessert?.name ?: "",
            description = dessert?.description ?: "",
            imageUrl = dessert?.imageUrl ?: ""
          )
          DessertListRowView(readDessert, dessertSelected)
        }
      }
    })
}