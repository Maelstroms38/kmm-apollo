package com.example.justdesserts.androidApp.ui.desserts.favorites

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.shared.DessertRepository
import kotlinx.coroutines.flow.Flow

class FavoriteListViewModel constructor(repository: DessertRepository): ViewModel() {
    val desserts: Flow<PagingData<GetDessertQuery.Dessert>> = Pager(PagingConfig(pageSize = 100)) {
        FavoriteDataSource(repository)
    }.flow
}