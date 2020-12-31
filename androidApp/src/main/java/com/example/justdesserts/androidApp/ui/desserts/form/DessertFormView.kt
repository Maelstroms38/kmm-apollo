package com.example.justdesserts.androidApp.ui.desserts.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DessertFormView(dessertId: String, name: String, description: String, imageUrl: String) {
    val (name, setName) = remember { mutableStateOf(name) }
    val (description, setDescription) = remember { mutableStateOf(description) }
    val (imageUrl, setImageUrl) = remember { mutableStateOf(imageUrl) }
    Column(modifier = Modifier.padding(8.dp)) {
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
    }
}