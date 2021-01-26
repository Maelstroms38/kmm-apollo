package com.example.justdesserts.androidApp.di

import com.example.justdesserts.androidApp.ui.auth.login.LoginViewModel
import com.example.justdesserts.androidApp.ui.auth.profile.ProfileViewModel
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormViewModel
import com.example.justdesserts.androidApp.ui.desserts.detail.DessertDetailViewModel
import com.example.justdesserts.androidApp.ui.desserts.favorites.FavoriteListViewModel
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListViewModel
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.AuthRepository
import com.example.justdesserts.shared.DessertRepository
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainAppModule = module {
  viewModel { DessertListViewModel(get()) }
  viewModel { DessertDetailViewModel(get()) }
  viewModel { DessertFormViewModel(get()) }
  viewModel { FavoriteListViewModel(get()) }
  viewModel { LoginViewModel(get()) }
  viewModel { ProfileViewModel(get()) }

  single { DessertRepository(get()) }
  single { AuthRepository(get()) }
  single { ApolloProvider(DatabaseDriverFactory(get())) }
  single { DatabaseDriverFactory(this.androidApplication().applicationContext) }
}


// Gather all app modules
val appModule = listOf(mainAppModule)