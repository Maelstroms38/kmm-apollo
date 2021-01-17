package com.example.justdesserts.androidApp.ui.desserts.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.items
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListRowView
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoriteListView(bottomBar: @Composable () -> Unit, dessertSelected: (dessert: Dessert) -> Unit) {
    val favoriteListViewModel = getViewModel<FavoriteListViewModel>()
    val favorites = favoriteListViewModel.desserts.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Desserts") }) },
        bottomBar = bottomBar,
        bodyContent = {
            LazyColumn(contentPadding = it) {
                items(favorites) { favorite ->
                    favorite?.let { dessert ->
                        val readDessert = Dessert(
                            dessertId = dessert.id,
                            name = dessert.name ?: "",
                            description = dessert.description ?: "",
                            imageUrl = dessert.imageUrl ?: ""
                        )
                        DessertListRowView(readDessert, dessertSelected)
                    }
                }
            }
        })
}
