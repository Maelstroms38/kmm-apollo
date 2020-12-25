package com.example.justdesserts.androidApp.di

import com.example.justdesserts.androidApp.ui.desserts.DessertListViewModel
import com.example.justdesserts.shared.DessertRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainAppModule = module {
  viewModel { DessertListViewModel(get()) }

  single { DessertRepository() }
}


// Gather all app modules
val appModule = listOf(mainAppModule)