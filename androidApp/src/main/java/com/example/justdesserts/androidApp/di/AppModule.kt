package com.example.justdesserts.androidApp.di

import com.example.justdesserts.androidApp.ui.auth.login.LoginViewModel
import com.example.justdesserts.androidApp.ui.auth.profile.ProfileViewModel
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormViewModel
import com.example.justdesserts.androidApp.ui.desserts.detail.DessertDetailViewModel
import com.example.justdesserts.androidApp.ui.desserts.favorites.FavoriteListViewModel
import com.example.justdesserts.androidApp.ui.desserts.list.DessertListViewModel
import com.example.justdesserts.androidApp.ui.desserts.review.ReviewFormViewModel
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.repository.AuthRepository
import com.example.justdesserts.shared.repository.DessertRepository
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.logger.MyLogger
import com.example.justdesserts.shared.repository.ReviewRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainAppModule = module {
  viewModel { DessertListViewModel(get()) }
  viewModel { DessertDetailViewModel(get(), get()) }
  viewModel { DessertFormViewModel(get()) }
  viewModel { ReviewFormViewModel(get()) }
  viewModel { FavoriteListViewModel(get()) }
  viewModel { LoginViewModel(get()) }
  viewModel { ProfileViewModel(get()) }

  single { DessertRepository(get()) }
  single { AuthRepository(get()) }
  single { ReviewRepository(get()) }
  single { ApolloProvider(DatabaseDriverFactory(get()), get()) }
  single { DatabaseDriverFactory(this.androidApplication().applicationContext) }
  single { MyLogger() }
}


// Gather all app modules
val appModule = listOf(mainAppModule)