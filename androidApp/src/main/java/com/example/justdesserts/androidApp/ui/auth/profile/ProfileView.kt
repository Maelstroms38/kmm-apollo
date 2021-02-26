package com.example.justdesserts.androidApp.ui.auth.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.items
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListRowView
import com.example.justdesserts.shared.cache.Dessert
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileView(dessertSelected: (dessert: Dessert) -> Unit) {
    val profileViewModel = getViewModel<ProfileViewModel>()
    val lazyDessertList = profileViewModel.desserts.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 60.dp)) {
        items(lazyDessertList) { favorite ->
            favorite?.let { dessert ->
                DessertListRowView(dessert, dessertSelected)
            }
        }
        lazyDessertList.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator (modifier = Modifier.padding(16.dp))
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator (modifier = Modifier.padding(16.dp))
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyDessertList.loadState.refresh as LoadState.Error
                    item {
                        Text(
                            text = e.error.localizedMessage!!,
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyDessertList.loadState.append as LoadState.Error
                    item {
                        Text(
                            text = e.error.localizedMessage!!
                        )
                    }
                }
            }
        }
    }
}