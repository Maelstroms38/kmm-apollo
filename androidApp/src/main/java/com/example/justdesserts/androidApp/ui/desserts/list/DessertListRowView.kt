package com.example.justdesserts.androidApp.ui.desserts.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.justdesserts.GetDessertsQuery
import com.example.justdesserts.androidApp.models.Dessert
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DessertListRowView(dessert: Dessert, dessertSelected: (network: Dessert) -> Unit) {
  Row(modifier = Modifier.fillMaxWidth().clickable(onClick = { dessertSelected(dessert) }).padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
  ) {

    Card(modifier = Modifier.preferredSize(50.dp), shape = CircleShape) {
      CoilImage(data = dessert.imageUrl)
    }

    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
      Text(dessert.name, style = MaterialTheme.typography.h6)
      Text(dessert.description,
           style = MaterialTheme.typography.subtitle2, color = Color.Gray)
    }
  }
  Divider()
}