package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.justdesserts.shared.cache.DessertAction
import com.example.justdesserts.shared.cache.Dessert
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertFormView(dessert: Dessert, action: DessertAction, popBack: () -> Unit) {
    val dessertFormViewModel = getViewModel<DessertFormViewModel>()
    val (dessert, setDessert) = remember { mutableStateOf(dessert) }
    val isEditing = action != DessertAction.CREATE
    val scope = rememberCoroutineScope()
    val label = if (isEditing) "Save" else "Create"

    suspend fun handleDessert(action: DessertAction) {
        when (action) {
            DessertAction.CREATE -> {
                dessertFormViewModel.createDessert(dessert)
                popBack()
            }
            DessertAction.UPDATE -> {
                val updateDessert = dessertFormViewModel.updateDessert(dessert)
                setDessert(
                    Dessert(
                        id = updateDessert?.id ?: "",
                        userId = updateDessert?.userId ?: "",
                        name = updateDessert?.name ?: "",
                        description = updateDessert?.description ?: "",
                        imageUrl = updateDessert?.imageUrl ?: ""
                    )
                )
                popBack()
            }
            DessertAction.DELETE -> {
                val deleted = dessertFormViewModel.deleteDessert(dessert.id)
                if (deleted == true) popBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$label Dessert") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = {
            dessert?.let {
                ScrollableColumn(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(
                            text = "Name",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                        TextField(
                            value = dessert.name,
                            onValueChange = { setDessert(dessert.copy(name = it)) }

                        )
                        Text(
                            text = "Description",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                        TextField(
                            value = dessert.description,
                            onValueChange = { setDessert(dessert.copy(description = it)) }
                        )
                        Text(
                            text = "Image URL",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.body1
                        )
                        TextField(
                            value = dessert.imageUrl,
                            onValueChange = { setDessert(dessert.copy(imageUrl = it)) }
                        )

                        Spacer(modifier = Modifier.preferredHeight(16.dp))

                        Button(onClick = {
                            scope.async {
                                handleDessert(action)
                            }
                        }, modifier = Modifier.padding(16.dp).preferredWidth(320.dp)) {
                            Text(label)
                        }

                        if (isEditing) {
                            Button(onClick = {
                                scope.async {
                                    handleDessert(DessertAction.DELETE)
                                }
                            }, modifier = Modifier.padding(16.dp).preferredWidth(320.dp)) {
                                Text("Delete")
                            }
                        }

                    }
                }
            }
        })
    }
