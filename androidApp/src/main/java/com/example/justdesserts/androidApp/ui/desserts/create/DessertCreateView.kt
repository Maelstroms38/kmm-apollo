package com.example.justdesserts.androidApp.ui.desserts.create

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.androidApp.models.DessertAction
import com.example.justdesserts.androidApp.ui.desserts.form.DessertFormView
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertCreateView(popBack: () -> Unit) {
    val dessertCreateViewModel = getViewModel<DessertCreateViewModel>()
    val (dessert) = remember { mutableStateOf<Dessert?>(Dessert(DessertAction.READ, "new", "", "", "")) }
    val scope = rememberCoroutineScope()

    suspend fun createDessert(dessert: Dessert) {
        dessertCreateViewModel.createDessert(dessert)
        popBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Dessert") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = {
            dessert?.let {
                DessertFormView(
                    Dessert(
                        dessertId = it.dessertId,
                        name = it.name,
                        description = it.description,
                        imageUrl = it.imageUrl
                    ),
                    handler = { dessert ->
                        scope.async {
                            createDessert(dessert)
                        }
                    }
                )
            }
        })
    }
