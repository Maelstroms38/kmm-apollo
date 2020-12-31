package com.example.justdesserts.androidApp.ui.desserts

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetDessertsQuery
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun DessertDetailView(dessertId: String, popBack: () -> Unit) {
    val dessertListViewModel = getViewModel<DessertListViewModel>()
    val (dessert, setDessert) = remember { mutableStateOf<GetDessertQuery.Dessert?>(null) }

    LaunchedEffect(dessertId) {
         setDessert(dessertListViewModel.getDessert(dessertId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dessert?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        })
    {
        Surface(color = Color.White) {

            ScrollableColumn(modifier = Modifier.padding(top = 16.dp)) {
                dessert?.let {
                    Surface(color = Color.White) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val imageUrl = dessert.imageUrl
                            if (imageUrl != null) {
                                Card(
                                    modifier = Modifier.preferredSize(150.dp),
                                    shape = RoundedCornerShape(25.dp)
                                ) {
                                    CoilImage(data = imageUrl)
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.preferredHeight(16.dp))

                    Text("Reviews", style = typography.h5, color = AmbientContentColor.current,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                    Surface(color = Color.White) {
                        DessertReviewsList(dessert)
                    }

                }

            }

        }

    }

}

@Composable
private fun DessertReviewsList(dessert: GetDessertQuery.Dessert) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        dessert.reviews?.let { reviewsList ->
            reviewsList.filterNotNull().forEach { review ->
                Column {
                    Text(review.text,
                        style = typography.h6)
                    Text("${review.rating.toString()} stars",
                        style = typography.subtitle2,
                        color = Color.Gray)
                }
                Divider()
            }
        }
    }
}
