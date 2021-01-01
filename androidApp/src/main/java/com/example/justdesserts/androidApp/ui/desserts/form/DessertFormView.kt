package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.justdesserts.androidApp.models.Dessert
import com.example.justdesserts.androidApp.models.DessertAction

@Composable
fun DessertFormView(dessert: Dessert,
                    handler: (Dessert) -> Unit) {
    val (name, setName) = remember { mutableStateOf(dessert.name) }
    val (description, setDescription) = remember { mutableStateOf(dessert.description) }
    val (imageUrl, setImageUrl) = remember { mutableStateOf(dessert.imageUrl) }
    val dessertId = dessert.dessertId
    val isEditing = dessertId != "new"

    ScrollableColumn(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Name",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = name,
                onValueChange = { setName(it) }
            )
            Text(
                text = "Description",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = description,
                onValueChange = { setDescription(it) }
            )
            Text(
                text = "Image URL",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = imageUrl,
                onValueChange = { setImageUrl(it) }
            )

            Spacer(modifier = Modifier.preferredHeight(16.dp))

            Button(onClick = {
                if (isEditing) {
                    val updateDessert = Dessert(
                        DessertAction.UPDATE,
                        dessertId,
                        name,
                        description,
                        imageUrl)
                    handler(updateDessert)
                } else {
                    val newDessert = Dessert(
                        DessertAction.CREATE,
                        dessertId,
                        name,
                        description,
                        imageUrl)
                    handler(newDessert)
                }
            }, modifier = Modifier.padding(16.dp).preferredWidth(320.dp)) {
                val label = if (isEditing) "Save" else "Create"
                Text(label)
            }

            if (isEditing) {
                Button(onClick = {
                    handler(Dessert(DessertAction.DELETE, dessertId, "", "", ""))
                }, modifier = Modifier.padding(16.dp).preferredWidth(320.dp)) {
                    Text("Delete")
                }
            }

        }
    }
}