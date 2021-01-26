package com.example.justdesserts.androidApp.ui.auth.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.items
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListRowView
import com.example.justdesserts.shared.cache.Dessert
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileView(dessertSelected: (dessert: Dessert) -> Unit) {
    val profileViewModel = getViewModel<ProfileViewModel>()
    val desserts = profileViewModel.desserts.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 60.dp)) {
        items(desserts) { favorite ->
            favorite?.let { dessert ->
                DessertListRowView(dessert, dessertSelected)
            }
        }
    }
}